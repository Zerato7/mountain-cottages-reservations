import { UserType } from "../userType";

export class UserRegistration {
  username: string = '';
  password: string = '';
  firstName: string = '';
  lastName: string = '';
  gender: 'M' | 'Ž' = 'M';
  address: string = '';
  phoneNumber: string = '';
  email: string = '';
  creditCardNumber: string = '';
  userType: UserType = UserType.TOURIST;
}
