import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {IRest} from "../models/restaurant";

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  public restId: number
  constructor(private http: HttpClient) { }

  getAll(): Observable<IRest[]> {
    return this.http.get<IRest[]>('http://localhost:8080/api/public/restaurants')
  }

  getSlice(limit: number, skip: number): Observable<IRest[]> {
    return this.http.get<IRest[]>('http://localhost:8080/api/public/restaurants', {
      params: new HttpParams({
        fromObject: {
          limit: limit,
          skip: skip
        }
      })
    })
  }
}
