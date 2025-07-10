"use client"

import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import {Controller, useForm} from "react-hook-form";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import CommonLabel from "@/components/common/common-label";
import {RadioGroup, RadioGroupItem} from "@/components/ui/radio-group";

const IMAGE_MIME_TYPES = ["image/jpeg", "image/png", "image/gif"];

const signupSchema = z.object({
	userId: z.string().min(1, "아이디를 입력해주세요"),
	passWd: z.string().min(1, "비밀번호를 입력해주세요"),
	passWdCheck: z.string().min(1, "비밀번호 확인을 입력해주세요"),
	userNm: z.string().min(1, "이름을 입력해주세요"),
	nickname: z.string().min(1, "닉네임을 입력해주세요"),
	sex: z.enum(["M", "F"], { errorMap: () => ({ message: "성별을 선택해주세요" }) }),
	email: z.string().email("이메일 형식이 아닙니다"),
	profileImg: z
		.any()
		.optional()
		.refine((files) => {
			// 파일이 없으면 OK
			if (!files || files.length === 0) return true;
			// 하나만 선택됐다고 가정하고, MIME 타입 체크
			return IMAGE_MIME_TYPES.includes(files[0].type);
		}, {
			message: "jpg, png, gif 이미지 파일만 업로드 가능합니다.",
		}),
}).refine((data) => data.passWd === data.passWdCheck, {
	path: ["passWdCheck"],
	message: "비밀번호가 일치하지 않습니다",
});

type SignupFormData = z.infer<typeof signupSchema>;

export default function SignupForm(){
	const {
		control,
		register,
		handleSubmit,
		formState: { errors },
	} = useForm<SignupFormData>({
		resolver: zodResolver(signupSchema),
	});

	const onSubmit = (data: SignupFormData) => {
		// eslint-disable-next-line @typescript-eslint/no-unused-vars
		const { passWdCheck, ...payload } = data;

		const formData = new FormData();
		formData.append("userId", payload.userId);
		formData.append("passWd", payload.passWd);
		formData.append("userNm", payload.userNm);
		formData.append("nickname", payload.nickname);
		formData.append("email", payload.email);
		formData.append("sex", payload.sex);
		if (payload.profileImg?.length) {
			formData.append("profileImg", payload.profileImg[0]);
		}

		console.log(formData);
	};

	return (
		<div className="flex min-h-svh w-full items-center justify-center p-6 md:p-10">
			<div className="w-full max-w-sm">
				<Card>
					<CardHeader>
						<CardTitle>회원가입</CardTitle>
					</CardHeader>
					<CardContent>
						<form
							onSubmit={handleSubmit(onSubmit)}
							encType="multipart/form-data"
						>
							<div className="flex flex-col gap-6">
								{/* 아이디 */}
								<div className="grid gap-3">
									<CommonLabel htmlFor="userId" required={true}>아이디</CommonLabel>
									<Input id="userId" {...register("userId")} />
									{errors.userId && (
										<p className="text-sm text-red-500">
											{errors.userId.message}
										</p>
									)}
								</div>

								{/* 비밀번호 */}
								<div className="grid gap-3">
									<CommonLabel htmlFor="passWd" required={true}>비밀번호</CommonLabel>
									<Input
										id="passWd"
										type="password"
										{...register("passWd")}
									/>
									{errors.passWd && (
										<p className="text-sm text-red-500">
											{errors.passWd.message}
										</p>
									)}
								</div>

								{/* 비밀번호 확인 */}
								<div className="grid gap-3">
									<CommonLabel htmlFor="passWdCheck" required={true}>비밀번호 확인</CommonLabel>
									<Input
										id="passWdCheck"
										type="password"
										{...register("passWdCheck")}
									/>
									{errors.passWdCheck && (
										<p className="text-sm text-red-500">
											{errors.passWdCheck.message}
										</p>
									)}
								</div>

								{/* 이름 */}
								<div className="grid gap-3">
									<CommonLabel htmlFor="userNm" required={true}>이름</CommonLabel>
									<Input id="userNm" {...register("userNm")} />
									{errors.userNm && (
										<p className="text-sm text-red-500">
											{errors.userNm.message}
										</p>
									)}
								</div>

								{/* 닉네임 */}
								<div className="grid gap-3">
									<CommonLabel htmlFor="nickname" required={true}>닉네임</CommonLabel>
									<Input id="nickname" {...register("nickname")} />
									{errors.nickname && (
										<p className="text-sm text-red-500">
											{errors.nickname.message}
										</p>
									)}
								</div>

								{/* 성별 */}
								<div className="grid gap-3">
									<CommonLabel htmlFor="sex" required={true}>성별</CommonLabel>
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
													<RadioGroupItem
														value="M"
														id="sex-male"
														className="h-4 w-4 border border-gray-300 rounded-full"
													/>
													<CommonLabel htmlFor="sex-male">남성</CommonLabel>
												</div>
												<div className="flex items-center gap-1">
													<RadioGroupItem
														value="F"
														id="sex-female"
														className="h-4 w-4 border border-gray-300 rounded-full"
													/>
													<CommonLabel htmlFor="sex-female">여성</CommonLabel>
												</div>
											</RadioGroup>
										)}
									/>
									{errors.sex && <p className="text-sm text-red-500">{errors.sex.message}</p>}
								</div>

								{/* 이메일 */}
								<div className="grid gap-3">
									<CommonLabel htmlFor="email" required={true}>이메일</CommonLabel>
									<Input id="email" type="email" {...register("email")} />
									{errors.email && (
										<p className="text-sm text-red-500">
											{errors.email.message}
										</p>
									)}
								</div>

								{/* 프로필 이미지 */}
								<div className="grid gap-3">
									<CommonLabel htmlFor="profileImg">프로필 이미지</CommonLabel>
									<Input
										id="profileImg"
										type="file"
										accept="image/*"
										{...register("profileImg")}
									/>
									{errors.profileImg && (
										<p className="text-sm text-red-500">
											{errors.profileImg.message as string}
										</p>
									)}
								</div>

								<Button type="submit" className="w-full">
									회원가입
								</Button>
							</div>
						</form>
					</CardContent>
				</Card>
			</div>
		</div>
	);
}