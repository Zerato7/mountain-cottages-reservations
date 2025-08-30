export enum UserStatus {
  PENDING = 'PENDING',
  ACCEPTED = 'ACCEPTED',
  REJECTED = 'REJECTED',
  DEACTIVATED = 'DEACTIVATED'
}

export enum UserType {
  ADMIN = 'ADMIN',
  HOST = 'HOST',
  TOURIST = 'TOURIST'
}

export class UserResponse {
  id: number = 0;
  username: string = '';
  userType: UserType = UserType.TOURIST;
  status: UserStatus = UserStatus.PENDING;
}
