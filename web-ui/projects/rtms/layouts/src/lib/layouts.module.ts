import { NgModule } from '@angular/core';
import { LayoutsComponent } from './layouts.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [LayoutsComponent],
  imports: [MatToolbarModule, MatIconModule, MatSidenavModule, MatListModule, MatButtonModule, CommonModule],
  exports: [LayoutsComponent],
})
export class LayoutsModule {}
