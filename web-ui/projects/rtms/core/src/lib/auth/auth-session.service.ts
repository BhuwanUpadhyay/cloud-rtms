import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import {SERVER_API_URL} from '../app.constants';

@Injectable({ providedIn: 'root' })
export class AuthServerProvider {
  constructor(private http: HttpClient) {}

  logout(): Observable<any> {
    return this.http.post<any>(SERVER_API_URL + 'api/logout', {});
  }
}
