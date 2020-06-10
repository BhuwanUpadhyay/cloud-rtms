import { Pipe, PipeTransform } from '@angular/core';
import { Optional } from '../optional';
import { ErrorResponse } from '../model';

@Pipe({
  name: 'resolve',
})
export class ResolvePipe implements PipeTransform {
  transform(value: ErrorResponse, ...args: unknown[]): unknown {
    return Optional.of(value)
      .map((v) => v.translations)
      .map((v) => v.find((msg) => msg.lang === 'en'))
      .map((v) => v.value)
      .orElseGet(() => `No translation given by server for errorId: ${value.errorId}`);
  }
}
