"use client";

import { useCallback, useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import DataTableLayout from "@/components/common/DataTableLayout";
import DataTable from "@/components/common/DataTable";
import MultilingualModal from "./MultilingualModal";
import {
  MultilingualDetail,
  MultilingualListRow,
  MultilingualSaveRequest,
  MultilingualSearchParams,
} from "../model/types";
import { MULTILINGUAL_TYPE_OPTIONS, multilingualColumns } from "../model/constants";
import { useApiClient } from "@/shared/hooks/useApiClient";
import { useInitStore } from "@/shared/store/initStore";

const DEFAULT_PAGE_SIZE = 10;

export default function MultilingualPage() {
  const { search, post, del } = useApiClient();
  const langCdList = useInitStore((s) => s.langCdList);

  const [data, setData]           = useState<MultilingualListRow[]>([]);
  const [totalRows, setTotalRows] = useState(0);
  const [pageIndex, setPageIndex] = useState(0);
  const [pageSize, setPageSize]   = useState(DEFAULT_PAGE_SIZE);

  const [searchParams, setSearchParams] = useState<Omit<MultilingualSearchParams, "pageNo" | "pageSize">>({
    langCd: "",
    multilingualKey: "",
    multilingualType: "",
    multilingualVal: "",
  });

  const [modalOpen, setModalOpen] = useState(false);
  const [detail, setDetail]       = useState<MultilingualDetail | null>(null);

  // langCdList 로드되면 첫 번째 언어 자동 선택
  useEffect(() => {
    if (langCdList.length > 0 && !searchParams.langCd) {
      setSearchParams((prev) => ({ ...prev, langCd: langCdList[0].code }));
    }
  }, [langCdList, searchParams.langCd]);

  // langCd 세팅되면 목록 조회
  useEffect(() => {
    if (searchParams.langCd) fetchList(0);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [searchParams.langCd]);

  const fetchList = useCallback(async (page = 0) => {
    const { body } = await search({
      body: { ...searchParams, pageNo: page, pageSize },
    });
    if (body) {
      setData(body.list);
      setTotalRows(body.totalElements);
      setPageIndex(page);
    }
  }, [search, searchParams, pageSize]);

  const handleSearch = () => fetchList(0);

  const handleOpenAdd = () => {
    setDetail(null);
    setModalOpen(true);
  };

  const handleRowDoubleClick = async (row: MultilingualListRow) => {
    const { body } = await search({ subfixPath: row.multilingualKey });
    if (body) {
      setDetail(body as MultilingualDetail);
      setModalOpen(true);
    }
  };

  const handleSave = async (formData: MultilingualSaveRequest) => {
    await post({ body: formData });
    setModalOpen(false);
    fetchList(0);
  };

  const handleDelete = async (key: string) => {
    await del({ subfixPath: key });
    setModalOpen(false);
    fetchList(0);
  };

  return (
    <>
      <DataTableLayout
        title="다국어 관리"
        search={
          <div className="rounded-lg border border-gray-200 bg-gray-50 px-5 py-4">
            <div className="flex flex-wrap gap-x-6 gap-y-3 items-end">
              <div className="flex flex-col gap-1">
                <span className="text-xs font-medium text-gray-500">언어</span>
                <select
                  className="h-9 rounded-md border border-gray-300 bg-white px-3 text-sm min-w-[110px] focus:outline-none focus:ring-2 focus:ring-primary/30"
                  value={searchParams.langCd}
                  onChange={(e) => setSearchParams((p) => ({ ...p, langCd: e.target.value }))}
                >
                  {langCdList.map((lang) => (
                    <option key={lang.code} value={lang.code}>{lang.text}</option>
                  ))}
                </select>
              </div>

              <div className="flex flex-col gap-1">
                <span className="text-xs font-medium text-gray-500">타입</span>
                <select
                  className="h-9 rounded-md border border-gray-300 bg-white px-3 text-sm min-w-[120px] focus:outline-none focus:ring-2 focus:ring-primary/30"
                  value={searchParams.multilingualType}
                  onChange={(e) => setSearchParams((p) => ({ ...p, multilingualType: e.target.value }))}
                >
                  <option value="">전체</option>
                  {MULTILINGUAL_TYPE_OPTIONS.map((opt) => (
                    <option key={opt.value} value={opt.value}>{opt.label}</option>
                  ))}
                </select>
              </div>

              <div className="flex flex-col gap-1">
                <span className="text-xs font-medium text-gray-500">다국어 코드</span>
                <Input
                  className="h-9 min-w-[160px] bg-white"
                  value={searchParams.multilingualKey}
                  onChange={(e) => setSearchParams((p) => ({ ...p, multilingualKey: e.target.value }))}
                  onKeyDown={(e) => e.key === "Enter" && handleSearch()}
                />
              </div>

              <div className="flex flex-col gap-1">
                <span className="text-xs font-medium text-gray-500">텍스트</span>
                <Input
                  className="h-9 min-w-[180px] bg-white"
                  value={searchParams.multilingualVal}
                  onChange={(e) => setSearchParams((p) => ({ ...p, multilingualVal: e.target.value }))}
                  onKeyDown={(e) => e.key === "Enter" && handleSearch()}
                />
              </div>
            </div>
          </div>
        }
        buttons={
          <>
            <Button variant="outline" onClick={handleSearch}>조회</Button>
            <Button onClick={handleOpenAdd}>추가</Button>
          </>
        }
      >
        <DataTable
          columns={multilingualColumns}
          data={data}
          onRowDoubleClick={handleRowDoubleClick}
          serverPagination={{
            totalRows,
            pageIndex,
            pageSize,
            onPageChange: (page) => fetchList(page),
            onPageSizeChange: (size) => { setPageSize(size); fetchList(0); },
          }}
        />
      </DataTableLayout>

      <MultilingualModal
        open={modalOpen}
        detail={detail}
        onClose={() => setModalOpen(false)}
        onSave={handleSave}
        onDelete={handleDelete}
      />
    </>
  );
}
