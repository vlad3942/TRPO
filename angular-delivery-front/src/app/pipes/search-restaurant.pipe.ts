import { Pipe, PipeTransform } from '@angular/core';
import {IRest} from "../models/restaurant";

@Pipe({
  name: 'searchRest'
})
export class SearchRestaurantPipe implements PipeTransform {

  transform(rests: IRest[], search: string): IRest[] {
    if (search.length === 0) return rests;
    return rests.filter(r => r.name.toLowerCase().includes(search.toLowerCase()));
  }

}
