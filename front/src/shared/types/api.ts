export interface ApiResponse<T> {
	code: string;
	message: string;
	body: T;
}

export interface ApiConfig {
	url?: string;
	subUrl?: string;
	body?: any;
}