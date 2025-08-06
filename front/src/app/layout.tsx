import "@/style/globals.css";
import Header from "@/components/common/header";

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className="flex flex-col h-screen">
        <Header />
        <main className="flex-1 flex items-center justify-center p-6 md:p-10">
          {children}
        </main>
      </body>
    </html>
  );
}
