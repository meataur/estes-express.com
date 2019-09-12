import { AbstractControl, FormControl } from '@angular/forms';

export function startEndTimeValidator(startHour: AbstractControl, startMinutes: AbstractControl, startAmPm: AbstractControl,
  endHour: AbstractControl, endMinutes: AbstractControl, endAmPm: AbstractControl)
  : boolean
{
  const startDate = new Date(`1 Jan 1900 ${startHour.value}:${startMinutes.value}:00 ${startAmPm.value}`);
  const endDate = new Date(`1 Jan 1900 ${endHour.value}:${endMinutes.value}:00 ${endAmPm.value}`);
  endHour.markAsTouched();
  endMinutes.markAsTouched();
  endAmPm.markAsTouched();
  if (endDate <= startDate) {
    endHour.setErrors( {'startEndTime':'Invalid Time'}) ;
    endMinutes.setErrors( {'startEndTime':'Invalid Time'} );
    endAmPm.setErrors( {'startEndTime':'Invalid Time'} );
    return true;
  } else {
    endHour.updateValueAndValidity({emitEvent: false});
    endMinutes.updateValueAndValidity({emitEvent: false});
    endAmPm.updateValueAndValidity({emitEvent: false});
    return false;
  }
}
