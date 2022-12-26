import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../services/order.service";
import {IDish} from "../../models/dish";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {TokenStorageService} from "../../services/token-storage.service";
import {IOrder, IOrderDish} from "../../models/order";
import {catchError, tap, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-checkout-page',
  templateUrl: './checkout-page.component.html',
  styleUrls: ['./checkout-page.component.css']
})
export class CheckoutPageComponent implements OnInit {
  dishes: IDish[] = []
  restId: number
  mess: string = ''
  errorMess: string = ''

  form: FormGroup = new FormGroup({
    deliveryAddress: new FormControl<string>('', [Validators.required]),
    comment: new FormControl<string>('')
  })
  constructor(
    private orderService: OrderService,
    private tokenService: TokenStorageService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.dishes = this.orderService.dishes
    this.restId = this.orderService.restaurantId
  }

  submit() {
    const dsh: IOrderDish[] = this.dishes.map(d => {
      return {
        id: d.id,
        amount: d.amount
      } as IOrderDish
    })
    const order: IOrder = {
      restId: this.restId,
      deliveryAddress: this.form.value.deliveryAddress as string,
      comment: this.form.value.comment as string,
      dishes: dsh
    }
    this.orderService.makeOrder(order)
      .subscribe(mess => {
        alert(mess.message)
        if (mess.message.toLowerCase().indexOf('successfully created') >= 0) {
          this.router.navigate(['/order', mess.id])
        }
      })
  }

  get deliveryAddress(): FormControl {
    return this.form.controls.deliveryAddress as FormControl
  }
}
