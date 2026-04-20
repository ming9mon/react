export interface MultilingualRow {
  msgKey: string;
  msgType: string;
  langCd: string;
  msgVal: string;
  msgDesc: string;
  useYn: string;
}

export interface MultilingualSaveRequest {
  msgKey?: string;
  msgType: string;
  langCd: string;
  msgVal: string;
  msgDesc: string;
  useYn: string;
}
