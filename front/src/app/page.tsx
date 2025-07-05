import LoginForm from "@/features/login/components/loginForm";
import DashboardForm from "@/features/dashboard/components/dashboardForm";

export default async function HomePage() {
  const session = false

  if (!session) {
    // 로그인 안 된 상태
    return (
        <main className="flex items-center justify-center h-screen">
          <LoginForm />
        </main>
    )
  }

  // 로그인 된 상태
  return <DashboardForm />
}
