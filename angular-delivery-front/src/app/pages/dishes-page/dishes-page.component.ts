import { Component } from '@angular/core';
import {Observable, tap} from "rxjs";
import {IDish} from "../../models/dish";
import {DishService} from "../../services/dish.service";
import {TokenStorageService} from "../../services/token-storage.service";
import {ModalService} from "../../services/modal.service";
import {RestaurantService} from "../../services/restaurant.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-dishes-page',
  templateUrl: './dishes-page.component.html',
  styleUrls: ['./dishes-page.component.css']
})
export class DishesPageComponent {
  title = 'delivery service';
  dishes$: Observable<IDish[]>
  loading = false
  term = ''
  restaurantId: number
  constructor(
    private dishService: DishService,
    private restaurantService: RestaurantService,
    public modalService: ModalService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.loading = true
    this.route.params.subscribe((params) => {
      this.restaurantId = params['id'] as number
    })
    //this.restaurantId = this.restaurantService.restId
    this.dishes$ = this.dishService.getAllFromRest(this.restaurantId).pipe(tap(() => {this.loading = false}));
  }
}
