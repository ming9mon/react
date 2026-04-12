import {create} from "zustand";
import {createJSONStorage, persist} from "zustand/middleware";
import { UserInfo, MenuAuth, LoginResponse } from "@/shared/types/user";

interface UserStore {
	userInfo: UserInfo | null;
	menuAuth: MenuAuth | null;
	accessToken: string | null;
	refreshToken: string | null;
	_hasHydrated: boolean;

	setUserInfo: (info: LoginResponse) => void;
	clearUser: () => void;
	setHasHydrated: (state: boolean) => void;
}

export const useUserStore = create<UserStore>()(
	persist(
		(set) => ({
			userInfo: null,
			menuAuth: null,
			accessToken: null,
			refreshToken: null,
			_hasHydrated: false,

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
			},
			setHasHydrated: (state: boolean) => set({ _hasHydrated: state }),
		}),
		{
			name: 'user-storage',
			storage: createJSONStorage(() => sessionStorage),
			onRehydrateStorage: () => (state) => {
				state?.setHasHydrated(true);
			},
		}
	)
)