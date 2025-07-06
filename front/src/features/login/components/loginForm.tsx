"use client"

import {z} from "zod";
import {zodResolver} from "@hookform/resolvers/zod";
import {Card, CardContent, CardDescription, CardHeader, CardTitle} from "@/components/ui/card";
import {Button} from "@/components/ui/button";
import {Label} from "@/components/ui/label";
import {Input} from "@/components/ui/input";
import {useForm} from "react-hook-form";

const loginSchema = z.object({
  username: z.string().min(1, "아이디를 입력해주세요"),
  password: z.string().min(1, "비밀번호를 입력해주세요"),
})

type LoginFormData = z.infer<typeof loginSchema>

export default function LoginForm() {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<LoginFormData>({
    resolver: zodResolver(loginSchema),
  })

  const onSubmit = (data: LoginFormData) => {
    console.log("로그인 시도:", data)
    // TODO: 로그인 API 호출
  }

  return (
		<div className="flex min-h-svh w-full items-center justify-center p-6 md:p-10">
			<div className="w-full max-w-sm">
				<div className="flex flex-col gap-6">
					<Card>
						<CardHeader>
							<CardTitle>로그인</CardTitle>
						</CardHeader>
						<CardContent>
							<form>
								<div className="flex flex-col gap-6">
									<div className="grid gap-3">
										<Label htmlFor="email">아이디</Label>
										<Input
											id="userId"
											type="text"
											placeholder="ID"
											required
										/>
									</div>
									<div className="grid gap-3">
										<div className="flex items-center">
											<Label htmlFor="password">비밀번호</Label>
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
											required
										/>
									</div>
									<div className="flex flex-col gap-3">
										<Button type="submit" className="w-full">
											로그인
										</Button>
										<Button variant="outline" className="w-full">
											Login with Google
										</Button>
									</div>
								</div>
								<div className="mt-4 text-center text-sm">
									<a href="#" className="underline underline-offset-4">
										회원가입
									</a>
								</div>
							</form>
						</CardContent>
					</Card>
				</div>
			</div>
		</div>
  )
}