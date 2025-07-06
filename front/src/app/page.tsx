import MainForm from "@/features/main/components/mainForm";
import {redirect} from "next/navigation";

export default async function HomePage() {
  const session = false

  if (!session) {
    redirect("/login")
  }

  return <MainForm />
}
