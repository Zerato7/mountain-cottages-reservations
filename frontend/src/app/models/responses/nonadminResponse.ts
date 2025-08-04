import { UserResponse } from "./userResponse";

export class NonAdminResponse extends UserResponse {
  firstName: string = '';
  lastName: string = '';
  gender: 'M' | 'Ž' = 'M';
  address: string = '';
  phoneNumber: string = '';
  email: string = '';
  profilePicture: string | null = '';
  creditCardNumberLast4Digits: string = '';
}
