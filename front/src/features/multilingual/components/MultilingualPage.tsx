"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import DataTableLayout from "@/components/common/DataTableLayout";
import DataTable from "@/components/common/DataTable";
import MultilingualModal from "./MultilingualModal";
import { MultilingualRow, MultilingualSaveRequest } from "../model/types";
import { useApiClient } from "@/shared/hooks/useApiClient";
import { MULTILINGUAL_API, multilingualColumns } from "../model/constants";

export default function MultilingualPage() {
  const { search, post, save, del } = useApiClient<MultilingualRow[]>();
  const [data, setData] = useState<MultilingualRow[]>([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedRow, setSelectedRow] = useState<MultilingualRow | null>(null);

  const fetchList = async () => {
    const { body } = await search({ url: MULTILINGUAL_API });
    if (body) setData(body);
  };

  const handleOpen = (row?: MultilingualRow) => {
    setSelectedRow(row ?? null);
    setModalOpen(true);
  };

  const handleClose = () => {
    setSelectedRow(null);
    setModalOpen(false);
  };

  const handleSave = async (formData: MultilingualSaveRequest) => {
    if (selectedRow) {
      await save({ url: `${MULTILINGUAL_API}/${formData.msgKey}`, body: formData });
    } else {
      await post({ url: MULTILINGUAL_API, body: formData });
    }
    handleClose();
    fetchList();
  };

  const handleDelete = async (msgKey: string) => {
    await del({ url: `${MULTILINGUAL_API}/${msgKey}` });
    handleClose();
    fetchList();
  };

  return (
    <>
      <DataTableLayout
        title="다국어 관리"
        buttons={<Button onClick={() => handleOpen()}>추가</Button>}
      >
        <DataTable
          columns={multilingualColumns}
          data={data}
          onRowDoubleClick={handleOpen}
        />
      </DataTableLayout>

      <MultilingualModal
        open={modalOpen}
        data={selectedRow}
        onClose={handleClose}
        onSave={handleSave}
        onDelete={handleDelete}
      />
    </>
  );
}
