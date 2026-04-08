"use client";

import Image from "next/image";
import { Button } from "@/components/ui/button";
import googleLogo from "@/shared/assets/images/logo/google.png";
import naverLogo from "@/shared/assets/images/logo/naver.png";
import kakaoLogo from "@/shared/assets/images/logo/kakao.png";

const OAUTH_PROVIDERS = [
  { name: "google", logo: googleLogo },
  { name: "naver",  logo: naverLogo },
  { name: "kakao",  logo: kakaoLogo },
];

export default function OAuthButtons() {
  return (
    <div className="flex flex-col gap-2">
      {OAUTH_PROVIDERS.map(({ name, logo }) => (
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
  );
}
