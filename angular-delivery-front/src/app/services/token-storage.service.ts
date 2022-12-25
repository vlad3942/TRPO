import { Injectable } from '@angular/core';
import {AuthService} from "./auth.service";
import {Subject} from "rxjs";

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  public isLoggedIn = new Subject<boolean>()

  constructor() {
    this.isLoggedIn.next(!!window.sessionStorage.getItem(TOKEN_KEY))
  }

  signOut(): void {
    window.sessionStorage.clear();
    this.isLoggedIn.next(false);
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
    this.isLoggedIn.next(true);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    return user ? JSON.parse(user) : {};
  }
}
