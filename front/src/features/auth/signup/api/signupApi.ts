import {SIGNUP_API} from "../model/constants";
import {post} from "@/shared/api/apiClient";
import {ApiResponse} from "@/shared/types/api";

// 회원가입
export const signup = async (
	data: FormData,
): Promise<ApiResponse<void>> => {
	return await post(SIGNUP_API, data);
};