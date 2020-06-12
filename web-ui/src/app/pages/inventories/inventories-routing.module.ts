import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {InventoriesComponent} from './inventories.component';
import {InventoryFormComponent} from './inventory-form/inventory-form.component';
import {InventoryGridComponent} from './inventory-grid/inventory-grid.component';


const routes: Routes = [{
  path: '',
  component: InventoriesComponent,
  children: [
    {
      path: 'list',
      component: InventoryGridComponent,
    },
    {
      path: 'create',
      component: InventoryFormComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class InventoriesRoutingModule {
}
