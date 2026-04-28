import { ColumnDef } from "@tanstack/react-table";
import { MultilingualListRow } from "./types";

export const MULTILINGUAL_API = "/admin/multilingual";

export const MULTILINGUAL_TYPE_OPTIONS = [
  { value: "S", label: "SCREEN" },
  { value: "W", label: "WORD" },
  { value: "M", label: "MESSAGE" },
  { value: "E", label: "ERROR" },
];

export const USE_YN_OPTIONS = [
  { value: "Y", label: "사용" },
  { value: "N", label: "미사용" },
];

export const multilingualColumns: ColumnDef<MultilingualListRow>[] = [
  { accessorKey: "multilingualKey",  header: "다국어 코드" },
  { accessorKey: "multilingualType", header: "타입" },
  { accessorKey: "multilingualVal",  header: "텍스트" },
  {
    accessorKey: "useYn",
    header: "사용여부",
    cell: ({ getValue }) => (getValue() === "Y" ? "사용" : "미사용"),
  },
];
