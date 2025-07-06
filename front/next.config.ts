import type { NextConfig } from "next";

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
		// 필요 시 커스텀 webpack 구성
		return config
	},
	async rewrites() {
		return [
			{
				source: '/api/:path*',       // 프론트 요청 주소
				destination: 'http://localhost:8080/:path*', // 백엔드 주소
			},
		]
	},
};

export default nextConfig;
