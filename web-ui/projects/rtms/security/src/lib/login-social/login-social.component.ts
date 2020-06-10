import { Component, Inject, OnInit } from '@angular/core';
import { LoginService } from '../service/login.service';
import { authBasePath } from '../model';

@Component({
  selector: 'rtms-login-social',
  template: `
    <div fxLayout="row" fxLayoutAlign="space-between">
      <div class="clear-line">----------------</div>
      <div><h3>or connect with</h3></div>
      <div class="clear-line">----------------</div>
    </div>
    <div fxLayout="row" fxLayoutAlign="space-evenly center">
      <a mat-stroked-button [href]="auth + '/oauth2/authorization/facebook'">
        Facebook
      </a>
      <a mat-stroked-button [href]="auth + '/oauth2/authorization/google'">
        Google
      </a>
      <a mat-stroked-button [href]="auth + '/oauth2/authorization/linkedin'">
        LinkedIn
      </a>
      <a mat-stroked-button [href]="auth + '/oauth2/authorization/github'">
        Github
      </a>
    </div>
  `,
  styleUrls: ['./login-social.component.scss'],
})
export class LoginSocialComponent implements OnInit {
  constructor(@Inject(authBasePath) public auth: string, public loginService: LoginService) {}

  ngOnInit(): void {}
}
