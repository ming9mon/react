import {SIGNUP_API} from "@/features/auth/constants";
import {SignupPayload} from "@/features/auth/types";
import {post} from "@/shared/helpers/api";

// 회원가입
export const signup = async (
	data: FormData,
): Promise<void> => {
	return await post(SIGNUP_API, data);
};