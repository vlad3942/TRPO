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
import {LoginPageComponent} from "./pages/login-page/login-page.component";
import { NavigationComponent } from './components/navigation/navigation.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { CartPageComponent } from './pages/cart-page/cart-page.component';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { OrdersPageComponent } from './pages/orders-page/orders-page.component';
import { CheckoutPageComponent } from './pages/checkout-page/checkout-page.component';
import { DishesTableComponent } from './components/dishes-table/dishes-table.component';
import { OrderPageComponent } from './pages/order-page/order-page.component';

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
    SearchRestaurantPipe,
    LoginPageComponent,
    NavigationComponent,
    RegisterPageComponent,
    CartPageComponent,
    ProfilePageComponent,
    OrdersPageComponent,
    CheckoutPageComponent,
    DishesTableComponent,
    OrderPageComponent
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
// @ts-ignore
export class AppModule { }
// @ts-ignore
