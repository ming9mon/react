"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useRouter } from "next/navigation";
import { signupSchema, SignupFormData } from "../model/schema";
import { SignupPayload } from "../model/types";
import { signup } from "../api/signupApi";
import { HOME } from "@/shared/constants";

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

  const onSubmit = async (data: SignupFormData) => {
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const { passWdCheck, ...payload } = data;
    const { code } = await signup(toFormData(payload));
    if (code === "200") {
      alert("회원가입 되었습니다.");
      router.push(HOME);
    }
  };

  return { control, register, handleSubmit, errors, onSubmit };
}
