// 목록 조회 요청
export interface BoardListRequest {
  page?: number;
  pageSize?: number;
  sortBy?: string;
  direction?: "ASC" | "DESC";
}

// 목록 조회 응답
export interface BoardListResponse {
  seq: number;
  title: string;
}

// 상세 조회 요청
export interface BoardDtlRequest {
  seq: number;
}

// 상세 조회 응답
export interface BoardDtlResponse {
  title: string;
  content: string;
}

// 저장/수정 요청
export interface BoardSaveRequest {
  seq?: number;
  title: string;
  author: string;
  content: string;
}
