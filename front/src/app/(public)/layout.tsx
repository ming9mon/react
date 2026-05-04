export default function ProtectedLayout({ children }: { children: React.ReactNode }) {
    return (
        <div className="h-full w-full flex flex-col overflow-hidden">
            {children}
        </div>
    )
}