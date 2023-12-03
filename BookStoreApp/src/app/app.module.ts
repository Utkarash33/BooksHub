import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import {ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule}  from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { HomeComponent } from './components/home/home.component';
import { DetailsComponent } from './components/details/details.component';
import { DiscussionsComponent } from './components/discussions/discussions.component';
import { LoginComponent } from './components/login/login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ReadinglistComponent } from './components/readinglist/readinglist.component';
import { MatDialogModule } from '@angular/material/dialog';
import { AddBookFormComponent } from './components/add-book-form/add-book-form.component';
import { DatePipe } from '@angular/common';
@NgModule({


  declarations: [
    AppComponent,
    RegistrationComponent,
    HomeComponent,
    DetailsComponent,
    DiscussionsComponent,
    LoginComponent,
    NavbarComponent,
    ReadinglistComponent,
    AddBookFormComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatDialogModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
