"use client";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import CommonLabel from "@/components/common/CommonLabel";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { Controller } from "react-hook-form";
import { useSignupForm } from "../model/useSignupForm";

export default function SignupForm() {
  const { control, register, handleSubmit, errors, onSubmit } = useSignupForm();

  return (
    <div className="h-full w-full overflow-y-auto">
        <div className="w-full max-w-lg mx-auto px-6 sm:px-10 py-10">

          {/* 헤더 */}
          <div className="mb-8 pb-5 border-b border-gray-100">
            <h1 className="text-xl font-semibold text-gray-800">회원가입</h1>
          </div>

          {/* 폼 */}
          <form onSubmit={handleSubmit(onSubmit)} encType="multipart/form-data">
            <div className="flex flex-col gap-5">

              {/* 아이디 */}
              <div className="grid gap-2">
                <CommonLabel htmlFor="userId" required>아이디</CommonLabel>
                <Input id="userId" {...register("userId")} maxLength={20} placeholder="4~20자" />
                {errors.userId && <p className="text-sm text-red-500">{errors.userId.message}</p>}
              </div>

              {/* 비밀번호 */}
              <div className="grid gap-2">
                <CommonLabel htmlFor="passWd" required>비밀번호</CommonLabel>
                <Input id="passWd" type="password" {...register("passWd")} maxLength={20} placeholder="8~20자" />
                {errors.passWd && <p className="text-sm text-red-500">{errors.passWd.message}</p>}
              </div>

              {/* 비밀번호 확인 */}
              <div className="grid gap-2">
                <CommonLabel htmlFor="passWdCheck" required>비밀번호 확인</CommonLabel>
                <Input id="passWdCheck" type="password" {...register("passWdCheck")} maxLength={20} />
                {errors.passWdCheck && <p className="text-sm text-red-500">{errors.passWdCheck.message}</p>}
              </div>

              {/* 이름 */}
              <div className="grid gap-2">
                <CommonLabel htmlFor="userNm" required>이름</CommonLabel>
                <Input id="userNm" {...register("userNm")} maxLength={12} />
                {errors.userNm && <p className="text-sm text-red-500">{errors.userNm.message}</p>}
              </div>

              {/* 닉네임 */}
              <div className="grid gap-2">
                <CommonLabel htmlFor="nickname" required>닉네임</CommonLabel>
                <Input id="nickname" {...register("nickname")} maxLength={12} />
                {errors.nickname && <p className="text-sm text-red-500">{errors.nickname.message}</p>}
              </div>

              {/* 이메일 */}
              <div className="grid gap-2">
                <CommonLabel htmlFor="email" required>이메일</CommonLabel>
                <Input id="email" type="email" {...register("email")} maxLength={40} />
                {errors.email && <p className="text-sm text-red-500">{errors.email.message}</p>}
              </div>

              {/* 성별 */}
              <div className="grid gap-2">
                <CommonLabel htmlFor="sex" required>성별</CommonLabel>
                <Controller
                  name="sex"
                  control={control}
                  render={({ field }) => (
                    <RadioGroup
                      {...field}
                      onValueChange={field.onChange}
                      value={field.value}
                      className="flex items-center gap-6 h-9"
                    >
                      <div className="flex items-center gap-2">
                        <RadioGroupItem value="M" id="sex-male" />
                        <CommonLabel htmlFor="sex-male">남성</CommonLabel>
                      </div>
                      <div className="flex items-center gap-2">
                        <RadioGroupItem value="F" id="sex-female" />
                        <CommonLabel htmlFor="sex-female">여성</CommonLabel>
                      </div>
                    </RadioGroup>
                  )}
                />
                {errors.sex && <p className="text-sm text-red-500">{errors.sex.message}</p>}
              </div>

              {/* 프로필 이미지 */}
              <div className="grid gap-2">
                <CommonLabel htmlFor="profileImg">프로필 이미지</CommonLabel>
                <Input id="profileImg" type="file" accept="image/*" {...register("profileImg")} />
                {errors.profileImg && <p className="text-sm text-red-500">{errors.profileImg.message as string}</p>}
              </div>

              <Button type="submit" className="w-full mt-2">회원가입</Button>
            </div>
          </form>
        </div>
    </div>
  );
}
