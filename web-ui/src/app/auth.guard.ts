import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  private authenticated: boolean;

  constructor(private router: Router, private login: LoginService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    return new Promise(async (resolve, reject) => {
      try {
        this.authenticated = await this.login.isLoggedIn();
        // const result = await this.isAccessAllowed(route, state);
        resolve(this.authenticated);
      } catch (error) {
        reject('An error happened during access validation. Details:' + error);
      }
    });
  }
}
