"use client";

import { useUiStore } from "@/shared/store/uiStore";

export default function LoadingBar() {
  const isLoading = useUiStore((s) => s.loadingCount > 0);

  if (!isLoading) return null;

  return (
    <div className="fixed top-0 left-0 right-0 z-[9999] h-[3px] overflow-hidden">
      <div className="h-full bg-primary animate-loading-bar" />
    </div>
  );
}
