import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {RestaurantsPageComponent} from "./pages/restaurants-page/restaurants-page.component";
import {DishesPageComponent} from "./pages/dishes-page/dishes-page.component";
import {LoginPageComponent} from "./pages/login-page/login-page.component";
import {RegisterPageComponent} from "./pages/register-page/register-page.component";
import {CartPageComponent} from "./pages/cart-page/cart-page.component";
import {ProfilePageComponent} from "./pages/profile-page/profile-page.component";
import {OrdersPageComponent} from "./pages/orders-page/orders-page.component";
import {CheckoutPageComponent} from "./pages/checkout-page/checkout-page.component";
import {AuthGuard} from "./services/auth.guard";
import {OrderPageComponent} from "./pages/order-page/order-page.component";

const routes: Routes = [
  { path: '', redirectTo: 'restaurants', pathMatch: 'full' },
  { path: 'restaurants', component: RestaurantsPageComponent },
  { path: 'dishes/:id', component: DishesPageComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'register', component: RegisterPageComponent },
  { path: 'cart', component: CartPageComponent },
  { path: 'profile', component: ProfilePageComponent, canActivate: [AuthGuard] },
  { path: 'orders', component: OrdersPageComponent, canActivate: [AuthGuard] },
  { path: 'order/:id', component: OrderPageComponent, canActivate: [AuthGuard] },
  { path: 'make-order', component: CheckoutPageComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [AuthGuard]
})
export class AppRoutingModule { }
