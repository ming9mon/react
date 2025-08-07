import {SIGNUP_API} from "@/features/auth/constants";
import {post} from "@/shared/helpers/api";
import {ApiResponse} from "@/shared/types/apiResponse";

// 회원가입
export const signup = async (
	data: FormData,
): Promise<ApiResponse<void>> => {
	return await post(SIGNUP_API, data);
};