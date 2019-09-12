import { Pipe, PipeTransform } from '@angular/core';
@Pipe({
  name: 'displayFilename'
})
export class DisplayFilenamePipe implements PipeTransform {
  transform(value: any) {
    
    return value ? value.split(/(fakepath)/).pop() : null;
  }
}