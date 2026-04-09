import { get, post, del } from "@/shared/api/api";
import { ApiResponse } from "@/shared/types/apiResponse";
import { BoardListRequest, BoardListResponse, BoardDtlResponse, BoardSaveRequest } from "../model/types";
import { BOARD_API } from "../model/constants";

// 목록 조회
export const getBoardList = (params: BoardListRequest) =>
  get<ApiResponse<BoardListResponse[]>>(BOARD_API, { params });

// 상세 조회
export const getBoardDtl = (seq: number) =>
  get<ApiResponse<BoardDtlResponse>>(`${BOARD_API}/${seq}`);

// 저장
export const saveBoard = (data: BoardSaveRequest) =>
  post<BoardSaveRequest, ApiResponse<void>>(BOARD_API, data);

// 삭제
export const deleteBoard = (seq: number) =>
  del<ApiResponse<void>>(`${BOARD_API}/${seq}`);
