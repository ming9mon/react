"use client";

import Image from 'next/image'
import logo from '@/assets/images/logo/logo-b.png'

export default function Header() {
	const goHome = () => {
		location.href="/"
	}

	return (
		<header className="flex items-center p-4">
			<Image
				src={logo}
				alt="로고"
				width={40}
				height={40}
				className="mr-3"
				onClick={goHome}
			/>
		</header>
	);
}