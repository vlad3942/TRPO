import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {ProductComponent} from "./components/dish/dish.component";
import {HttpClientModule} from "@angular/common/http";
import { authInterceptorProviders } from './services/auth.interceptor';
import { GlobalErrorComponent } from './components/global-error/global-error.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { SearchDishPipe } from './pipes/search-dish.pipe';
import { ModalComponent } from './components/modal/modal.component';
import { CreateDishComponent } from './components/create-dish/create-dish.component';
import { FocusDirective } from './directives/focus.directive';
import { DishesPageComponent } from './pages/dishes-page/dishes-page.component';
import { RestaurantsPageComponent } from './pages/restaurants-page/restaurants-page.component';
import {RouterOutlet} from "@angular/router";
import {AppRoutingModule} from "./app-routing.module";
import { RestaurantComponent } from './components/restaurant/restaurant.component';
import {SearchRestaurantPipe} from "./pipes/search-restaurant.pipe";

@NgModule({
  declarations: [
    AppComponent,
    ProductComponent,
    GlobalErrorComponent,
    SearchDishPipe,
    ModalComponent,
    CreateDishComponent,
    FocusDirective,
    DishesPageComponent,
    RestaurantsPageComponent,
    RestaurantComponent,
    SearchRestaurantPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterOutlet,
    AppRoutingModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
