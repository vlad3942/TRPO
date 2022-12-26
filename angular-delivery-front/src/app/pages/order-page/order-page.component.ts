import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../services/order.service";
import {ActivatedRoute} from "@angular/router";
import {IOrderResponse} from "../../models/order-response";

@Component({
  selector: 'app-order-page',
  templateUrl: './order-page.component.html',
  styleUrls: ['./order-page.component.css']
})
export class OrderPageComponent implements OnInit {

  orderId: number = -1

  order: IOrderResponse

  cancelDisable = false
  warnDisable = false

  error = false

  constructor(
    public orderService: OrderService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.orderId = params['id'] as number
    })
    this.orderService.getOrder(this.orderId).subscribe(
      order => this.order = order,
      () => this.error = true
    )
  }

  getDate(date: number): Date {
    return new Date(date)
  }

  cancelOrder() {
    this.orderService.cancelOrder(this.orderId).subscribe(
      message => {
        if (message.message.indexOf("successfully canceled.") < 0) {
          this.cancelDisable = true
        }
        alert(message.message)
      }
    )
  }

  warnOrder() {
    this.orderService.warnOrder(this.orderId).subscribe(
      message => {
        this.warnDisable = true
        alert(message.message)
      }
    )
  }

}
