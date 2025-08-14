import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export namespace ParseUtil {

  export namespace Validators {

    export function isInteger(): ValidatorFn {
      return (control: AbstractControl): ValidationErrors | null => {
        const value = control.value?.toString();
        if (!value) return null;
        const parsed = parseIntSafe(value);
        return !isNaN(parsed) && parsed.toString() === value.trim() ? null : { notInteger: true };
      };
    }

    export function isFloat(): ValidatorFn {
      return (control: AbstractControl): ValidationErrors | null => {
        const value = control.value?.toString();
        if (!value) return null;
        const parsed = parseFloatSafe(value);
        return !isNaN(parsed) && isFinite(parsed) ? null : { notFloat: true };
      };
    }

    export function minValue(min: number): ValidatorFn {
      return (control: AbstractControl): ValidationErrors | null => {
        const value = parseFloatSafe(control.value?.toString());
        return isNaN(value) ? null : (value >= min ? null : { minValue: { requiredMin: min, actual: value } });
      };
    }

    export function maxValue(max: number): ValidatorFn {
      return (control: AbstractControl): ValidationErrors | null => {
        const value = parseFloatSafe(control.value?.toString());
        return isNaN(value) ? null : (value <= max ? null : { maxValue: { requiredMax: max, actual: value } });
      };
    }
  }

  export function parseIntSafe(value: string): number {
    return parseInt(value.trim(), 10);
  }

  export function parseFloatSafe(value: string): number {
    return parseFloat(value.trim().replace(',', '.'));
  }
}
