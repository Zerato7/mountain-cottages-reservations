import { UserType } from "../responses/userResponse";

export class UserRegistration {
  username: string = '';
  password: string = '';
  firstName: string = '';
  lastName: string = '';
  gender: 'М' | 'Ж' = 'М';
  address: string = '';
  phoneNumber: string = '';
  email: string = '';
  creditCardNumber: string = '';
  userType: UserType = UserType.TOURIST;
}
