"use client";

import React, {useEffect, useState, ReactNode, FC, useCallback} from 'react';
import { Button } from '@/components/ui/button';
import {
	Dialog,
	DialogContent,
	DialogFooter,
	DialogHeader,
	DialogOverlay,
	DialogPortal,
	DialogTitle
} from "@/components/ui/dialog";
import {DialogEntry, DialogOptions} from "@/shared/types/commonDialog";
import {DialogContext} from "@/shared/hooks/useCommonDialog";
import {registerDialogInternal} from "@/shared/helpers/commonDialog";

export const CommonDialogProvider: FC<{ children: ReactNode }> = ({children,}) => {
	const [dialogs, setDialogs] = useState<DialogEntry[]>([]);

	const showDialog = useCallback((opts: DialogOptions) => {
		const id = Date.now() + Math.random();
		return new Promise<boolean>((resolve) => {
			setDialogs((prev) => [...prev, { id, ...opts, resolve }]);
		});
	}, []);

	useEffect(() => registerDialogInternal(showDialog), [showDialog]);

	const handleClose = useCallback((id: number, result: boolean) => {
		setDialogs((prev) =>
			prev.filter((d) => {
				if (d.id === id) d.resolve(result);
				return d.id !== id;
			})
		);
	}, []);

	return (
		<DialogContext.Provider value={{ showDialog }}>
			{children}

			{dialogs.map((d, idx) => (
				<Dialog key={d.id} open onOpenChange={(open) => !open && handleClose(d.id, false)}>
					{/* 직접 Portal을 열어서 overlay/content z-index를 제어 */}
					<DialogPortal>
						{/* 배경 */}
						<DialogOverlay className="fixed inset-0 bg-black/50 z-[1000]" />
						{/* 모달 콘텐츠 */}
						<DialogContent className="z-[1001]">
							<DialogHeader>
								<DialogTitle>{d.title ?? "알림"}</DialogTitle>
							</DialogHeader>
							<div className="mt-2 mb-4">{d.message}</div>
							<DialogFooter>
								{d.showCancel && (
									<Button variant="ghost" onClick={() => handleClose(d.id, false)}>
										{d.cancelText ?? "취소"}
									</Button>
								)}
								<Button onClick={() => handleClose(d.id, true)}>
									{d.confirmText ?? "확인"}
								</Button>
							</DialogFooter>
						</DialogContent>
					</DialogPortal>
				</Dialog>
			))}
		</DialogContext.Provider>
	);
};