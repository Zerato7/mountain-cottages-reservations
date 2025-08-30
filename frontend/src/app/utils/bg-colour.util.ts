import { UserType } from "../models/responses/userResponse";

export namespace BgColourUtil {

  export function getFooterBgClass(userType: UserType | null): string {
    switch (userType) {
      case UserType.TOURIST: return 'bg-footer-tourist';
      case UserType.HOST: return 'bg-footer-host';
      case UserType.ADMIN: return 'bg-footer-admin';
      default: return 'bg-footer-unregistered';
    }
  }

  export function getHeaderBgClass(userType: UserType | null): string {
    switch (userType) {
      case UserType.TOURIST: return 'bg-header-tourist';
      case UserType.HOST: return 'bg-header-host';
      case UserType.ADMIN: return 'bg-header-admin';
      default: return 'bg-header-unregistered';
    }
  }

  export function getMainBgClass(userType: UserType | null): string {
    switch (userType) {
      case UserType.TOURIST: return 'bg-tourist';
      case UserType.HOST: return 'bg-host';
      case UserType.ADMIN: return 'bg-admin';
      default: return 'bg-unregistered';
    }
  }

  export function getCardBodyClass(userType: UserType | null): string {
    switch (userType) {
      case UserType.TOURIST: return 'bg-cardbody-tourist';
      case UserType.HOST: return 'bg-cardbody-host';
      default: return '';
    }
  }

}
