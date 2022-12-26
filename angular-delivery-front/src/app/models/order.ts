export interface IOrder {
  restId: number,
  deliveryAddress: string,
  comment: string,
  dishes: IOrderDish[]
}

export interface IOrderDish {
  id: number,
  amount: number
}
