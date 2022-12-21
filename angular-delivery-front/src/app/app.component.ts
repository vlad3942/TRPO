import {Component, Inject, OnInit} from '@angular/core';
import {dishes, dishes as data} from './data/dishes';
import {IDish} from "./models/dish";
import {DishService} from "./services/dish.service";
import {TokenStorageService} from "./services/token-storage.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'delivery service';
  dishes: IDish[] = []
  constructor(
    private dishService: DishService,
    private tokenService: TokenStorageService
  ) {

  }

  ngOnInit(): void {
    let token: string = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjcxNTgyNDQ2LCJleHAiOjE2NzE2Njg4NDZ9.PV2nbqe5nZtu40Zdl6mbI4szq2g0ladKeNS4wKNWPpb7e7Ur28rxW3BtVH9mz_mJuGXf3GO50A96f3JUpry7AQ';
    this.tokenService.saveToken(token);
    this.dishService.getAllFromRest(1).subscribe(dishes => {
      this.dishes = dishes
    });
  }

}
