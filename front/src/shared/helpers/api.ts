import axios, {AxiosError, AxiosRequestConfig, AxiosResponse} from 'axios'
import {mAlert} from "@/shared/helpers/commonDialog";


interface CustomAxiosRequestConfig extends AxiosRequestConfig {
	_retry?: boolean;
}

const handleError = async (error: AxiosError) => {
	const AXIOS_ERROR = "AxiosError";
	const UNAUTHORIZED = "Unauthorized";
	const response = error.response as AxiosResponse;
	const unauthorized = response && response.status === 401;
	const duplicateLogin = response && response.status === 409;
	const original = error.config as CustomAxiosRequestConfig;

	// 토큰 만료
	// if (unauthorized) {
	// 	// 토큰 재발급
	// 	if (!original._retry) {
	// 		try {
	// 			const config = {
	// 				headers: { "refresh-token": user.refreshToken },
	// 				_retry: true,
	// 			};
	// 			const { body } = await post(GET_ACCESS_TOKEN_API, {}, config);
	//
	// 			storeUser({ ...user, accessToken: body.accessToken });
	//
	// 			if (original.url) {
	// 				return await instance.post(original.url, original.data, original);
	// 			}
	// 		} catch (refreshErr) {
	// 			// 토큰이 만료되었습니다.
	// 			const text = changeLocaleText({ key: "M645" });
	// 			showAlert({ title: UNAUTHORIZED, text, callback: useAuth().logout });
	// 		}
	// 	}
	// 	return Promise.resolve({});
	// }

	// 중복 로그인
	// if (duplicateLogin) {
	// 	const { data } = response;
	// 	showAlert({ title: data.message, callback: useAuth().logout });
	// 	return Promise.resolve({});
	// }

	// const { data } = response;
	// if (data) {
	// 	showAlert({ title: data.message });
	// 	return Promise.resolve({});
	// }
	//
	const isAxiosError = error.name === AXIOS_ERROR;
	if (!response || isAxiosError) {
		const { code, message } = response.data;
		const text = `<strong>Message</strong>: ${message} <br/> <strong>Code</strong>: ${code}`;
		// showAlert({ title: UNAUTHORIZED, text });
		console.log(response)
		console.log(message)
		if (message) {
			mAlert(message)
		} else {
			mAlert("에러가 발생하였습니다.")
		}
	}

	return Promise.resolve({});
}

export const api = axios.create({
	baseURL: process.env.NEXT_PUBLIC_API_PREFIX,
	timeout: 10_000, // 10초
})

api.interceptors.request.use((config) => {
	// const { accessToken } = useUserStore.getState();
	// if (accessToken) config.headers!['Authorization'] = `Bearer ${accessToken}`
	return config
})

api.interceptors.response.use(
	(res) => res,
	(error) => {
		return handleError(error)
		// if (error.response?.status === 401) {
		// 	window.location.href = '/login'
		// }
		// return Promise.reject(error)
	}
)

function request<T>(promise: Promise<AxiosResponse<T>>): Promise<T> {
	return promise.then(res => res.data);
}

export const get = <T>(url: string, config?: AxiosRequestConfig) => request(api.get<T>(url, config));
export const post = <B, R>(url: string, body: B, config?: AxiosRequestConfig) => request<R>(api.post<R>(url, body, config));
export const del = <T>(url: string, config?: AxiosRequestConfig) => request<T>(api.delete<T>(url, config));
export const download = (url: string, config?: AxiosRequestConfig) => api.get<Blob>(url, { ...config, responseType: 'blob' });