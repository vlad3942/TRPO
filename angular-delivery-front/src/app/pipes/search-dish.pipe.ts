import { Pipe, PipeTransform } from '@angular/core';
import {IDish} from "../models/dish";

@Pipe({
  name: 'searchDish'
})
export class SearchDishPipe implements PipeTransform {

  transform(dishes: IDish[], search: string): IDish[] {
    if (search.length === 0) return dishes;
    return dishes.filter(d => d.name.toLowerCase().includes(search.toLowerCase()));
  }

}
