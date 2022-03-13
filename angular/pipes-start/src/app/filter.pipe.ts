import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter',
  pure: false
})
export class FilterPipe implements PipeTransform {

  transform(value: any, filterStatus: string, propName: string): any {
    if (value.length > 0 && filterStatus && propName) {
      const resultArray = [];
      for(let item of value) {
        if (item[propName] === filterStatus) {
          resultArray.push(item);
        }
      }
      return resultArray;
    }
    return value;
  }
}
