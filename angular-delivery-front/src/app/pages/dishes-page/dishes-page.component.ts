import { Component } from '@angular/core';
import {Observable, tap} from "rxjs";
import {IDish} from "../../models/dish";
import {DishService} from "../../services/dish.service";
import {TokenStorageService} from "../../services/token-storage.service";
import {ModalService} from "../../services/modal.service";

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
  constructor(
    private dishService: DishService,
    private tokenService: TokenStorageService,
    public modalService: ModalService
  ) {
  }

  ngOnInit(): void {
    this.loading = true
    let token: string = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjcxNzQxMDE1LCJleHAiOjE2NzE4Mjc0MTV9.PRc_HDpnJjSeP4x0fDCtkpgiqJiQWlo0yuDCFINFrEHCHNH8Up1wRL_8ABFf30u6qzHU4mul504LGuA5BGgN_g';
    this.tokenService.saveToken(token);
    this.dishes$ = this.dishService.getAllFromRest(1).pipe(tap(() => {this.loading = false}));
  }
}
