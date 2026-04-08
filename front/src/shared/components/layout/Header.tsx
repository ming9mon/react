"use client";

import Image from 'next/image'
import logo from '@/shared/assets/images/logo/logo-b.png'
import Link from "next/link";
import SideMenu from "@/components/layout/SideMenu";
import {usePathname} from "next/navigation";

const AUTH_PATHS = ["/login", "/signup"];

export default function Header() {
	const pathname = usePathname();
	const isAuthPage = AUTH_PATHS.includes(pathname);

	const goHome = () => {
		location.href="/"
	}

	return (
		<header className="flex items-center justify-between p-4">
			<div className="flex items-center gap-2">
				{/* 사이드바 토글 버튼 */}
				{!isAuthPage && <SideMenu />}

				{/* 로고 */}
				<div
					className="flex items-center cursor-pointer"
					onClick={goHome}
				>
					<Image
						src={logo}
						alt="로고"
						width={40}
						height={40}
						className="mr-3"
					/>
				</div>
			</div>

			{/* 로그인/회원가입 버튼 */}
			<div className="flex space-x-2">
				<Link href="/login">
					<button className="px-4 py-2 text-sm font-medium rounded hover:bg-gray-100">
						로그인
					</button>
				</Link>
				<Link href="/signup">
					<button className="px-4 py-2 text-sm font-medium rounded hover:bg-gray-100">
						회원가입
					</button>
				</Link>
			</div>
		</header>
	);
}
