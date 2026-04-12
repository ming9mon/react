import { z } from "zod";

export const loginSchema = z.object({
  userId: z.string().min(4, "아이디는 최소 4자리 이상입니다").max(20, "아이디는 최대 20자리 이하입니다"),
  passWd: z.string().min(8, "비밀번호는 최소 8자리 이상입니다").max(20, "비밀번호는 최대 20자리 이하입니다"),
});

export type LoginFormData = z.infer<typeof loginSchema>;
