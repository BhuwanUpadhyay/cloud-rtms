import { NgModule } from '@angular/core';
import { PaymentsComponent } from './payments.component';
import { CommonModule } from '@angular/common';
import { PaymentsRoutingModule } from './payments-routing.module';

@NgModule({
  declarations: [PaymentsComponent],
  imports: [CommonModule, PaymentsRoutingModule],
  exports: [PaymentsComponent],
})
export class PaymentsModule {}
