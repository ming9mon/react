"use client";

import { useEffect } from "react";
import { useInitStore } from "@/shared/store/initStore";
import { InitResponse } from "@/shared/types/init";
import { ApiResponse } from "@/shared/types/api";
import * as apiClient from "@/shared/api/apiClient";

export default function AppInitializer() {
  const { langCdList, setLangCdList, setMultilingual, setInitialized } = useInitStore();

  useEffect(() => {
    if (langCdList.length > 0) return;

    const fetchInit = async () => {
      const { code, body } = await apiClient.get<ApiResponse<InitResponse>>("/init");
      if (code === "200") {
        setLangCdList(body.langCdList);
        setMultilingual(body.multilingual);
        setInitialized(true);
      }
    };

    fetchInit();
  }, [langCdList.length, setLangCdList, setMultilingual, setInitialized]);

  return null;
}
