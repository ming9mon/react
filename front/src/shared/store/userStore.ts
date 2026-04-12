import {create} from "zustand";
import {createJSONStorage, persist} from "zustand/middleware";
import { UserInfo, MenuAuth, LoginResponse } from "@/shared/types/user";

interface UserStore {
	userInfo: UserInfo | null;
	menuAuth: MenuAuth | null;
	accessToken: string | null;
	refreshToken: string | null;

	setUserInfo: (info: LoginResponse) => void;
	clearUser: () => void;
}

export const useUserStore = create<UserStore>()(
	persist(
		(set) => ({
			userInfo: null,
			menuAuth: null,
			accessToken: null,
			refreshToken: null,

			setUserInfo: (info: LoginResponse) => {
				set({
					userInfo: info?.userInfo ?? null,
					menuAuth: info?.menuAuth ?? {},
					accessToken: info?.accessToken ?? null,
					refreshToken: info?.refreshToken ?? null,
				})
			},
			clearUser: () => {
				set({
					userInfo: null,
					menuAuth: null,
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