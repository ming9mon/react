"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useRouter } from "next/navigation";
import { signupSchema, SignupFormData } from "../model/schema";
import { SignupPayload } from "../model/types";
import { HOME } from "@/shared/constants";
import { useApiClient } from "@/shared/hooks/useApiClient";
import { PREFIX_PATH } from "./constants";

function toFormData(data: SignupPayload): FormData {
  const fd = new FormData();
  fd.append('userId',   data.userId);
  fd.append('passWd',   data.passWd);
  fd.append('userNm',   data.userNm);
  fd.append('nickname', data.nickname);
  fd.append('email',    data.email);
  fd.append('sex',      data.sex);
  if (data.profileImg && data.profileImg.length > 0) {
    fd.append('profileImg', data.profileImg[0]);
  }
  return fd;
}

export function useSignupForm() {
  const router = useRouter();
  const { control, register, handleSubmit, formState: { errors } } = useForm<SignupFormData>({
    resolver: zodResolver(signupSchema),
  });

  const { post } = useApiClient();

  const onSubmit = async (data: SignupFormData) => {
    const { passWdCheck, ...payload } = data;
    const { code } = await post({
      prefixPath: PREFIX_PATH,
      body: toFormData(data)
    });

    if (code === "200") {
      alert("회원가입 되었습니다.");
      router.push(HOME);
    }
  };

  return { control, register, handleSubmit, errors, onSubmit };
}
