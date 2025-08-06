import Link from "next/link";
import {
	Accordion,
	AccordionItem,
	AccordionTrigger,
	AccordionContent,
} from "@/components/ui/accordion";
import {Home, Settings, Menu} from "lucide-react";
import {Sheet, SheetContent, SheetTrigger} from "@/components/ui/sheet";

export default function SideMenu() {
	return (
		<Sheet>
			{/* 햄버거 트리거 */}
			<SheetTrigger asChild>
				<button
					className="p-2 hover:bg-gray-100 rounded-md"
					aria-label="메뉴 열기"
				>
					<Menu size={24} />
				</button>
			</SheetTrigger>

			{/* 슬라이드 메뉴 */}
			<SheetContent side="left" className="w-74 pt-12">

				<nav className="flex flex-col space-y-2 px-4">
					{/* 홈 */}
					<Link
						href="/"
						className="flex items-center px-3 py-2 mb-4 hover:bg-gray-100 rounded"
					>
						<Home className="mr-2" /> 홈
					</Link>

					{/* 2deps */}
					<Accordion type="single" collapsible>
						<AccordionItem value="dashboard">
							<AccordionTrigger className="flex items-center justify-between px-3 py-2 hover:bg-gray-100 rounded">
								<span>2deps</span>
							</AccordionTrigger>
							<AccordionContent className="flex flex-col ml-6 space-y-1 mt-2">
								<Link
									href="/dashboard/overview"
									className="px-2 py-1 hover:bg-gray-100 rounded text-sm"
								>
									1
								</Link>
								<Link
									href="/dashboard/stats"
									className="px-2 py-1 hover:bg-gray-100 rounded text-sm"
								>
									2
								</Link>
								<Link
									href="/dashboard/realtime"
									className="px-2 py-1 hover:bg-gray-100 rounded text-sm"
								>
									3
								</Link>
							</AccordionContent>
						</AccordionItem>
					</Accordion>

					{/* 1단계: 설정 */}
					{/* 홈 */}
					<Link
						href="/"
						className="flex items-center px-3 py-2 mb-4 hover:bg-gray-100 rounded"
					>
						<Settings className="mr-2" />설정
					</Link>
				</nav>
			</SheetContent>
		</Sheet>
	);
}