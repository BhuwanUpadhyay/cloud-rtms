import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InventoriesComponent } from './inventories.component';
import { InventorySaveComponent } from './inventory-save/inventory-save.component';
import { InventoriesListComponent } from './inventories-list/inventories-list.component';
import { InventoryByIdComponent } from './inventory-by-id/inventory-by-id.component';

const routes: Routes = [
  {
    path: '',
    component: InventoriesComponent,
    children: [
      {
        path: '',
        component: InventoriesListComponent,
      },
      {
        path: 'create',
        component: InventorySaveComponent,
      },
      {
        path: ':id/update',
        component: InventorySaveComponent,
      },
      {
        path: ':id',
        component: InventoryByIdComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class InventoriesRoutingModule {}
