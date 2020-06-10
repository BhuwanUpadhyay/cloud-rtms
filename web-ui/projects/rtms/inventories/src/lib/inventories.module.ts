import { NgModule } from '@angular/core';
import { InventoriesComponent } from './inventories.component';
import { CommonModule } from '@angular/common';
import { InventoriesRoutingModule } from './inventories-routing.module';
import { InventoriesListComponent } from './inventories-list/inventories-list.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { FlexModule } from '@angular/flex-layout';
import { UtilsModule } from '../../../utils/src/lib/utils.module';
import { InventorySaveComponent } from './inventory-save/inventory-save.component';
import { InventoryByIdComponent } from './inventory-by-id/inventory-by-id.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatStepperModule } from '@angular/material/stepper';
import { CdkTableModule } from '@angular/cdk/table';

@NgModule({
  declarations: [InventoriesComponent, InventoriesListComponent, InventorySaveComponent, InventoryByIdComponent],
  imports: [
    CommonModule,
    MatNativeDateModule,
    InventoriesRoutingModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatSelectModule,
    MatButtonToggleModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatInputModule,
    MatTableModule,
    FlexModule,
    UtilsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatStepperModule,
    CdkTableModule,
    UtilsModule,
  ],
  exports: [InventoriesComponent],
})
export class InventoriesModule {}
