"use client";

import { useEffect } from "react";
import { useInitStore } from "@/shared/store/initStore";
import { InitResponse } from "@/shared/types/init";
import { ApiResponse } from "@/shared/types/api";
import * as apiClient from "@/shared/api/apiClient";

export default function AppInitializer() {
  const { langCdList, setLangCdList, setInitialized } = useInitStore();

  useEffect(() => {
    if (langCdList.length > 0) return;

    const fetchInit = async () => {
      const res = await apiClient.get<ApiResponse<InitResponse>>("/init");
      if (res?.body?.langCdList?.length) {
        setLangCdList(res.body.langCdList);
      }
      setInitialized(true);
    };

    fetchInit();
  }, [langCdList.length, setLangCdList, setInitialized]);

  return null;
}
