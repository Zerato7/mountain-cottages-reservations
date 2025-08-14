import { Component } from '@angular/core';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { AuthService } from '../services/auth.service';
import { ImageService } from '../services/image.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ProfileUpdateComponent } from '../profile-update/profile-update.component';
import { CommonModule } from '@angular/common';
import { UserType } from '../models/userType';
import { BgColourUtil } from '../utils/bg-colour.util';
import { CreditCardUtil } from '../utils/credit-card.util';

const disabledFields: Record<UserType, string[]> = {
  ADMIN: [],
  TOURIST: ['username', 'gender'],
  HOST: ['username', 'gender', 'creditCardNumber']
}

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {

  constructor(
    private authService: AuthService,
    private imageService: ImageService,
    private modalService: NgbModal
  ) { }

  nonadmin!: NonAdminResponse;

  ngOnInit() {
    this.loadNonadmin();
  }

  private loadNonadmin(): void {
    this.nonadmin = this.authService.getNonadmin()!;
  }

  openEditModal() {
    const buttonElement = document.activeElement as HTMLElement;
    buttonElement.blur();
    const modalRef = this.modalService.open(ProfileUpdateComponent, {
      centered: true
    });
    modalRef.componentInstance.nonadmin = this.nonadmin;
    modalRef.componentInstance.disabledFields = disabledFields[this.nonadmin.userType];

    modalRef.result.then(
      (nonadmin: NonAdminResponse) => {
        this.authService.setNonadmin(nonadmin);
        this.loadNonadmin();
      },
      () => {

      }
    );
  }

  getImageUrl(profilePicturePath: string): string {
    return this.imageService.getImageUrl(profilePicturePath);
  }

  getCreditCardNumberDisplay(last4Digits: string): string {
    return CreditCardUtil.getCreditCardNumberDisplay(last4Digits);
  }

  enabled(fieldName: string): boolean {
    return !disabledFields[this.nonadmin.userType].includes(fieldName);
  }

  getBackgroundClass(): string {
    return BgColourUtil.getCardBodyClass(this.authService.getUserType());
  }

}
