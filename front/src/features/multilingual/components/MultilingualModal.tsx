"use client";

import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import CommonLabel from "@/components/common/CommonLabel";
import { MultilingualRow, MultilingualSaveRequest } from "../model/types";
import { MSG_TYPE_OPTIONS, LANG_OPTIONS, USE_YN_OPTIONS } from "../model/constants";

interface MultilingualModalProps {
  open: boolean;
  data?: MultilingualRow | null;
  onClose: () => void;
  onSave: (data: MultilingualSaveRequest) => void;
  onDelete?: (msgKey: string) => void;
}

export default function MultilingualModal({
  open,
  data,
  onClose,
  onSave,
  onDelete,
}: MultilingualModalProps) {
  const isEdit = !!data;

  const { register, handleSubmit, reset, formState: { errors } } = useForm<MultilingualSaveRequest>({
    defaultValues: { msgType: "LABEL", langCd: "ko", useYn: "Y" },
  });

  useEffect(() => {
    if (open) {
      reset(data ?? { msgType: "LABEL", langCd: "ko", useYn: "Y" });
    }
  }, [open, data, reset]);

  const handleClose = () => {
    reset();
    onClose();
  };

  return (
    <Dialog open={open} onOpenChange={(open) => !open && handleClose()}>
      <DialogContent className="w-full max-w-md">
        <DialogHeader>
          <DialogTitle>{isEdit ? "다국어 상세" : "다국어 등록"}</DialogTitle>
        </DialogHeader>

        <form onSubmit={handleSubmit(onSave)}>
          <div className="flex flex-col gap-4">
            {isEdit && (
              <div className="grid gap-2">
                <CommonLabel htmlFor="msgKey">다국어 코드</CommonLabel>
                <Input
                  id="msgKey"
                  {...register("msgKey")}
                  disabled
                  className="bg-gray-50 text-gray-500"
                />
              </div>
            )}

            <div className="grid gap-2">
              <CommonLabel htmlFor="msgType" required>타입</CommonLabel>
              <select
                id="msgType"
                {...register("msgType", { required: "타입을 선택해주세요" })}
                className="w-full h-9 rounded-md border border-input px-3 text-sm"
              >
                {MSG_TYPE_OPTIONS.map((opt) => (
                  <option key={opt.value} value={opt.value}>{opt.label}</option>
                ))}
              </select>
              {errors.msgType && <p className="text-sm text-red-500">{errors.msgType.message}</p>}
            </div>

            <div className="grid gap-2">
              <CommonLabel htmlFor="langCd" required>언어</CommonLabel>
              <select
                id="langCd"
                {...register("langCd", { required: "언어를 선택해주세요" })}
                className="w-full h-9 rounded-md border border-input px-3 text-sm"
              >
                {LANG_OPTIONS.map((opt) => (
                  <option key={opt.value} value={opt.value}>{opt.label}</option>
                ))}
              </select>
              {errors.langCd && <p className="text-sm text-red-500">{errors.langCd.message}</p>}
            </div>

            <div className="grid gap-2">
              <CommonLabel htmlFor="msgVal" required>텍스트</CommonLabel>
              <Input
                id="msgVal"
                {...register("msgVal", { required: "텍스트를 입력해주세요" })}
              />
              {errors.msgVal && <p className="text-sm text-red-500">{errors.msgVal.message}</p>}
            </div>

            <div className="grid gap-2">
              <CommonLabel htmlFor="msgDesc">설명</CommonLabel>
              <Input id="msgDesc" {...register("msgDesc")} />
            </div>

            <div className="grid gap-2">
              <CommonLabel htmlFor="useYn" required>사용여부</CommonLabel>
              <select
                id="useYn"
                {...register("useYn")}
                className="w-full h-9 rounded-md border border-input px-3 text-sm"
              >
                {USE_YN_OPTIONS.map((opt) => (
                  <option key={opt.value} value={opt.value}>{opt.label}</option>
                ))}
              </select>
            </div>

            <div className="flex justify-between pt-2">
              {isEdit && onDelete ? (
                <Button type="button" variant="destructive" onClick={() => onDelete(data.msgKey)}>
                  삭제
                </Button>
              ) : <span />}
              <div className="flex gap-2">
                <Button type="button" variant="outline" onClick={handleClose}>취소</Button>
                <Button type="submit">{isEdit ? "수정" : "저장"}</Button>
              </div>
            </div>
          </div>
        </form>
      </DialogContent>
    </Dialog>
  );
}
