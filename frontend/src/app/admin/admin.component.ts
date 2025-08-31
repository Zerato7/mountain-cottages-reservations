import { Component } from '@angular/core';
import { NonadminService } from '../services/nonadmin.service';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { AdminService } from '../services/admin.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { ProfileUpdateComponent } from '../profile-update/profile-update.component';
import { CreditCardUtil } from '../utils/credit-card.util';
import { ImageUtil } from '../utils/images.util';
import { CottageService } from '../services/cottage.service';
import { CottageResponse } from '../models/responses/cottageResponse';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NgbModalModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent {

  constructor(
    private nonadminService: NonadminService,
    private adminService: AdminService,
    private modalService: NgbModal,
    private cottageService: CottageService
  ) {}

  ngOnInit() {
    this.loadNonadmins();
    this.loadCottages();
  }

  nonadmins: NonAdminResponse[] = [];
  editingId: number = 0;
  cottages: CottageResponse[] = [];

  private loadNonadmins(): void {
    this.nonadminService.getAll().subscribe({
      next: nonadmins => {
        this.nonadmins = nonadmins;
      }
    });
  }

  private loadCottages(): void {
    this.cottageService.getAll().subscribe({
      next: cottages => {
        this.cottages = cottages;
        this.cottages.forEach((cottage) => {
          cottage.dateTimeTilBlocked = cottage.dateTimeTilBlocked ? new Date(cottage.dateTimeTilBlocked) : null;
          cottage.reservations.forEach((reservation) => {
            if (reservation.feedback) reservation.feedback.dateTimeCreation = new Date(reservation.feedback.dateTimeCreation);
          });
        });
      }
    });
  }

  acceptNonadmin(id: number) {
    this.adminService.acceptNonadmin(id).subscribe({
      next: message => {
        this.loadNonadmins();
      },
      error: err => {

      }
    });
  }

  rejectNonadmin(id: number) {
    this.adminService.rejectNonadmin(id).subscribe({
      next: message => {
        this.loadNonadmins();
      },
      error: err => {

      }
    });
  }

  deactivateNonadmin(id: number) {
    this.adminService.deactivateNonadmin(id).subscribe({
      next: message => {
        this.loadNonadmins();
      },
      error: err => {

      }
    });
  }

  reactivateNonadmin(id: number) {
    this.adminService.reactivateNonadmin(id).subscribe({
      next: message => {
        this.loadNonadmins();
      },
      error: err => {

      }
    });
  }

  getNonadminsByStatus(status: string): NonAdminResponse[] {
    return this.nonadmins.filter(na => na.status === status);
  }

  openEditModal(nonadmin: NonAdminResponse): void {
    const buttonElement = document.activeElement as HTMLElement;
    buttonElement.blur();
    const modalRef = this.modalService.open(ProfileUpdateComponent, {
      centered: true
    });
    modalRef.componentInstance.nonadmin = nonadmin;
    modalRef.componentInstance.disabledFields = [];

    modalRef.result.then(
      (nonadmin: NonAdminResponse) => {
        this.loadNonadmins();
      },
      () => {

      }
    )
  }

  getImageUrl(profilePicturePath: string): string {
    return ImageUtil.getImageUrl(profilePicturePath);
  }

  getCreditCardNumberDisplay(last4Digits: string): string {
    return CreditCardUtil.getCreditCardNumberDisplay(last4Digits);
  }

  badRatings(cottage: CottageResponse): boolean {
    if (this.isBlocked(cottage)) return false;
    return true;
  }

  isBlocked(cottage: CottageResponse): boolean {
    return cottage.dateTimeTilBlocked !== null &&
      cottage.dateTimeTilBlocked.getTime() > new Date().getTime();
  }

  block(cottage: CottageResponse): void {
    this.adminService.blockCottage(cottage.id).subscribe({
      next: message => {
        this.loadCottages();
      },
      error: err => {

      }
    });
  }

}
