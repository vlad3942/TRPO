import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {IDish} from "../models/dish";
import {catchError, Observable, retry, throwError} from "rxjs";
import {ErrorService} from "./error.service";

@Injectable({
  providedIn: 'root'
})
export class DishService {
  constructor(
    private http: HttpClient,
    private errorService: ErrorService
  ) {
  }

  getAllFromRest(restaurantId: number): Observable<IDish[]> {
    return this.http.get<IDish[]>(`http://localhost:8080/api/user/restaurants/${restaurantId}/dish`)
      .pipe(
        retry(2),
        catchError(this.errorHandler.bind(this))
      )
  }

  getSliceFromRest(restaurantId: number, limit: number, skip: number): Observable<IDish[]> {
    return this.http.get<IDish[]>(`http://localhost:8080/api/user/restaurants/${restaurantId}/dish`, {
      params: new HttpParams({
        fromObject: {
          limit: limit,
          skip: skip
        }
      })
    })
  }

  private errorHandler(error: HttpErrorResponse) {
    this.errorService.handle(error.message)
    return throwError(() => error.message)
  }
}
