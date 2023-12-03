import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DetailsComponent } from './components/details/details.component';
import { HomeComponent } from './components/home/home.component';
import { DiscussionsComponent } from './components/discussions/discussions.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { ReadinglistComponent } from './components/readinglist/readinglist.component';
import { AddBookFormComponent } from './components/add-book-form/add-book-form.component';

const routes: Routes = [
{path: 'book-details/:id',component:DetailsComponent},
{path: '',component:HomeComponent},
{ path: 'discussions', component: DiscussionsComponent },
{ path: 'login', component:LoginComponent},
{path: 'registration',component:RegistrationComponent},
{path: 'readinglist',component:ReadinglistComponent},
{path:'addbook',component:AddBookFormComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
