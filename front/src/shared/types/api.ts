export interface ApiResponse<T> {
	code: string;
	message: string;
	body: T;
}

export const API_CONFIG_KEYS = ["url", "prefixPath", "subfixPath", "body"] as const;

export interface ApiConfig {
	url?: string;
	prefixPath?: string;
	subfixPath?: string;
	body?: any;
}