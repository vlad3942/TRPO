import {Injectable} from "@angular/core";
import {IDish} from "../models/dish";
import {IOrder} from "../models/order";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {IMessage} from "../models/message";
import {IOrderResponse} from "../models/order-response";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  restaurantId: number = 0
  dishes: IDish[] = []

  constructor(private http: HttpClient) {
  }

  cancelOrder(orderId: number): Observable<IMessage> {
    // @ts-ignore
    return this.http.get<IMessage>(`${window['apiUrl']}/api/user/orders/${orderId}/cancel`)
  }

  warnOrder(orderId: number): Observable<IMessage> {
    // @ts-ignore
    return this.http.get<IMessage>(`${window['apiUrl']}/api/user/orders/${orderId}/warning`)
  }

  makeOrder(order: IOrder): Observable<IMessage> {
    // @ts-ignore
    return this.http.post<IMessage>(window['apiUrl'] + '/api/user/make-order', order)
  }

  getOrders(): Observable<IOrderResponse[]> {
    // @ts-ignore
    return this.http.get<IOrderResponse[]>(`${window['apiUrl']}/api/user/orders`)
  }

  getOrder(id: number): Observable<IOrderResponse> {
    // @ts-ignore
    return this.http.get<IOrderResponse>(`${window['apiUrl']}/api/user/orders/${id}`)
  }

  skipCart() {
    this.restaurantId = 0;
    this.dishes = [];
  }

  skipDish(id: number) {
    this.dishes = this.dishes.filter(d => d.id != id)
    if (this.dishes.length <= 0) {
      this.restaurantId = 0
    }
  }

  getStatusMessage(status: string): string {
    switch (status) {
      case 'WAITING_CONFIRMATION':
        return 'Ожидает подтверждения со стороны ресторана'
      case 'WAITING_COURIER':
        return 'Ожидает предачу курьеру'
      case 'IN_PROGRESS':
        return 'Передан курьеру'
      case 'DONE':
        return 'Завершён'
      case 'CANCELED':
        return 'Отменён'
      case 'WARNING':
        return 'Открыт спор'
    }
    return 'Неизвестный статус'
  }
}
