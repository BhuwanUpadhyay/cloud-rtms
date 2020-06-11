import {LOCALE_ID, NgModule} from '@angular/core';
import {registerLocaleData} from '@angular/common';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {Title} from '@angular/platform-browser';
import {FaIconLibrary} from '@fortawesome/angular-fontawesome';
import {CookieService} from 'ngx-cookie-service';
import {NgxWebstorageModule} from 'ngx-webstorage';
import locale from '@angular/common/locales/en';

import {AuthExpiredInterceptor} from './interceptor/auth-expired.interceptor';

import {fontAwesomeIcons} from './icons/font-awesome-icons';

@NgModule({
  imports: [
    HttpClientModule,
    NgxWebstorageModule.forRoot({prefix: 'jhi', separator: '-'})
  ],
  providers: [
    Title,
    CookieService,
    {
      provide: LOCALE_ID,
      useValue: 'en',
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true,
    }
  ],
})
export class CoreModule {
  constructor(iconLibrary: FaIconLibrary) {
    registerLocaleData(locale);
    iconLibrary.addIcons(...fontAwesomeIcons);
  }
}
