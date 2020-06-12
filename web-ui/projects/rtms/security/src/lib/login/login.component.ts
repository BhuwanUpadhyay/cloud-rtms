import { Component, OnInit } from '@angular/core';
import { LoginService } from '../service/login.service';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BasicFormComponent } from '../model';
import { Optional } from '../../../../utils/src/lib/optional';

@Component({
  selector: 'rtms-login',
  template: `
    <mat-card>
      <mat-card-content>
        <form [formGroup]="form" (ngSubmit)="formSubmit()">
          <mat-error *ngIf="formInvalid">
            The username and password were not recognised
          </mat-error>
          <mat-form-field class="full-width-input">
            <input matInput placeholder="Username" autocomplete="username" formControlName="username" required />
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
          <div fxLayout="row" fxLayoutAlign="space-between">
            <mat-checkbox>Remember me</mat-checkbox>
            <a routerLink="/reset-password">
              Forgot your password?
            </a>
          </div>
          <div class="clear-fix"></div>
          <div fxLayout="row" fxLayoutAlign="end">
            <button type="submit" mat-raised-button color="primary">
              <mat-icon>power_settings_new</mat-icon>
              Login
            </button>
          </div>
        </form>
        <div class="clear-fix"></div>
        <rtms-login-social></rtms-login-social>
      </mat-card-content>
    </mat-card>
  `,
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent extends BasicFormComponent implements OnInit {
  private returnUrl: string;

  constructor(
    protected fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService
  ) {
    super();
  }

  async ngOnInit() {
    this.returnUrl = Optional.of(this.route.snapshot.queryParams.returnUrl).orElse('/features');

    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });

    if (await this.loginService.checkAuthenticated()) {
      await this.router.navigate([this.returnUrl]);
    }
  }

  protected async doSubmit(): Promise<any> {
    const username = this.form.get('username').value;
    const password = this.form.get('password').value;
    await this.loginService.login(username, password);
  }
}
