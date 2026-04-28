"use client";

import {
  ColumnDef,
  flexRender,
  getCoreRowModel,
  getPaginationRowModel,
  useReactTable,
} from "@tanstack/react-table";
import { Button } from "@/components/ui/button";
import { ChevronLeft, ChevronRight, ChevronsLeft, ChevronsRight } from "lucide-react";

interface ServerPagination {
  totalRows: number;
  pageIndex: number;
  pageSize: number;
  onPageChange: (page: number) => void;
  onPageSizeChange: (size: number) => void;
}

interface DataTableProps<TData> {
  columns: ColumnDef<TData, any>[];
  data: TData[];
  onRowDoubleClick?: (row: TData) => void;
  pageSize?: number;
  serverPagination?: ServerPagination;
}

export default function DataTable<TData>({
  columns,
  data,
  onRowDoubleClick,
  pageSize = 10,
  serverPagination,
}: DataTableProps<TData>) {
  const isServer = !!serverPagination;

  const table = useReactTable({
    data,
    columns,
    getCoreRowModel: getCoreRowModel(),
    ...(isServer
      ? {
          manualPagination: true,
          pageCount: Math.ceil(serverPagination.totalRows / serverPagination.pageSize),
          state: {
            pagination: {
              pageIndex: serverPagination.pageIndex,
              pageSize: serverPagination.pageSize,
            },
          },
          onPaginationChange: (updater) => {
            const prev = { pageIndex: serverPagination.pageIndex, pageSize: serverPagination.pageSize };
            const next = typeof updater === "function" ? updater(prev) : updater;
            if (next.pageIndex !== prev.pageIndex) serverPagination.onPageChange(next.pageIndex);
            if (next.pageSize !== prev.pageSize) serverPagination.onPageSizeChange(next.pageSize);
          },
        }
      : {
          getPaginationRowModel: getPaginationRowModel(),
          initialState: { pagination: { pageSize } },
        }),
  });

  const pagination = isServer
    ? { pageIndex: serverPagination.pageIndex, pageSize: serverPagination.pageSize }
    : table.getState().pagination;

  const totalRows = isServer ? serverPagination.totalRows : data.length;
  const pageCount = isServer
    ? Math.ceil(totalRows / pagination.pageSize)
    : table.getPageCount();
  const from = totalRows === 0 ? 0 : pagination.pageIndex * pagination.pageSize + 1;
  const to = Math.min((pagination.pageIndex + 1) * pagination.pageSize, totalRows);
  const canPrev = pagination.pageIndex > 0;
  const canNext = pagination.pageIndex < pageCount - 1;

  const gotoPage = (page: number) => {
    if (isServer) serverPagination.onPageChange(page);
    else table.setPageIndex(page);
  };

  const changeSize = (size: number) => {
    if (isServer) serverPagination.onPageSizeChange(size);
    else table.setPageSize(size);
  };

  return (
    <div className="flex flex-col h-full min-h-0">
      {/* 테이블 스크롤 영역 */}
      <div className="flex-1 overflow-auto min-h-0 rounded border border-gray-200">
        <table className="w-full text-sm min-w-[600px]">
          <thead className="bg-gray-50 text-gray-600 sticky top-0 z-10">
            {table.getHeaderGroups().map((headerGroup) => (
              <tr key={headerGroup.id}>
                {headerGroup.headers.map((header) => (
                  <th
                    key={header.id}
                    className="px-4 py-3 text-left font-medium border-b border-gray-200 bg-gray-50"
                  >
                    {flexRender(header.column.columnDef.header, header.getContext())}
                  </th>
                ))}
              </tr>
            ))}
          </thead>
          <tbody>
            {table.getRowModel().rows.length === 0 ? (
              <tr>
                <td colSpan={columns.length} className="px-4 py-8 text-center text-gray-400">
                  데이터가 없습니다.
                </td>
              </tr>
            ) : (
              table.getRowModel().rows.map((row) => (
                <tr
                  key={row.id}
                  className="border-b border-gray-100 hover:bg-gray-50 cursor-pointer"
                  onDoubleClick={() => onRowDoubleClick?.(row.original)}
                >
                  {row.getVisibleCells().map((cell) => (
                    <td key={cell.id} className="px-4 py-3">
                      {flexRender(cell.column.columnDef.cell, cell.getContext())}
                    </td>
                  ))}
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {/* 페이징 영역 */}
      <div className="flex items-center justify-between px-1 py-3 shrink-0">
        <span className="text-sm text-gray-500">
          {totalRows > 0 ? `${from} - ${to} / 총 ${totalRows}건` : "0건"}
        </span>

        <div className="flex items-center gap-1">
          <Button variant="outline" size="icon" className="h-8 w-8" onClick={() => gotoPage(0)} disabled={!canPrev}>
            <ChevronsLeft className="h-4 w-4" />
          </Button>
          <Button variant="outline" size="icon" className="h-8 w-8" onClick={() => gotoPage(pagination.pageIndex - 1)} disabled={!canPrev}>
            <ChevronLeft className="h-4 w-4" />
          </Button>
          <span className="text-sm px-2">{pagination.pageIndex + 1} / {pageCount || 1}</span>
          <Button variant="outline" size="icon" className="h-8 w-8" onClick={() => gotoPage(pagination.pageIndex + 1)} disabled={!canNext}>
            <ChevronRight className="h-4 w-4" />
          </Button>
          <Button variant="outline" size="icon" className="h-8 w-8" onClick={() => gotoPage(pageCount - 1)} disabled={!canNext}>
            <ChevronsRight className="h-4 w-4" />
          </Button>
        </div>

        <select
          className="text-sm border border-gray-200 rounded px-2 py-1"
          value={pagination.pageSize}
          onChange={(e) => changeSize(Number(e.target.value))}
        >
          {[10, 20, 50].map((size) => (
            <option key={size} value={size}>{size}건</option>
          ))}
        </select>
      </div>
    </div>
  );
}
