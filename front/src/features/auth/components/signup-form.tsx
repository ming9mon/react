"use client"

import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import {Controller, useForm} from "react-hook-form";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import CommonLabel from "@/components/common/common-label";
import {RadioGroup, RadioGroupItem} from "@/components/ui/radio-group";
import {signup} from "@/features/auth/api/authApi";
import {SignupPayload} from "@/features/auth/types";
import {ArrowLeft} from "lucide-react";
import {useRouter} from "next/navigation";
import {HOME} from "@/shared/constants";

const IMAGE_MIME_TYPES = ["image/jpeg", "image/png", "image/gif"];

const signupSchema = z.object({
	userId: z.string()
		.min(4, "아이디는 최소 4자리 이상이어야 합니다.")
		.regex(/^[a-zA-Z0-9]+$/, "아이디는 영문 대소문자와 숫자만 입력할 수 있습니다."),
	passWd: z.string()
		.min(8, "비밀번호는 최소 8자리 이상이어야 합니다.")
		.regex(/^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+[\]{};':"\\|,.<>/?]).*$/,
			"비밀번호는 대문자, 숫자, 특수문자를 포함해야 합니다."),
	passWdCheck: z.string().min(8, "비밀번호 확인을 입력해주세요."),
	userNm: z.string()
		.min(2, "이름은 최소 2자리 이상이어야 합니다.")
		.regex(/^[a-zA-Z가-힣]+$/, "이름은 한글 또는 영문만 입력할 수 있습니다."),
	nickname: z.string()
		.min(1, "닉네임을 입력해주세요")
		.max(12, "닉네임은 최대 12자리 이하이어야 합니다."),
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
	const router = useRouter();
	const {
		control,
		register,
		handleSubmit,
		formState: { errors },
	} = useForm<SignupFormData>({
		resolver: zodResolver(signupSchema),
	});

	function toFormData(data: SignupPayload): FormData {
		const fd = new FormData();                   // ← FormData 타입
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

	const onSubmit = async (data: SignupFormData) => {
		// eslint-disable-next-line @typescript-eslint/no-unused-vars
		const { passWdCheck, ...payload } = data;

		const formData = toFormData(payload)

		const { code } = await signup(formData);

		if (code === "200") {
			alert("회원가입 되었습니다.")
			router.push(HOME)
		}
	};

	return (
		<div className="flex h-full w-full items-center justify-center p-6 md:p-10">
			<div className="w-full max-w-sm">
				<Card>
					<CardHeader className="flex items-center justify-between">
						<CardTitle>회원가입</CardTitle>
						<Button
							variant="ghost"
							size="icon"
							onClick={() => router.back()}
							aria-label="뒤로가기"
						>
							<ArrowLeft className="h-4 w-4" />
						</Button>
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
									<Input
										id="userId"
										{...register("userId")}
										maxLength={12}
									/>
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
										maxLength={20}
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
										maxLength={20}
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
									<Input
										id="userNm"
										{...register("userNm")}
										maxLength={12}
									/>
									{errors.userNm && (
										<p className="text-sm text-red-500">
											{errors.userNm.message}
										</p>
									)}
								</div>

								{/* 닉네임 */}
								<div className="grid gap-3">
									<CommonLabel htmlFor="nickname" required={true}>닉네임</CommonLabel>
									<Input
										id="nickname"
										{...register("nickname")}
										maxLength={12}
									/>
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
									<Input
										id="email"
										type="email"
										{...register("email")}
										maxLength={40}
									/>
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