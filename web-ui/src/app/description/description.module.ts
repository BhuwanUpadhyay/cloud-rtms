import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FeaturesComponent } from './features/features.component';
import { DescriptionRoutingModule } from './description-routing.module';
import { LayoutsModule } from '../../../projects/rtms/layouts/src/lib/layouts.module';
import { MatDividerModule } from '@angular/material/divider';
import { MatGridListModule } from '@angular/material/grid-list';

@NgModule({
  declarations: [FeaturesComponent],
  imports: [CommonModule, DescriptionRoutingModule, LayoutsModule, MatDividerModule, MatGridListModule],
})
export class DescriptionModule {}
