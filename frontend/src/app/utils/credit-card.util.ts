import { AbstractControl, ValidationErrors } from "@angular/forms";

const dinersRegexp = /(^(300|301|302|303)\d{12}$)|(^(36|38)\d{13}$)/;
const masterRegexp = /^(51|52|53|54|55)\d{14}$/;
const visaRegexp = /^(4539|4556|4916|4532|4929|4485|4716)\d{12}$/;

const hiddenChar: string = '*';

export namespace CreditCardUtil {

  export function creditCardNumberValidator(control: AbstractControl): ValidationErrors | null {
    const value = control.value?.toString().replace(/\s+/g, '') || '';
    if (value === '') return null;
    if (dinersRegexp.test(value) ||
        masterRegexp.test(value) ||
        visaRegexp.test(value)) return null;
    else return {invalidNumber: true};
  }

  export function getCardType(value: string): string | null {
    value = trim(value);
    if (dinersRegexp.test(value)) return 'diners';
    if (masterRegexp.test(value)) return 'mastercard';
    if (visaRegexp.test(value)) return 'visa';
    return null;
  }

  export function getCreditCardNumberDisplay(last4Digits: string): string {
    return (hiddenChar.repeat(4) + ' ').repeat(3) + last4Digits;
  }

  export function trim(value: string): string {
    return value.replace(/\s+/g, '');
  }

}
