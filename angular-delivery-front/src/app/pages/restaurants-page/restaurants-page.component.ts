import {Component} from '@angular/core';
import {Observable, tap} from "rxjs";
import {TokenStorageService} from "../../services/token-storage.service";
import {IRest} from "../../models/restaurant";
import {RestaurantService} from "../../services/restaurant.service";

@Component({
  selector: 'app-restaurants-page',
  templateUrl: './restaurants-page.component.html',
  styleUrls: ['./restaurants-page.component.css']
})
export class RestaurantsPageComponent {
  title = 'delivery service';
  rests$: Observable<IRest[]>
  loading = false
  term = ''
  constructor(
    private restService: RestaurantService,
    private tokenService: TokenStorageService
  ) {
  }

  ngOnInit(): void {
    this.loading = true
    let token: string = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjcxNzQxMDE1LCJleHAiOjE2NzE4Mjc0MTV9.PRc_HDpnJjSeP4x0fDCtkpgiqJiQWlo0yuDCFINFrEHCHNH8Up1wRL_8ABFf30u6qzHU4mul504LGuA5BGgN_g';
    this.tokenService.saveToken(token);
    this.rests$ = this.restService.getAll().pipe(tap(() => {this.loading = false}));
  }
}
