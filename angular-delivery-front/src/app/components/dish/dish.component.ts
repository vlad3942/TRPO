import {Component, Input} from '@angular/core'
import {IDish} from "../../models/dish";

@Component({
  selector: 'app-dish',
  templateUrl: './dish.component.html'
})
export class ProductComponent {
  @Input() dish: IDish
  count: number = 0
  details = false

  incCount() {
    if (this.dish.amount >= this.count + 1) {
      this.count++;
    }
  }
  decCount() {
    if (0 <= this.count - 1) {
      this.count--;
    }
  }
}
