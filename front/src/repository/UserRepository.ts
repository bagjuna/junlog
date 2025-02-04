import HttpRepository from "@/repository/HttpRepository.ts";
import type Login from "@/entity/user/Login.ts";
import {inject, singleton} from "tsyringe";

@singleton()
export default class UserRepository {

  constructor(@inject(HttpRepository) private readonly httpRepository: HttpRepository) {
  }

  public login(request: Login) {
    return this.httpRepository.post({
      path: '/api/auth/login',
      body: request
    });
  }

};
