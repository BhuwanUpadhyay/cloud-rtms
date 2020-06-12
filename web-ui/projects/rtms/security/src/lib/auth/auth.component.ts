import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'rtms-auth',
  template: `
    <mat-tab-group>
      <mat-tab label="Log in">
        <rtms-login></rtms-login>
      </mat-tab>
      <mat-tab label="Sign up">
        <rtms-signup></rtms-signup>
      </mat-tab>
    </mat-tab-group>
  `,
  styleUrls: ['./auth.component.scss'],
})
export class AuthComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
