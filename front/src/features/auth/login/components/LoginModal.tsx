"use client";

import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import CommonLabel from "@/components/common/CommonLabel";
import Link from "next/link";
import OAuthButtons from "./OAuthButtons";
import { useLoginForm } from "../model/useLoginForm";
import { useUiStore } from "@/shared/store/uiStore";

export default function LoginModal() {
  const { isLoginModalOpen, closeLoginModal } = useUiStore();
  const { register, handleSubmit, errors, onSubmit } = useLoginForm();

  return (
    <Dialog open={isLoginModalOpen} onOpenChange={(open) => !open && closeLoginModal()}>
      <DialogContent className="w-full max-w-sm">
        <DialogHeader>
          <DialogTitle>로그인</DialogTitle>
        </DialogHeader>

        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="flex flex-col gap-4">
            <div className="grid gap-2">
              <CommonLabel htmlFor="userId">아이디</CommonLabel>
              <Input type="text" placeholder="ID" maxLength={20} {...register("userId")} />
              {errors.userId && <p className="text-sm text-red-500">{errors.userId.message}</p>}
            </div>

            <div className="grid gap-2">
              <div className="flex items-center">
                <CommonLabel htmlFor="passWd">비밀번호</CommonLabel>
                <a href="#" className="ml-auto text-sm underline-offset-4 hover:underline">
                  비밀번호 찾기
                </a>
              </div>
              <Input id="passWd" type="password" placeholder="password" maxLength={20} {...register("passWd")} />
              {errors.passWd && <p className="text-sm text-red-500">{errors.passWd.message}</p>}
            </div>

            <div className="flex flex-col gap-3">
              <Button type="submit" className="w-full">로그인</Button>
              <OAuthButtons />
            </div>

            <div className="text-center text-sm">
              <Link
                href="/signup"
                className="underline underline-offset-4"
                onClick={closeLoginModal}
              >
                회원가입
              </Link>
            </div>
          </div>
        </form>
      </DialogContent>
    </Dialog>
  );
}
