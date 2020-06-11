import {Injectable} from '@angular/core';
import {Location} from '@angular/common';

import {AuthServerProvider} from '../auth/auth-session.service';
import {AUTH_API_URL, SERVER_API_URL} from '../app.constants';

@Injectable({providedIn: 'root'})
export class LoginService {
  constructor(private location: Location, private authServerProvider: AuthServerProvider) {
  }

  login(): void {
    // If you have configured multiple OIDC providers, then, you can update this URL to /login.
    // It will show a Spring Security generated login page with links to configured OIDC providers.
    location.href = `${AUTH_API_URL}${this.location.prepareExternalUrl('oauth2/authorization/keycloak')}`;
  }

  logout(): void {
    // this.authServerProvider.logout().subscribe((logout: Logout) => {
    //   let logoutUrl = logout.logoutUrl;
    //   const redirectUri = `${location.origin}${this.location.prepareExternalUrl('/')}`;
    //
    //   // if Keycloak, uri has protocol/openid-connect/token
    //   if (logoutUrl.includes('/protocol')) {
    //     logoutUrl = logoutUrl + '?redirect_uri=' + redirectUri;
    //   } else {
    //     // Okta
    //     logoutUrl = logoutUrl + '?id_token_hint=' + logout.idToken + '&post_logout_redirect_uri=' + redirectUri;
    //   }
    //   window.location.href = logoutUrl;
    // });
  }
}