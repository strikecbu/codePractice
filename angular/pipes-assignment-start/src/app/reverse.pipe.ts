import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'reverse'
})
export class ReversePipe implements PipeTransform {

  transform(value: string, ): string {
    const strArray = value.split('');
    return strArray.reverse().join('')
  }
}
