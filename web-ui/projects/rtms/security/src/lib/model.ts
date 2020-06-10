import { FormGroup } from '@angular/forms';
import { InjectionToken } from '@angular/core';
import { ErrorResponse } from '../../../utils/src/lib/model';

export abstract class BasicFormComponent {
  form: FormGroup;
  serverErrors: Array<ErrorResponse> = [];
  formInvalid: boolean;
  formSubmitAttempt: boolean;

  async formSubmit() {
    this.formInvalid = false;
    this.formSubmitAttempt = false;
    if (this.form.valid) {
      try {
        await this.doSubmit();
      } catch (err) {
        this.formInvalid = true;
      }
    } else {
      this.formSubmitAttempt = true;
    }
  }

  protected abstract async doSubmit();
}

export interface AlreadyExistsResponse {
  exists: boolean;
}

export interface LoginResponse {
  status: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface SignUpRequest {
  email: string;
  name: string;
  username: string;
  password: string;
}

export const authBasePath: InjectionToken<string> = new InjectionToken<string>('authBasePath');
