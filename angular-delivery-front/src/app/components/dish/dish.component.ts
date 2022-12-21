import {Component, Input} from '@angular/core'
import {IDish} from "../../models/dish";

@Component({
  selector: 'app-dish',
  templateUrl: './dish.component.html'
})
export class ProductComponent {
  @Input() dish: IDish

  details = false
}
