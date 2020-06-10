import { Pipe, PipeTransform } from '@angular/core';
import { Optional } from '../optional';

@Pipe({
  name: 'hateoasTitlecase',
})
export class HateoasTitlecasePipe implements PipeTransform {
  transform(value: string, ...args: unknown[]): string {
    const parts: string[] = Optional.of(value)
      .map((v) => v.split('-'))
      .orElse([]);
    return parts.map((v) => v.charAt(0).toUpperCase() + v.slice(1).toLowerCase()).join(' ');
  }
}
