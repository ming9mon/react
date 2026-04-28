export interface MultilingualListRow {
  multilingualKey: string;
  multilingualType: string;
  multilingualVal: string;
  useYn: string;
}

export interface MultilingualValueDto {
  langCd: string;
  langNm?: string;
  multilingualVal: string;
}

export interface MultilingualDetail {
  multilingualKey: string;
  multilingualType: string;
  useYn: string;
  multilingualDesc: string;
  values: MultilingualValueDto[];
}

export interface MultilingualSaveRequest {
  multilingualKey: string;
  multilingualType: string;
  useYn: string;
  multilingualDesc?: string;
  values: { langCd: string; multilingualVal: string }[];
}

export interface MultilingualSearchParams {
  langCd: string;
  multilingualKey?: string;
  multilingualType?: string;
  multilingualVal?: string;
  pageNo: number;
  pageSize: number;
}
