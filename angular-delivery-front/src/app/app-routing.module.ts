import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//import { RegisterComponent } from './register/register.component';
//import { LoginComponent } from './login/login.component';
//import { HomeComponent } from './home/home.component';
//import { ProfileComponent } from './profile/profile.component';
//import { ShelfComponent } from './shelf/shelf.component';
//import { EditorComponent } from './editor/editor.component';
import {RestaurantsPageComponent} from "./pages/restaurants-page/restaurants-page.component";
import {DishesPageComponent} from "./pages/dishes-page/dishes-page.component";

const routes: Routes = [
  { path: '', component: RestaurantsPageComponent },
  { path: 'dishes', component: DishesPageComponent }
  //{ path: 'home', component: HomeComponent },
  //{ path: 'login', component: LoginComponent },
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
