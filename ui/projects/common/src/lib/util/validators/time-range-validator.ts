import { FormGroup } from '@angular/forms';
import { convertTime12to24 } from '../date-helpers';

/**
 * @description This validator operates under the assumption that the formGroup contains
 * the following formControls: availableByHour, availableByMinutes, availableByAmPm,
 * closesByHour, closesByMinutes, closesByAmPm
 */
export function timeRangeValidator(group: FormGroup) {
  const availableByHour = +group.controls.availableByHour.value;
  const availableByMinutes = +group.controls.availableByMinutes.value;
  const availableByAmPm = group.controls.availableByAmPm.value as 'AM' | 'PM';
  const closesByHour = +group.controls.closesByHour.value;
  const closesByMinutes = +group.controls.closesByMinutes.value;
  const closesByAmPm = group.controls.closesByAmPm.value as 'AM' | 'PM';

  let availableBy = new Date();
  let closesBy = new Date();
  closesBy.setTime(availableBy.getTime());

  availableBy = convertTime12to24(
    availableByHour,
    availableByMinutes,
    availableByAmPm,
    availableBy
  );

  closesBy = convertTime12to24(closesByHour, closesByMinutes, closesByAmPm, closesBy);

  let validRange = true;

  if (closesBy < availableBy) {
    validRange = false;
  }

  return validRange ? null : { invalidPickupRange: true };
}
