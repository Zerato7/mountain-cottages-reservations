import { Component, Input } from '@angular/core';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { UserEdit } from '../models/requests/userEdit';
import { NonadminService } from '../services/nonadmin.service';
import { CommonModule } from '@angular/common';
import { CreditCardUtil } from '../utils/credit-card.util';
import { ImageUpload } from '../utils/images.util';

@Component({
  selector: 'app-profile-update',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './profile-update.component.html',
  styleUrl: './profile-update.component.css'
})
export class ProfileUpdateComponent {

  constructor(
    private nonadminService: NonadminService,
    public activeModal: NgbActiveModal
  ) {}

  @Input() nonadmin: NonAdminResponse = new NonAdminResponse();
  @Input() disabledFields: string[] = [];

  editForm!: FormGroup;
  errorMessage: string = '';
  cardType: string | null = null;
  private imageUpload: ImageUpload = new ImageUpload();

  ngOnInit() {
    this.editForm = new FormGroup({
      username: new FormControl<string|null>({
        value: this.nonadmin.username, disabled: this.disabledFields.includes('username')
      }, {
        nonNullable: true, validators: Validators.required
      }),
      firstName: new FormControl<string>({
        value: this.nonadmin.firstName, disabled: this.disabledFields.includes('firstName')
      }, {
        nonNullable: true, validators: Validators.required
      }),
      lastName: new FormControl<string>({
        value: this.nonadmin.lastName, disabled: this.disabledFields.includes('lastName')
      }, {
        nonNullable: true, validators: Validators.required
      }),
      address: new FormControl<string>({
        value: this.nonadmin.address, disabled: this.disabledFields.includes('address')
      }, {
        nonNullable: true, validators: Validators.required
      }),
      gender: new FormControl<string|null>({
        value: this.nonadmin.gender, disabled: this.disabledFields.includes('gender')
      }, {
        nonNullable: true, validators: Validators.required
      }),
      phoneNumber: new FormControl<string>({
        value: this.nonadmin.phoneNumber, disabled: this.disabledFields.includes('phoneNumber')
      }, {
        nonNullable: true,
        validators: [
          Validators.required,
          Validators.pattern('^\\+?[0-9]{10,15}$')
        ]
      }),
      email: new FormControl<string>({
        value: this.nonadmin.email, disabled: this.disabledFields.includes('email')
      }, {
        nonNullable: true,
        validators: [
          Validators.required,
          Validators.email
        ]
      }),
      editProfilePicture: new FormControl<boolean>({
        value: true, disabled: this.disabledFields.includes('editProfilePicture')
      }, {
        nonNullable: true, validators: Validators.required
      }),
      image: new FormControl<File|null>({
        value: null, disabled: this.disabledFields.includes('image')
      }, {
        validators: [],
        asyncValidators: [ImageUpload.imageAsyncValidator()],
        updateOn: 'change'
      }),
      creditCardNumber: new FormControl<string>({
        value: '', disabled: this.disabledFields.includes('creditCardNumber')
      }, {
        nonNullable: true,
        validators: [
          CreditCardUtil.creditCardNumberValidator
        ]
      })
    });

    this.editForm.get('creditCardNumber')?.valueChanges.subscribe(() => {
      this.cardType = CreditCardUtil.getCardType(
        this.editForm.get('creditCardNumber')?.value || ''
      );
    });
  }

  submitEdit():void {
    if (this.editForm.valid) {
      const formValues = this.editForm.getRawValue();
      let userEdit = new UserEdit();
      userEdit.id = this.nonadmin.id;
      userEdit.username = formValues.username ?? null;
      userEdit.firstName = formValues.firstName ?? '';
      userEdit.lastName = formValues.lastName ?? '';
      userEdit.gender = formValues.gender ?? null;
      userEdit.address = formValues.address ?? '';
      userEdit.phoneNumber = formValues.phoneNumber ?? '';
      userEdit.email = formValues.email ?? '';
      userEdit.editProfilePicture = formValues.editProfilePicture ?? false;
      userEdit.creditCardNumber = formValues.creditCardNumber ?? '';

      const formData = new FormData();
      formData.append('user',
        new Blob([JSON.stringify(userEdit)], {type: 'application/json'})
      );

      if (formValues.image) formData.append('profilePicture', formValues.image);

      this.nonadminService.editNonadmin(formData).subscribe({
        next: response => {
          this.activeModal.close(response);
        },
        error: err => {
          this.errorMessage = err.message;
        }
      })
    }
  }

  onFileSelected(event: Event): void {
    this.imageUpload.onImageSelected(event, this.editForm, 'image');
  }

  getImageName(): string | null {
    return this.imageUpload.getImageName();
  }

  getImageSize(): number {
    return this.imageUpload.getImageSize() ?? 0;
  }

  getImagePreviewUrl(): string | null {
    return this.imageUpload.getImagePreviewUrl();
  }

}
