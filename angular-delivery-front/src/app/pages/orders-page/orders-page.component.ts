import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../services/order.service";
import {tap} from "rxjs";
import {IOrderResponse} from "../../models/order-response";

@Component({
  selector: 'app-orders-page',
  templateUrl: './orders-page.component.html',
  styleUrls: ['./orders-page.component.css']
})
export class OrdersPageComponent implements OnInit {

  orders: IOrderResponse[] = []
  constructor(public orderService: OrderService) {
  }

  ngOnInit() {
    this.orderService.getOrders().subscribe(orders => {
      this.orders = orders;
    })
  }

  getDate(date: number): Date {
    return new Date(date)
  }
}
