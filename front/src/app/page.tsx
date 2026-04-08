import MainForm from "@/features/dashboard/components/MainForm";
import {redirect} from "next/navigation";

export default async function HomePage() {
  const session = false

  if (!session) {
    redirect("/login")
  }

  return <MainForm />
}
