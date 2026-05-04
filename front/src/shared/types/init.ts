export interface ComboDto {
  code: string;
  text: string;
}

export interface InitResponse {
  langCdList: ComboDto[];
  multilingual: Record<string, string>;
}
