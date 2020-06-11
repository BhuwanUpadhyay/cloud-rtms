import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DescriptionModule} from './description/description.module';
import {ApiModule} from './api/api.module';
import {environment} from '../environments/environment';
import {UserRouteAccessService} from '../../projects/rtms/core/src/lib/auth/user-route-access-service';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => DescriptionModule,
  },
  {
    path: 'oauth2/authorization/keycloak',
    redirectTo: `${environment.authUrl}/oauth2/authorization/keycloak`
  },
  {
    path: 'rtms',
    loadChildren: () => ApiModule,
    data: {
      breadcrumb: 'RTMS',
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
