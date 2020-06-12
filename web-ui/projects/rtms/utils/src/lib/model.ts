export interface MessageResponse {
  lang: string;
  value: string;
}

export interface ErrorResponse {
  errorId: string;
  translations: Array<MessageResponse>;
}

export interface ServerResponse<T> {
  status: number;
  resources: T;
  errors: Array<ErrorResponse>;
}
