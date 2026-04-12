"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { loginSchema, LoginFormData } from "../model/schema";
import { useApiClient } from "@/shared/hooks/useApiClient";
import { LoginResponse } from "@/shared/types/user";
import { LOGIN_API } from "./constants";
import { useUserStore } from "@/shared/store/userStore";
import { useUiStore } from "@/shared/store/uiStore";

export function useLoginForm() {
  const { register, handleSubmit, formState: { errors } } = useForm<LoginFormData>({
    resolver: zodResolver(loginSchema),
  });

  const { post } = useApiClient<LoginResponse>();
  const { setUserInfo } = useUserStore();
  const { closeLoginModal } = useUiStore();

  const onSubmit = async (data: LoginFormData) => {
    const { code, body } = await post({
      url: LOGIN_API,
      body: data,
    });

    if (code === "200" && body) {
      setUserInfo(body);
      closeLoginModal();
    }
  };

  return { register, handleSubmit, errors, onSubmit };
}
