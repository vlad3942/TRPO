import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {IDish} from "../models/dish";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DishService {
  constructor(private http: HttpClient) {
  }

  getAllFromRest(restaurantId: number): Observable<IDish[]> {
    return this.http.get<IDish[]>(`http://localhost:8080/api/user/restaurants/${restaurantId}/dish`)
  }
}
