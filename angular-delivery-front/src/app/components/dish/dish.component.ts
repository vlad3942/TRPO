import {Component, Input, OnInit} from '@angular/core'
import {IDish} from "../../models/dish";
import {OrderService} from "../../services/order.service";

@Component({
  selector: 'app-dish',
  templateUrl: './dish.component.html'
})
export class ProductComponent implements OnInit {
  @Input() dish: IDish
  @Input() restId: number
  count: number = 0
  details = false

  constructor(private orderService: OrderService) {
  }

  ngOnInit() {
    if (this.orderService.restaurantId === this.restId) {
      let foundDish = this.orderService.dishes.filter(d => d.id === this.dish.id)[0]
      if (!!foundDish) {
        this.count = foundDish.amount
      }
    }
  }

  incCount() {
    if (this.checkRest()) {
      return;
    }
    if (this.dish.amount >= this.count + 1) {
      this.count++;
    } else {
      return;
    }
    let foundDish = this.orderService.dishes.filter(d => d.id === this.dish.id)[0]
    if (!!foundDish) {
      foundDish.amount++;
    } else {
      const newDish: IDish = {
        ...this.dish,
        amount: this.count
      }
      this.orderService.dishes.push(newDish)
    }
  }
  decCount() {
    if (this.checkRest()) {
      return;
    }
    if (0 <= this.count - 1) {
      this.count--;
    } else {
      return
    }
    let foundDish = this.orderService.dishes.filter(d => d.id === this.dish.id)[0];
    if (!!foundDish) {
      foundDish.amount--
    } else {
      const newDish: IDish = {
        ...this.dish,
        amount: this.count
      }
      this.orderService.dishes.push(newDish)
    }
  }

  private checkRest(): boolean {
    if (
      this.orderService.restaurantId === null
      || this.orderService.restaurantId === undefined
      || this.orderService.restaurantId === 0
    ) {
      this.orderService.restaurantId = this.restId
      this.orderService.dishes = []
      return false;
    }
    if (this.orderService.restaurantId != this.restId) {
      let skipCart = confirm(
        "В карзине уже содержатся блюда из другого рестарана.\nЖелаете заказать в этом рестаране?\n(Текущая карзина будет сброшена)"
      );
      if (skipCart) {
        this.orderService.restaurantId = this.restId
        this.orderService.dishes = []
        return false
      } else {
        return true
      }
    }
    return false;
  }
}
