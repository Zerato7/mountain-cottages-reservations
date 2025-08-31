import { Injectable } from "@angular/core";
import { AbstractControl, ValidationErrors } from "@angular/forms";
import { OwlDateTimeIntl } from "@danielmoncada/angular-datetime-picker";
import { CottageResponse } from "../models/responses/cottageResponse";

@Injectable()
export class SerbianCyrillicIntl extends OwlDateTimeIntl {
  override cancelBtnLabel = 'Откажи';
  override setBtnLabel = 'Постави';
}

export namespace DateTimeUtil {

  export const oneHourMS: number = 1000 * 60 * 60;
  export const oneDayMS: number = oneHourMS * 24;

  export function DateTimeDiffHour(dt1: Date, dt2: Date): number {
    return Math.ceil((dt1.getTime() - dt2.getTime()) / (oneHourMS));
  }

  export function DateTimeDiffDays(dt1: Date, dt2: Date): number {
    return Math.ceil((dt1.getTime() - dt2.getTime()) / oneDayMS);
  }

  export function minTodayValidator(control: AbstractControl): ValidationErrors | null {
    let today = new Date();
    if (!control.value) return null;

    const hours = DateTimeDiffHour(control.value, today);
    if (control.value.getHours() < 14) return {invalidDate: true};
    return hours < 1 ? {tooSmall: true} : null;
  }

  export function MinStartDateTimeValidator(control: AbstractControl): ValidationErrors | null {
    const startDateTime = control.get('startDateTime')?.value;
    const endDateTime = control.get('endDateTime')?.value;

    if (!startDateTime || !endDateTime) return null;

    const errors: ValidationErrors = {}

    if (endDateTime.getHours() >= 10) {
      return {invalidDate: true};
    }

    if (endDateTime.getTime() - startDateTime.getTime() < 0) {
      return {tooSmall: true};
    }

    return null;
  }

  export function toLocalDateTimeFormatString(date: Date): string {
    return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() +
      ' ' + date.getHours() + ':' + date.getMinutes();
  }

}
