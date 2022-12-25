import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//import { RegisterComponent } from './register/register.component';
//import { HomeComponent } from './home/home.component';
//import { ProfileComponent } from './profile/profile.component';
//import { ShelfComponent } from './shelf/shelf.component';
//import { EditorComponent } from './editor/editor.component';
import {RestaurantsPageComponent} from "./pages/restaurants-page/restaurants-page.component";
import {DishesPageComponent} from "./pages/dishes-page/dishes-page.component";
import {LoginPageComponent} from "./pages/login-page/login-page.component";
import {RegisterPageComponent} from "./pages/register-page/register-page.component";

const routes: Routes = [
  { path: '', component: RestaurantsPageComponent },
  { path: 'dishes/:id', component: DishesPageComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'register', component: RegisterPageComponent}
  //{ path: 'home', component: HomeComponent },
  //{ path: 'register', component: RegisterComponent },
  //{ path: 'profile', component: ProfileComponent },
  //{ path: 'shelf', component: ShelfComponent },
  //{ path: 'editor', component: EditorComponent },
  //{ path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
