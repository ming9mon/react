"use client";

import { useUiStore } from "@/shared/store/uiStore";

export default function LoadingBar() {
  const isLoading = useUiStore((s) => s.loadingCount > 0);

  if (!isLoading) return null;

  return (
    <div className="fixed inset-0 z-[9999] flex items-center justify-center bg-black/5">
      <div className="w-12 h-12 rounded-full border-4 border-gray-200 border-t-primary animate-spin" />
    </div>
  );
}
