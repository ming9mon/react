"use client"

import {z} from "zod";
import {zodResolver} from "@hookform/resolvers/zod";
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import {Button} from "@/components/ui/button";
import {Input} from "@/components/ui/input";
import {useForm} from "react-hook-form";
import Link from "next/link";
import CommonLabel from "@/components/common/CommonLabel";

import googleLogo from "@/shared/assets/images/logo/google.png";
import naverLogo from "@/shared/assets/images/logo/naver.png";
import kakaoLogo from "@/shared/assets/images/logo/kakao.png";
import Image from "next/image";
import {mAlert} from "@/shared/helpers/commonDialog";

const loginSchema = z.object({
  userId: z.string().min(1, "아이디를 입력해주세요"),
  passWd: z.string().min(1, "비밀번호를 입력해주세요"),
})

type LoginFormData = z.infer<typeof loginSchema>

export default function LoginForm() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormData>({
    resolver: zodResolver(loginSchema),
  })

  const onSubmit = (data: LoginFormData) => {
    console.log("로그인 시도:", data)
    // TODO: 로그인 API 호출
  }

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
											<p className="text-sm text-red-500">
												{errors.userId.message}
											</p>
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
											<p className="text-sm text-red-500">
												{errors.passWd.message}
											</p>
										)}
									</div>
									<div className="flex flex-col gap-3">
										<Button type="submit" className="w-full">
											로그인
										</Button>

										{/* OAuth 버튼들 */}
										<div className="flex flex-col gap-2">
											{[
												{ name: "google", logo: googleLogo },
												{ name: "naver", logo: naverLogo },
												{ name: "kakao", logo: kakaoLogo },
											].map(({ name, logo }) => (
												<Button
													key={name}
													type="button"
													variant="outline"
													className="w-full flex items-center justify-center gap-2 py-2"
													// onClick={() => handleOAuth(name)}
												>
													<Image src={logo} alt={`${name} 로고`} width={20} height={20} />
													<span>{`${name} 계정으로 로그인`}</span>
												</Button>
											))}
										</div>
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
  )
}