import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { UserRegistrationPageComponent } from './user-registration-page/user-registration-page.component';
import {ReactiveFormsModule} from '@angular/forms';
import {KeycloakService} from './keycloak.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    UserRegistrationPageComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [KeycloakService],
  bootstrap: [AppComponent]
})
export class AppModule { }
