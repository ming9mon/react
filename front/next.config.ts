import type { NextConfig } from "next";

const { NEXT_PUBLIC_API_PREFIX, NEXT_PUBLIC_BACKEND_URL } = process.env;

const nextConfig: NextConfig = {
	reactStrictMode: true,
	eslint: {
		// 빌드 시 eslint 실행 중단 설정 (CI 또는 Husky 사용 권장)
		ignoreDuringBuilds: false,
	},
	typescript: {
		// 빌드 중 타입 오류 무시
		ignoreBuildErrors: false,
	},
	webpack(config, { isServer }) {
		return config
	},
	async rewrites() {
		return [
			{
				source: `${NEXT_PUBLIC_API_PREFIX}/:path*`,       // 프론트 요청 주소
				destination: `${NEXT_PUBLIC_BACKEND_URL}/:path*`, // 백엔드 주소
			},
		]
	},
};

export default nextConfig;
