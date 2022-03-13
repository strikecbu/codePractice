import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "shorten"
})
export class ShortenPipe implements PipeTransform {
  transform(
    value: string,
    limit: number = 10,
    toLower: boolean = false
  ): string {
    if (toLower) {
      value = value.toLowerCase();
    }
    if (value.length > limit) {
      return value.substr(0, limit) + " ...";
    }
    return value;
  }
}
