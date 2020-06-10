import { KeycloakService } from 'keycloak-angular';

export function initializer(keycloak: KeycloakService): () => Promise<any> {
  return (): Promise<any> =>
    keycloak.init({
      config: {
        url: 'http://localhost:9990/auth',
        realm: 'rtms',
        clientId: 'web-ui',
      },
      initOptions: {
        onLoad: 'login-required',
        checkLoginIframe: false,
      },
      enableBearerInterceptor: true,
      bearerExcludedUrls: ['/assets', '/features'],
    });
}
