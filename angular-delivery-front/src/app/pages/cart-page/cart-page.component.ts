import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {OrderService} from "../../services/order.service";
import {IDish} from "../../models/dish";
import {Subject} from "rxjs";

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.css']
})
export class CartPageComponent implements OnInit {

  private destroy$ = new Subject<undefined>();
  restId: number = 0;
  dishes: IDish[] = [];
  constructor(
    private orderService: OrderService
  ) {
  }

  ngOnInit() {
    this.restId = this.orderService.restaurantId
    this.dishes = this.orderService.dishes
  }

  countPrice(): number {
    let price = 0;
    this.dishes.forEach(d => {
      price += d.price * d.amount
    })
    return price
  }

  skip(id: number) {
    this.orderService.skipDish(id)
    this.dishes = this.orderService.dishes
  }
}
