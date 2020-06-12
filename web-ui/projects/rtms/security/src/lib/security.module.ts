import { NgModule } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { SignupComponent } from './signup/signup.component';
import { MatIconModule } from '@angular/material/icon';
import { ExtendedModule, FlexModule } from '@angular/flex-layout';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { SecurityRoutingModule } from './security-routing.module';
import { LoginSocialComponent } from './login-social/login-social.component';
import { MatDividerModule } from '@angular/material/divider';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { AuthComponent } from './auth/auth.component';
import { MatTabsModule } from '@angular/material/tabs';
import { UtilsModule } from '../../../utils/src/lib/utils.module';

@NgModule({
  declarations: [LoginComponent, SignupComponent, ResetPasswordComponent, LoginSocialComponent, AuthComponent],
  imports: [
    MatCardModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    CommonModule,
    MatButtonModule,
    MatIconModule,
    FlexModule,
    ExtendedModule,
    SecurityRoutingModule,
    UtilsModule,
    MatDividerModule,
    MatCheckboxModule,
    MatTabsModule,
  ],
  exports: [],
})
export class SecurityModule {}
