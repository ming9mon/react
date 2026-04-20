import Link from "next/link";
import {
	Accordion,
	AccordionItem,
	AccordionTrigger,
	AccordionContent,
} from "@/components/ui/accordion";
import { Home, Settings, Menu, Languages } from "lucide-react";
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet";

export default function SideMenu() {
	return (
		<Sheet>
			<SheetTrigger asChild>
				<button
					className="p-2 hover:bg-gray-100 rounded-md"
					aria-label="메뉴 열기"
				>
					<Menu size={24} />
				</button>
			</SheetTrigger>

			<SheetContent side="left" className="w-64 pt-12">
				<nav className="flex flex-col space-y-1 px-4">

					{/* 홈 */}
					<Link href="/" className="flex items-center gap-2 px-3 py-2 hover:bg-gray-100 rounded text-sm">
						<Home size={16} /> 홈
					</Link>

					{/* 관리자 */}
					<Accordion type="single" collapsible>
						<AccordionItem value="admin" className="border-none">
							<AccordionTrigger className="flex items-center gap-2 px-3 py-2 hover:bg-gray-100 rounded text-sm">
								<div className="flex items-center gap-2">
									<Settings size={16} /> 관리자
								</div>
							</AccordionTrigger>
							<AccordionContent className="flex flex-col ml-6 space-y-1 mt-1">
								<Link href="/admin/multilingual" className="flex items-center gap-2 px-3 py-2 hover:bg-gray-100 rounded text-sm">
									<Languages size={14} /> 다국어 관리
								</Link>
							</AccordionContent>
						</AccordionItem>
					</Accordion>

				</nav>
			</SheetContent>
		</Sheet>
	);
}
