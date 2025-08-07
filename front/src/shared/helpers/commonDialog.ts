import {AlertOptions, ConfirmOptions, DialogOptions} from "@/shared/types/commonDialog";

let internalShowDialog!: (opts: DialogOptions) => Promise<boolean>;

// Provider에서 내부 함수 등록
export const registerDialogInternal = (fn: (opts: DialogOptions) => Promise<boolean>) => {
	internalShowDialog = fn;
};

// mAlert: 간편 알림 다이얼로그
export const mAlert = (arg: string | AlertOptions): Promise<boolean> => {
	const opts: DialogOptions =
		typeof arg === 'string'
			? { message: arg, showCancel: false }
			: { ...arg, showCancel: false };
	return internalShowDialog(opts);
};

// mConfirm: 간편 확인/취소 다이얼로그
export const mConfirm = (arg: string | ConfirmOptions): Promise<boolean> => {
	const opts: DialogOptions =
		typeof arg === 'string'
			? { message: arg, showCancel: true }
			: {
				...arg,
				showCancel: true,
			};
	return internalShowDialog(opts);
};