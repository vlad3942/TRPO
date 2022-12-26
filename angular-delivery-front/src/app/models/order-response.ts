import {IDish} from "./dish";

export interface IOrderResponse {

  id: number,

  userId: number,

  restaurant: {
    id: number,
    name: string,
    address: string
  },
  courier: {
    id: number,
    name: string
  },
  status: string,
  deliveryAddress: string,
  totalPrice: number,
  comment: string,
  created: number,
  dishes: IDish[]
}
