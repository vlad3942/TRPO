import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {IRegister} from "../models/register";
import {Observable, Subject} from "rxjs";
import {ILogin} from "../models/login";

const AUTH_API = '/api/auth/signup'
const LOGIN_API = '/api/auth/signin'

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {}

  register(data: IRegister): Observable<any> {
    // @ts-ignore
    return this.http.post(window['apiUrl'] + AUTH_API, data, httpOptions)
  }

  login(data: ILogin): Observable<any> {
    // @ts-ignore
    return this.http.post(window['apiUrl'] + LOGIN_API, data, httpOptions)
  }


}
