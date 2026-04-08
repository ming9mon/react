"use client";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import Link from "next/link";
import CommonLabel from "@/components/common/CommonLabel";
import OAuthButtons from "./OAuthButtons";
import { useLoginForm } from "../model/useLoginForm";

export default function LoginForm() {
  const { register, handleSubmit, errors, onSubmit } = useLoginForm();

  return (
    <div className="flex h-full w-full items-center justify-center p-6 md:p-10">
      <div className="w-full max-w-sm">
        <div className="flex flex-col gap-6">
          <Card>
            <CardHeader>
              <CardTitle>로그인</CardTitle>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSubmit(onSubmit)}>
                <div className="flex flex-col gap-6">
                  <div className="grid gap-3">
                    <CommonLabel htmlFor="email">아이디</CommonLabel>
                    <Input
                      type="text"
                      placeholder="ID"
                      {...register('userId')}
                    />
                    {errors.userId && (
                      <p className="text-sm text-red-500">{errors.userId.message}</p>
                    )}
                  </div>
                  <div className="grid gap-3">
                    <div className="flex items-center">
                      <CommonLabel htmlFor="password">비밀번호</CommonLabel>
                      <a
                        href="#"
                        className="ml-auto inline-block text-sm underline-offset-4 hover:underline"
                      >
                        비밀번호 찾기
                      </a>
                    </div>
                    <Input
                      id="password"
                      type="password"
                      placeholder="password"
                      {...register('passWd')}
                    />
                    {errors.passWd && (
                      <p className="text-sm text-red-500">{errors.passWd.message}</p>
                    )}
                  </div>
                  <div className="flex flex-col gap-3">
                    <Button type="submit" className="w-full">
                      로그인
                    </Button>
                    <OAuthButtons />
                  </div>
                </div>
                <div className="mt-4 text-center text-sm">
                  <Link href="/signup" className="underline underline-offset-4">
                    회원가입
                  </Link>
                </div>
              </form>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
}
