import type {AxiosError} from "axios";

export default class HttpError {
  private readonly code: number;
  private readonly message: string;

  constructor(e: AxiosError) {
    this.code = e.response?.data.code ?? "500";
    this.message = e.response?.data.message ?? '서버와의 통신 중 오류가 발생했습니다.';

  }

  public getMessage() {
    return this.message;
  }

}
