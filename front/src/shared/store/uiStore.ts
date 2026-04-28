import { create } from "zustand";

interface UiStore {
  isLoginModalOpen: boolean;
  openLoginModal: () => void;
  closeLoginModal: () => void;
  loadingCount: number;
  startLoading: () => void;
  stopLoading: () => void;
}

export const useUiStore = create<UiStore>((set) => ({
  isLoginModalOpen: false,
  openLoginModal: () => set({ isLoginModalOpen: true }),
  closeLoginModal: () => set({ isLoginModalOpen: false }),
  loadingCount: 0,
  startLoading: () => set((s) => ({ loadingCount: s.loadingCount + 1 })),
  stopLoading: () => set((s) => ({ loadingCount: Math.max(0, s.loadingCount - 1) })),
}));
