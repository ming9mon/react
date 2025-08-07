export type DialogEntry = DialogOptions & {
	id: number;
	resolve: (result: boolean) => void;
};

export interface DialogContextType {
	showDialog: (options: AlertOptions) => Promise<boolean>;
	showDialogInternal?: (options: ConfirmOptions) => Promise<boolean>;
}

export interface AlertOptions {
	title?: string;
	message: string;
	confirmText?: string;
}

export interface ConfirmOptions extends AlertOptions {
	cancelText?: string;
}

export type DialogOptions = {
	title?: string;
	message: string;
	confirmText?: string;
	cancelText?: string;
	showCancel?: boolean;
};