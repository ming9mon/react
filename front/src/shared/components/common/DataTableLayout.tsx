"use client";

import { ReactNode } from "react";

interface DataTableLayoutProps {
  title: string;
  buttons?: ReactNode;
  children: ReactNode;
}

export default function DataTableLayout({
  title,
  buttons,
  children,
}: DataTableLayoutProps) {
  return (
    <div className="flex flex-col h-full w-full min-w-[640px] p-6 gap-3 overflow-hidden">
      {/* 네비게이션 바 */}
      <div className="flex items-center justify-between border-b border-gray-200 pb-3 shrink-0">
        <h1 className="text-lg font-semibold text-gray-800">{title}</h1>
      </div>

      {/* 버튼 영역 */}
      {buttons && (
        <div className="flex items-center justify-end gap-2 shrink-0">{buttons}</div>
      )}

      {/* 테이블 영역 */}
      <div className="flex-1 flex flex-col min-h-0">{children}</div>
    </div>
  );
}
