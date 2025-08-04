import { UserType } from "../userType";

export class UserResponse {
  id: number = 0;
  username: string = '';
  userType: UserType = UserType.TOURIST;
}
