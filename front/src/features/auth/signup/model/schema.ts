import { z } from "zod";

const IMAGE_MIME_TYPES = ["image/jpeg", "image/png", "image/gif"];

export const signupSchema = z.object({
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
  profileImg: z.any().optional().refine((files) => {
    if (!files || files.length === 0) return true;
    return IMAGE_MIME_TYPES.includes(files[0].type);
  }, { message: "jpg, png, gif 이미지 파일만 업로드 가능합니다." }),
}).refine((data) => data.passWd === data.passWdCheck, {
  path: ["passWdCheck"],
  message: "비밀번호가 일치하지 않습니다",
});

export type SignupFormData = z.infer<typeof signupSchema>;
