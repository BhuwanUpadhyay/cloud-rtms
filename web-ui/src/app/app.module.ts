import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { authBasePath } from '../../projects/rtms/security/src/lib/model';
import { gatewayBasePath } from '../../projects/rtms/utils/src/lib/gateway';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production }),
    ReactiveFormsModule,
  ],
  providers: [
    {
      provide: authBasePath,
      useValue: environment.authBasePath,
    },
    {
      provide: gatewayBasePath,
      useValue: environment.gateway,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
