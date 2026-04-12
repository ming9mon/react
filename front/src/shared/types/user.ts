export interface UserInfo {
	userId: string;
	userNm: string;
	nickName: string;
	profilePicUrl: string;
}

export interface MenuAuth {
	menuId: string;
	menuNm: string;
	menuUrl: string;
}

export interface LoginResponse {
	userInfo: UserInfo;
	menuAuth: MenuAuth;
	accessToken: string;
	refreshToken: string;
}
