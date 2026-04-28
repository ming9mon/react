"use client";

import { useEffect } from "react";
import { useForm, useFieldArray } from "react-hook-form";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import CommonLabel from "@/components/common/CommonLabel";
import { MultilingualDetail, MultilingualSaveRequest } from "../model/types";
import { MULTILINGUAL_TYPE_OPTIONS, USE_YN_OPTIONS } from "../model/constants";
import { useInitStore } from "@/shared/store/initStore";

interface MultilingualModalProps {
  open: boolean;
  detail?: MultilingualDetail | null;
  onClose: () => void;
  onSave: (data: MultilingualSaveRequest) => void;
  onDelete?: (key: string) => void;
}

export default function MultilingualModal({
  open,
  detail,
  onClose,
  onSave,
  onDelete,
}: MultilingualModalProps) {
  const isEdit = !!detail;
  const langCdList = useInitStore((s) => s.langCdList);

  const { register, handleSubmit, reset, control, formState: { errors } } = useForm<MultilingualSaveRequest>({
    defaultValues: { multilingualType: "S", useYn: "Y", values: [] },
  });

  const { fields, replace } = useFieldArray({ control, name: "values" });

  useEffect(() => {
    if (!open) return;

    if (detail) {
      const values = langCdList.map((lang) => {
        const found = detail.values.find((v) => v.langCd === lang.code);
        return { langCd: lang.code, multilingualVal: found?.multilingualVal ?? "" };
      });
      reset({ ...detail, values });
    } else {
      const values = langCdList.map((lang) => ({ langCd: lang.code, multilingualVal: "" }));
      reset({ multilingualType: "S", useYn: "Y", multilingualKey: "", multilingualDesc: "", values });
    }
  }, [open, detail, langCdList, reset]);

  const handleClose = () => {
    reset();
    onClose();
  };

  return (
    <Dialog open={open} onOpenChange={(open) => !open && handleClose()}>
      <DialogContent className="w-full max-w-lg max-h-[90vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle>{isEdit ? "다국어 상세" : "다국어 등록"}</DialogTitle>
        </DialogHeader>

        <form onSubmit={handleSubmit(onSave)}>
          <div className="flex flex-col gap-4">

            {/* 다국어 코드 */}
            <div className="grid gap-2">
              <CommonLabel htmlFor="multilingualKey" required>다국어 코드</CommonLabel>
              <Input
                id="multilingualKey"
                {...register("multilingualKey", { required: "다국어 코드를 입력해주세요" })}
                maxLength={6}
                disabled={isEdit}
                className={isEdit ? "bg-gray-50 text-gray-500" : ""}
                placeholder="최대 6자"
              />
              {errors.multilingualKey && <p className="text-sm text-red-500">{errors.multilingualKey.message}</p>}
            </div>

            {/* 타입 / 사용여부 */}
            <div className="grid grid-cols-2 gap-4">
              <div className="grid gap-2">
                <CommonLabel htmlFor="multilingualType" required>타입</CommonLabel>
                <select
                  id="multilingualType"
                  {...register("multilingualType", { required: true })}
                  className="w-full h-9 rounded-md border border-input px-3 text-sm"
                >
                  {MULTILINGUAL_TYPE_OPTIONS.map((opt) => (
                    <option key={opt.value} value={opt.value}>{opt.label}</option>
                  ))}
                </select>
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
            </div>

            {/* 설명 */}
            <div className="grid gap-2">
              <CommonLabel htmlFor="multilingualDesc">설명</CommonLabel>
              <Input id="multilingualDesc" {...register("multilingualDesc")} maxLength={500} />
            </div>

            {/* 언어별 번역값 */}
            {fields.length > 0 && (
              <div className="grid gap-3">
                <CommonLabel htmlFor="values">언어별 번역</CommonLabel>
                <div className="flex flex-col gap-2 rounded border border-gray-100 p-3 bg-gray-50">
                  {fields.map((field, index) => {
                    const lang = langCdList.find((l) => l.code === field.langCd);
                    return (
                      <div key={field.id} className="grid grid-cols-[80px_1fr] items-center gap-3">
                        <span className="text-sm text-gray-600">{lang?.text ?? field.langCd}</span>
                        <Input
                          {...register(`values.${index}.multilingualVal`)}
                        />
                        <input type="hidden" {...register(`values.${index}.langCd`)} />
                      </div>
                    );
                  })}
                </div>
              </div>
            )}

            {/* 버튼 */}
            <div className="flex justify-between pt-2">
              {isEdit && onDelete ? (
                <Button type="button" variant="destructive" onClick={() => onDelete(detail.multilingualKey)}>
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
