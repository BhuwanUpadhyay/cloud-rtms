import {NgModule} from '@angular/core';

import {InventoriesRoutingModule} from './inventories-routing.module';
import {InventoryFormComponent} from './inventory-form/inventory-form.component';
import {InventoriesComponent} from './inventories.component';
import {NbButtonModule, NbCardModule, NbStepperModule} from '@nebular/theme';
import {ReactiveFormsModule} from '@angular/forms';
import {ThemeModule} from '../../@theme/theme.module';
import {InventoryGridComponent} from './inventory-grid/inventory-grid.component';
import {Ng2SmartTableModule} from 'ng2-smart-table';

const components = [
  InventoriesComponent,
  InventoryFormComponent,
  InventoryGridComponent,
];

@NgModule({
  imports: [
    ReactiveFormsModule,
    NbCardModule,
    NbStepperModule,
    NbButtonModule,
    ThemeModule,
    InventoriesRoutingModule,
    Ng2SmartTableModule,
  ],
  declarations: [
    ...components,
  ],
})
export class InventoriesModule {
}
