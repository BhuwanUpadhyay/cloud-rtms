import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DescriptionModule } from './description/description.module';
import { ApiModule } from './api/api.module';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => DescriptionModule,
  },
  {
    path: 'rtms',
    loadChildren: () => ApiModule,
    data: {
      breadcrumb: 'RTMS',
    },
    canActivate: [AuthGuard],
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
export class AppRoutingModule {}
