export interface SignupPayload {
	userId: string;
	passWd: string;
	userNm: string;
	nickname: string;
	email: string;
	sex: 'M' | 'F';
	profileImg?: File[];
}