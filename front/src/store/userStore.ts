import {create} from "zustand/react";
import {createJSONStorage, persist} from "zustand/middleware/persist";

interface UserInfo {
	userId: string;      		// 사용자 아이디
	userName: string;    		// 이름
	nickName: string;    		// 닉네임
	profilePicUrl: string;	// 사진 URL
}

interface UserInfoResponse {
	userInfo: UserInfo
	accessToken: string
	refreshToken: string
}

interface UserStore {
	userInfo: UserInfo | null
	accessToken: string | null
	refreshToken: string | null

	setUserInfo: (info: UserInfoResponse) => void;
	clearUser: () => void;
}

export const useUserStore = create<UserStore>()(
	persist(
		(set) => ({
			userInfo: null,
			accessToken: null,
			refreshToken: null,

			setUserInfo: (info: UserInfoResponse) => {
				set({
					userInfo: info?.userInfo ?? null,
					accessToken: info?.accessToken ?? null,
					refreshToken: info?.refreshToken ?? null,
				})
			},
			clearUser: () => {
				set({
					userInfo: null,
					accessToken: null,
					refreshToken: null,
				})
			}
		}),
		{
			name: 'user-storage',
			storage: createJSONStorage(() => sessionStorage)
		}
	)
)