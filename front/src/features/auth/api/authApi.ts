import {SIGNUP_API} from "@/features/auth/constants";
import {post} from "@/shared/api/client";
import {ApiResponse} from "@/shared/types/apiResponse";

// 회원가입
export const signup = async (
	data: FormData,
): Promise<ApiResponse<void>> => {
	return await post(SIGNUP_API, data);
};