import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';
import { AlreadyExistsResponse, authBasePath, LoginRequest, LoginResponse, SignUpRequest } from '../model';
import { Optional } from '../../../../utils/src/lib/optional';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  public isAuthenticated = new BehaviorSubject<boolean>(false);

  constructor(@Inject(authBasePath) private basePath: string, private http: HttpClient, private router: Router) {}

  async checkAuthenticated() {
    const authenticated = await this.http.get<AlreadyExistsResponse>(`${this.basePath}/iam/already-exists`).toPromise();
    this.isAuthenticated.next(authenticated.exists);
    return Optional.of(authenticated)
      .map((v) => v.exists)
      .orElseGet(() => false);
  }

  async login(u: string, p: string) {
    const request: LoginRequest = {
      username: u,
      password: p,
    };

    const response = await this.http.post<LoginResponse>(`${this.basePath}/iam/login`, request).toPromise();

    console.log(response);
    if (response.status !== 'SUCCESS') {
      throw Error('We cannot handle the ' + response.status + ' status');
    }
    this.isAuthenticated.next(true);
  }

  async logout(redirect: string) {
    await this.http.get<LoginResponse>(`${this.basePath}/iam/logout`).toPromise();
    this.isAuthenticated.next(false);
    await this.router.navigate([redirect]);
  }

  async signUp(n: string, u: string, p: string, e: string) {
    const request: SignUpRequest = {
      name: n,
      username: u,
      password: p,
      email: e,
    };
    await this.http.post<LoginResponse>(`${this.basePath}/iam/sign-up`, request).toPromise();
    this.isAuthenticated.next(false);
  }
}
