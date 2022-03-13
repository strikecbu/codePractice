import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "sort",
})
export class SortPipe implements PipeTransform {
  transform(value: any, propName: string, desc: boolean = false): any {
    value = [...value];
    console.log(value);
    value.sort((a, b) => {
      const itemA = a[propName].charAt(0).charCodeAt(0);
      const itemB = b[propName].charAt(0).charCodeAt(0);
      console.log(itemA, itemB);
      if (desc) {
        return itemB - itemA;
      }
      return itemA - itemB;
    });
    return value;
  }
}
