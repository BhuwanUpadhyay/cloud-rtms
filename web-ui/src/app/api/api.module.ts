import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApiRoutingModule } from './api-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LayoutsModule } from '../../../projects/rtms/layouts/src/lib/layouts.module';
import { ApiComponent } from './api.component';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { BreadcrumbComponent } from './breadcumb/breadcrumb.component';
import { FlexModule } from '@angular/flex-layout';
import { MatToolbarModule } from '@angular/material/toolbar';

@NgModule({
  declarations: [DashboardComponent, ApiComponent, BreadcrumbComponent],
  imports: [CommonModule, ApiRoutingModule, LayoutsModule, MatListModule, MatIconModule, FlexModule, MatToolbarModule],
})
export class ApiModule {}
