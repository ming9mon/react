import "@/shared/styles/globals.css";
import Header from "@/components/layout/Header";
import { CommonDialogProvider } from "@/components/common/CommonDialog";
import LoginModal from "@/features/auth/login/components/LoginModal";

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className="flex flex-col h-screen">
        <CommonDialogProvider>
          <Header />
          <main className="flex-1 overflow-hidden min-w-0 flex flex-col">
            {children}
          </main>
          <LoginModal />
        </CommonDialogProvider>
      </body>
    </html>
  );
}
