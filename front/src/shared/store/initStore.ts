import { create } from "zustand";
import { createJSONStorage, persist } from "zustand/middleware";
import { ComboDto } from "@/shared/types/init";

interface InitStore {
  langCdList: ComboDto[];
  multilingual: Record<string, string>;
  isInitialized: boolean;
  setLangCdList: (list: ComboDto[]) => void;
  setMultilingual: (map: Record<string, string>) => void;
  setInitialized: (value: boolean) => void;
  clear: () => void;
}

export const useInitStore = create<InitStore>()(
  persist(
    (set) => ({
      langCdList: [],
      multilingual: {},
      isInitialized: false,
      setLangCdList: (list) => set({ langCdList: list }),
      setMultilingual: (map) => set({ multilingual: map }),
      setInitialized: (value) => set({ isInitialized: value }),
      clear: () => set({ langCdList: [], multilingual: {}, isInitialized: false }),
    }),
    {
      name: "init-storage",
      storage: createJSONStorage(() => sessionStorage),
    }
  )
);
