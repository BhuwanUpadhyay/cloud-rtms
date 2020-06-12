import { NgModule } from '@angular/core';
import { ShippingsComponent } from './shippings.component';
import { CommonModule } from '@angular/common';
import { ShippingsRoutingModule } from './shippings-routing.module';

@NgModule({
  declarations: [ShippingsComponent],
  imports: [CommonModule, ShippingsRoutingModule],
  exports: [ShippingsComponent],
})
export class ShippingsModule {}
