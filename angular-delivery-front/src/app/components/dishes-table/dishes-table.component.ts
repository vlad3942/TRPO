import {Component, Input} from '@angular/core';
import {IDish} from "../../models/dish";

@Component({
  selector: 'app-dishes-table',
  templateUrl: './dishes-table.component.html',
  styleUrls: ['./dishes-table.component.css']
})
export class DishesTableComponent {
  @Input() dishes: IDish[]

  countPrice(): number {
    let price = 0;
    this.dishes.forEach(d => {
      price += d.price * d.amount
    })
    return price
  }
}
