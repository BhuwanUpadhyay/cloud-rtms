import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private http: HttpClient) {}

  async isLoggedIn() {
    return this.http
      .get<{ exists: boolean }>(`${environment.gateway}/iam/already-exists`)
      .pipe(map((value) => value.exists))
      .toPromise();
  }
}
