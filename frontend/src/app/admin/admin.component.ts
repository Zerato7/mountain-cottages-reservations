import { Component, NgZone, TemplateRef, ViewChild } from '@angular/core';
import { NonadminService } from '../services/nonadmin.service';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { AdminService } from '../services/admin.service';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbModal, NgbModalModule, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ImageService } from '../services/image.service';
import { CreditCardService } from '../services/credit-card.service';
import { UserEdit } from '../models/requests/userEdit';

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
    private imageService: ImageService,
    private creditCardService: CreditCardService,
    private modalService: NgbModal,
    private ngZone: NgZone
  ) {}

  ngOnInit() {
    this.loadNonadmins();
    this.editForm.get('creditCardNumber')?.valueChanges.subscribe(() => {
      this.cardType =this.creditCardService.getCardType(
        this.editForm.get('creditCardNumber')?.value || ''
      );
    });
  }

  nonadmins: NonAdminResponse[] = [];
  editingId: number = 0;
  cardType: string | null = null;
  errorMessage: string = '';

  private loadNonadmins(): void {
    this.nonadminService.getAll().subscribe({
      next: nonadmins => {
        this.nonadmins = nonadmins;
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

  editForm = new FormGroup({
    username: new FormControl<string>('', {nonNullable: true, validators: Validators.required}),
    firstName: new FormControl<string>('', {nonNullable: true, validators: Validators.required}),
    lastName: new FormControl<string>('', {nonNullable: true, validators: Validators.required}),
    address: new FormControl<string>('', {nonNullable: true, validators: Validators.required}),
    gender: new FormControl<string>('М', {nonNullable: true, validators: Validators.required}),
    phoneNumber: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.pattern('^\\+?[0-9]{10,15}$')
      ]
    }),
    email: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.email
      ]
    }),
    editProfilePicture: new FormControl<boolean>(true, {nonNullable: true, validators: Validators.required}),
    image: new FormControl<File|null>(null, {
      validators: [],
      asyncValidators: [ImageService.imageAsyncValidator()],
      updateOn: 'change'
    }),
    creditCardNumber: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        CreditCardService.creditCardNumberValidator
      ]
    })
  })

  @ViewChild('editModal') editModal!: TemplateRef<any>;

  openEditModal(nonadmin: NonAdminResponse): void {
    this.editingId = nonadmin.id;
    this.editForm.patchValue({
      username: nonadmin.username,
      firstName: nonadmin.firstName,
      lastName: nonadmin.lastName,
      address: nonadmin.address,
      gender: nonadmin.gender,
      phoneNumber: nonadmin.phoneNumber,
      email: nonadmin.email
    });

    const editButton: HTMLElement = document.activeElement as HTMLElement;
    editButton.blur();
    this.modalService.open(this.editModal, {centered: true});
  }

  submitEdit(modal: NgbModalRef):void {
    if (this.editForm.valid) {
      const { username, firstName, lastName, gender, address, phoneNumber, email, editProfilePicture, image, creditCardNumber } = this.editForm.value;
      let userEdit = new UserEdit();
      userEdit.id = this.editingId;
      userEdit.username = username ?? '';
      userEdit.firstName = firstName ?? '';
      userEdit.lastName = lastName ?? '';
      userEdit.gender = gender === 'М' || gender === 'Ж' ? gender : 'М';
      userEdit.address = address ?? '';
      userEdit.phoneNumber = phoneNumber ?? '';
      userEdit.email = email ?? '';
      userEdit.editProfilePicture = editProfilePicture ?? false;
      userEdit.creditCardNumber = creditCardNumber ?? '';

      const formData = new FormData();
      formData.append('user',
        new Blob([JSON.stringify(userEdit)], {type: 'application/json'})
      );

      if (image) formData.append('profilePicture', image);

      this.nonadminService.editNonadmin(formData).subscribe({
        next: message => {
          modal.close();
          this.errorMessage = '';
          this.loadNonadmins();
        },
        error: err => {
          this.errorMessage = err.message;
        }
      });
    }
  }

  onFileSelected(event: Event): void {
    this.imageService.onFileSelected(event, this.editForm, 'image');
  }

  getImageName(): string | null {
    return this.imageService.getImageName();
  }

  getImageSize(): number {
    return this.imageService.getImageSize() ?? 0;
  }

  getImagePreviewUrl(): string | null {
    return this.imageService.getImagePreviewUrl();
  }

  getImageUrl(profilePicturePath: string): string {
    return this.imageService.getImageUrl(profilePicturePath);
  }

  getCreditCardNumberDisplay(last4Digits: string): string {
    return this.creditCardService.getCreditCardNumberDisplay(last4Digits);
  }

}
