import { NgModule } from '@angular/core';
import { ResolvePipe } from './pipes/resolve.pipe';
import { CommonModule } from '@angular/common';
import { HateoasActionComponent } from './hateos-action/hateoas-action.component';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { FlexModule } from '@angular/flex-layout';
import { HateoasTitlecasePipe } from './hateos-action/hateoas-titlecase.pipe';

@NgModule({
  declarations: [ResolvePipe, HateoasActionComponent, HateoasTitlecasePipe],
  imports: [CommonModule, MatButtonModule, MatDividerModule, MatIconModule, FlexModule],
  exports: [ResolvePipe, HateoasActionComponent, HateoasTitlecasePipe],
})
export class UtilsModule {}
