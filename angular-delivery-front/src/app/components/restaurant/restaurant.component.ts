import {Component, Input, OnInit} from '@angular/core';
import {IRest} from "../../models/restaurant";
import {RestaurantService} from "../../services/restaurant.service";

@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.css']
})
export class RestaurantComponent implements OnInit{
  @Input() rest: IRest

  openDate: Date
  closeDate: Date

  constructor(public restService: RestaurantService) {
  }
  details = false

  ngOnInit(): void {
    console.log(this.rest);
    if (this.rest.rating === undefined || this.rest.rating === null) {
      this.rest.rating = 0
    }
    this.openDate = new Date(this.rest.openTime * 1000)
    this.closeDate = new Date(this.rest.closeTime * 1000)
  }
}
