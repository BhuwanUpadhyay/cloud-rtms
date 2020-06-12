import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InventoriesModule } from '../../../projects/rtms/inventories/src/public-api';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ApiComponent } from './api.component';
import { OrdersModule } from '../../../projects/rtms/orders/src/lib/orders.module';
import { ShippingsModule } from '../../../projects/rtms/shippings/src/lib/shippings.module';
import { PaymentsModule } from '../../../projects/rtms/payments/src/lib/payments.module';

const routes: Routes = [
  {
    path: '',
    component: ApiComponent,
    children: [
      {
        path: '',
        component: DashboardComponent,
        data: {
          breadcrumb: 'Dashboard',
        },
      },
      {
        path: 'inventories',
        loadChildren: () => InventoriesModule,
        data: {
          breadcrumb: 'Inventories',
        },
      },
      {
        path: 'orders',
        loadChildren: () => OrdersModule,
        data: {
          breadcrumb: 'Orders',
        },
      },
      {
        path: 'shippings',
        loadChildren: () => ShippingsModule,
        data: {
          breadcrumb: 'Shippings',
        },
      },
      {
        path: 'payments',
        loadChildren: () => PaymentsModule,
        data: {
          breadcrumb: 'Payments',
        },
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ApiRoutingModule {}
