import { ColumnDef } from "@tanstack/react-table";
import { MultilingualRow } from "./types";

export const MULTILINGUAL_API = "/admin/multilingual";

export const multilingualColumns: ColumnDef<MultilingualRow>[] = [
  { accessorKey: "msgKey", header: "다국어 코드" },
  { accessorKey: "msgType", header: "타입" },
  { accessorKey: "langCd", header: "언어" },
  { accessorKey: "msgVal", header: "텍스트" },
  { accessorKey: "msgDesc", header: "설명" },
  {
    accessorKey: "useYn",
    header: "사용여부",
    cell: ({ getValue }) => (getValue() === "Y" ? "사용" : "미사용"),
  },
];

export const MSG_TYPE_OPTIONS = [
  { value: "LABEL", label: "라벨" },
  { value: "MESSAGE", label: "메시지" },
  { value: "BUTTON", label: "버튼" },
  { value: "MENU", label: "메뉴" },
];

export const LANG_OPTIONS = [
  { value: "ko", label: "한국어" },
  { value: "en", label: "영어" },
  { value: "ja", label: "일본어" },
];

export const USE_YN_OPTIONS = [
  { value: "Y", label: "사용" },
  { value: "N", label: "미사용" },
];
