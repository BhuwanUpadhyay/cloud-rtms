import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FeaturesComponent } from './features/features.component';
import { DescriptionRoutingModule } from './description-routing.module';
import { AppMaterialModule } from '../app-material.module';
import { LayoutsModule } from '../../../projects/rtms/layouts/src/lib/layouts.module';

@NgModule({
  declarations: [FeaturesComponent],
  imports: [CommonModule, AppMaterialModule, DescriptionRoutingModule, LayoutsModule],
})
export class DescriptionModule {}
