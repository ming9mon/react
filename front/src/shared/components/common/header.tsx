"use client";

import Image from 'next/image'
import logo from '@/assets/images/logo/logo-b.png'
import Link from "next/link";

export default function Header() {
	const goHome = () => {
		location.href="/"
	}

	return (
		<header className="flex items-center justify-between p-4">
			<div className="flex items-center cursor-pointer" onClick={goHome}>
				<Image
					src={logo}
					alt="로고"
					width={40}
					height={40}
					className="mr-3"
					onClick={goHome}
				/>
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