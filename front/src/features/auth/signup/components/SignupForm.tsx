"use client";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import CommonLabel from "@/components/common/CommonLabel";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { Controller } from "react-hook-form";
import { ArrowLeft } from "lucide-react";
import { useSignupForm } from "../model/useSignupForm";

export default function SignupForm() {
  const { control, register, handleSubmit, errors, onSubmit } = useSignupForm();

  return (
    <div className="flex h-full w-full items-center justify-center p-6 md:p-10">
      <div className="w-full max-w-sm">
        <Card>
          <CardHeader className="flex items-center justify-between">
            <CardTitle>회원가입</CardTitle>
            <Button
              variant="ghost"
              size="icon"
              onClick={() => history.back()}
              aria-label="뒤로가기"
            >
              <ArrowLeft className="h-4 w-4" />
            </Button>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit(onSubmit)} encType="multipart/form-data">
              <div className="flex flex-col gap-6">

                {/* 아이디 */}
                <div className="grid gap-3">
                  <CommonLabel htmlFor="userId" required>아이디</CommonLabel>
                  <Input id="userId" {...register("userId")} maxLength={12} />
                  {errors.userId && <p className="text-sm text-red-500">{errors.userId.message}</p>}
                </div>

                {/* 비밀번호 */}
                <div className="grid gap-3">
                  <CommonLabel htmlFor="passWd" required>비밀번호</CommonLabel>
                  <Input id="passWd" type="password" {...register("passWd")} maxLength={20} />
                  {errors.passWd && <p className="text-sm text-red-500">{errors.passWd.message}</p>}
                </div>

                {/* 비밀번호 확인 */}
                <div className="grid gap-3">
                  <CommonLabel htmlFor="passWdCheck" required>비밀번호 확인</CommonLabel>
                  <Input id="passWdCheck" type="password" {...register("passWdCheck")} maxLength={20} />
                  {errors.passWdCheck && <p className="text-sm text-red-500">{errors.passWdCheck.message}</p>}
                </div>

                {/* 이름 */}
                <div className="grid gap-3">
                  <CommonLabel htmlFor="userNm" required>이름</CommonLabel>
                  <Input id="userNm" {...register("userNm")} maxLength={12} />
                  {errors.userNm && <p className="text-sm text-red-500">{errors.userNm.message}</p>}
                </div>

                {/* 닉네임 */}
                <div className="grid gap-3">
                  <CommonLabel htmlFor="nickname" required>닉네임</CommonLabel>
                  <Input id="nickname" {...register("nickname")} maxLength={12} />
                  {errors.nickname && <p className="text-sm text-red-500">{errors.nickname.message}</p>}
                </div>

                {/* 성별 */}
                <div className="grid gap-3">
                  <CommonLabel htmlFor="sex" required>성별</CommonLabel>
                  <Controller
                    name="sex"
                    control={control}
                    render={({ field }) => (
                      <RadioGroup
                        {...field}
                        onValueChange={field.onChange}
                        value={field.value}
                        className="flex items-center space-x-6"
                      >
                        <div className="flex items-center gap-1">
                          <RadioGroupItem value="M" id="sex-male" className="h-4 w-4 border border-gray-300 rounded-full" />
                          <CommonLabel htmlFor="sex-male">남성</CommonLabel>
                        </div>
                        <div className="flex items-center gap-1">
                          <RadioGroupItem value="F" id="sex-female" className="h-4 w-4 border border-gray-300 rounded-full" />
                          <CommonLabel htmlFor="sex-female">여성</CommonLabel>
                        </div>
                      </RadioGroup>
                    )}
                  />
                  {errors.sex && <p className="text-sm text-red-500">{errors.sex.message}</p>}
                </div>

                {/* 이메일 */}
                <div className="grid gap-3">
                  <CommonLabel htmlFor="email" required>이메일</CommonLabel>
                  <Input id="email" type="email" {...register("email")} maxLength={40} />
                  {errors.email && <p className="text-sm text-red-500">{errors.email.message}</p>}
                </div>

                {/* 프로필 이미지 */}
                <div className="grid gap-3">
                  <CommonLabel htmlFor="profileImg">프로필 이미지</CommonLabel>
                  <Input id="profileImg" type="file" accept="image/*" {...register("profileImg")} />
                  {errors.profileImg && <p className="text-sm text-red-500">{errors.profileImg.message as string}</p>}
                </div>

                <Button type="submit" className="w-full">회원가입</Button>
              </div>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
