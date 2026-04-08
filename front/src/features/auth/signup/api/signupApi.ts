import {SIGNUP_API} from "../model/constants";
import {post} from "@/shared/api/api";
import {ApiResponse} from "@/shared/types/apiResponse";

// 회원가입
export const signup = async (
	data: FormData,
): Promise<ApiResponse<void>> => {
	return await post(SIGNUP_API, data);
};