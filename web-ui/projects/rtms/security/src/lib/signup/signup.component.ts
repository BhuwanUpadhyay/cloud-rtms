import { Component, OnInit } from '@angular/core';
import { BasicFormComponent } from '../model';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from '../service/login.service';

@Component({
  selector: 'rtms-signup',
  template: `
    <mat-card>
      <mat-card-content>
        <form [formGroup]="form" (ngSubmit)="formSubmit()">
          <mat-error *ngIf="formInvalid">
            <ul>
              <li *ngFor="let err of serverErrors">
                <span [class]="'server-error-' + err.errorId">{{ err | resolve }}</span>
              </li>
            </ul>
          </mat-error>
          <mat-form-field class="full-width-input">
            <input matInput placeholder="Name" type="text" formControlName="name" required />
            <mat-error>
              Please provide a valid name
            </mat-error>
          </mat-form-field>
          <mat-form-field class="full-width-input">
            <input matInput placeholder="Email" autocomplete="email" type="email" formControlName="email" required />
            <mat-error>
              Please provide a valid email
            </mat-error>
          </mat-form-field>
          <mat-form-field class="full-width-input">
            <input matInput placeholder="Username" type="text" formControlName="username" required />
            <mat-error>
              Please provide a valid username
            </mat-error>
          </mat-form-field>
          <mat-form-field class="full-width-input">
            <input
              matInput
              placeholder="Password"
              type="password"
              autocomplete="off"
              formControlName="password"
              required
            />
            <mat-error>
              Please provide a valid password
            </mat-error>
          </mat-form-field>
          <mat-form-field class="full-width-input">
            <input
              matInput
              placeholder="Confirm Password"
              type="password"
              autocomplete="off"
              formControlName="confirmPassword"
              required
            />
            <mat-error>
              Password does not match
            </mat-error>
          </mat-form-field>
          <div fxLayout="row" fxLayoutAlign="end">
            <button type="submit" mat-raised-button color="primary">
              <mat-icon>power_settings_new</mat-icon>
              Submit
            </button>
          </div>
        </form>
        <div class="clear-fix"></div>
        <rtms-login-social></rtms-login-social>
      </mat-card-content>
    </mat-card>
  `,
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent extends BasicFormComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService
  ) {
    super();
  }

  async ngOnInit() {
    this.form = this.fb.group({
      name: ['', Validators.required],
      email: ['', Validators.email],
      username: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
    });
  }

  protected async doSubmit(): Promise<any> {
    const name = this.form.get('name').value;
    const email = this.form.get('email').value;
    const username = this.form.get('username').value;
    const password = this.form.get('password').value;
    await this.loginService.signUp(name, username, password, email);
  }
}
