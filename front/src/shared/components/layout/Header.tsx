"use client";

import Image from 'next/image'
import logo from '@/shared/assets/images/logo/logo-b.png'
import defaultProfile from '@/shared/assets/images/etc/user-default.png'
import Link from "next/link";
import SideMenu from "@/components/layout/SideMenu";
import { usePathname } from "next/navigation";
import { useUiStore } from "@/shared/store/uiStore";
import { useUserStore } from "@/shared/store/userStore";
import { useState, useRef, useEffect } from "react";

const AUTH_PATHS = ["/login"];

export default function Header() {
  const pathname = usePathname();
  const isAuthPage = AUTH_PATHS.includes(pathname);
  const { openLoginModal } = useUiStore();
  const { userInfo, clearUser, _hasHydrated } = useUserStore();
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleClickOutside = (e: MouseEvent) => {
      if (dropdownRef.current && !dropdownRef.current.contains(e.target as Node)) {
        setDropdownOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <header className="flex items-center justify-between px-6 py-3 bg-white border-b border-gray-200 shadow-sm shrink-0">
      <div className="flex items-center gap-2">
        {!isAuthPage && <SideMenu />}
        <div className="flex items-center cursor-pointer" onClick={() => location.href = "/"}>
          <Image src={logo} alt="로고" width={40} height={40} className="mr-3" />
        </div>
      </div>

      <div className="flex items-center space-x-2">
        {!_hasHydrated ? null : userInfo ? (
          <div className="relative" ref={dropdownRef}>
            <button
              onClick={() => setDropdownOpen((prev) => !prev)}
              className="flex items-center gap-2 px-3 py-2 text-sm font-medium rounded hover:bg-gray-100"
            >
              <Image
                src={userInfo.profilePicUrl || defaultProfile}
                alt="프로필"
                width={30}
                height={30}
                className="rounded-full object-cover"
              />
              <b>{userInfo.nickname}</b>
            </button>

            {dropdownOpen && (
              <div className="absolute right-0 mt-1 w-36 bg-white border border-gray-200 rounded shadow-md z-50">
                <Link
                  href="/mypage"
                  className="block px-4 py-2 text-sm hover:bg-gray-100"
                  onClick={() => setDropdownOpen(false)}
                >
                  회원정보
                </Link>
                <button
                  onClick={() => { clearUser(); setDropdownOpen(false); }}
                  className="w-full text-left px-4 py-2 text-sm hover:bg-gray-100"
                >
                  로그아웃
                </button>
              </div>
            )}
          </div>
        ) : (
          <>
            <button
              onClick={openLoginModal}
              className="px-4 py-2 text-sm font-medium rounded hover:bg-gray-100"
            >
              로그인
            </button>
            <Link href="/signup">
              <button className="px-4 py-2 text-sm font-medium rounded hover:bg-gray-100">
                회원가입
              </button>
            </Link>
          </>
        )}
      </div>
    </header>
  );
}
