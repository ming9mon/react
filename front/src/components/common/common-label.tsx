"use client";

import { Label } from "@/components/ui/label";

interface CommonLabelProps {
	htmlFor: string;
	children: React.ReactNode;
	required?: boolean;
}

export default function CommonLabel({ htmlFor, children, required = false }: CommonLabelProps) {
	return (
		<Label htmlFor={htmlFor} className="flex items-center gap-1">
			{children}
			{ required && <span className="text-red-500">*</span> }
		</Label>
	);
}