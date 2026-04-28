import { create } from "zustand";
import { createJSONStorage, persist } from "zustand/middleware";
import { ComboDto } from "@/shared/types/init";

interface InitStore {
  langCdList: ComboDto[];
  isInitialized: boolean;
  setLangCdList: (list: ComboDto[]) => void;
  setInitialized: (value: boolean) => void;
  clear: () => void;
}

export const useInitStore = create<InitStore>()(
  persist(
    (set) => ({
      langCdList: [],
      isInitialized: false,
      setLangCdList: (list) => set({ langCdList: list }),
      setInitialized: (value) => set({ isInitialized: value }),
      clear: () => set({ langCdList: [], isInitialized: false }),
    }),
    {
      name: "init-storage",
      storage: createJSONStorage(() => sessionStorage),
    }
  )
);
