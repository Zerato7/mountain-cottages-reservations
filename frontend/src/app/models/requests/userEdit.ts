export class UserEdit {
  id: number = 0;
  username: string | null = null;
  firstName: string = '';
  lastName: string = '';
  gender: 'М' | 'Ж' | null = null;
  address: string = '';
  phoneNumber: string = '';
  email: string = '';
  editProfilePicture: boolean = true;
  creditCardNumber: string | null = null;
}
