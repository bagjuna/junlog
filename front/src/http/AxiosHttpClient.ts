import axios, {AxiosError, type AxiosInstance, type AxiosRequestConfig, type AxiosResponse} from "axios";
import HttpError from "@/http/HttpError.ts";
import {ElMessage} from "element-plus";
import router from "@/router";
import {singleton} from "tsyringe";

export type HttpRequestConfig = {
  method?: 'GET' | 'POST' | 'PATCH' | 'DELETE';
  path: string;
  params?: any
  body?: any
};

@singleton()
export default class AxiosHttpClient {
  private readonly client: AxiosInstance = axios.create({
    timeout: 3000,
    timeoutErrorMessage: '시간이 만료됐습니다.',
  });

  public async request(config: HttpRequestConfig) {
    return this.client.request(
      {
        url: config.path,
        method: config.method,
        params: config.params,
        data: config.body
      })
      .then((response: AxiosResponse) => {
        return response.data;
      })
      .catch((e: AxiosError) => {
      return Promise.reject(new HttpError(e));
    })
  }


}
