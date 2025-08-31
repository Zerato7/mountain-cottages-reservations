import { Component, Input } from '@angular/core';
import { CottageService } from '../services/cottage.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ParseUtil } from '../utils/parse.util';
import { ImageListUpload } from '../utils/images.util';
import { CommonModule } from '@angular/common';
import { CottageResponse } from '../models/responses/cottageResponse';
import { CottageEdit } from '../models/requests/cottageEdit';

@Component({
  selector: 'app-cottage-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './cottage-edit.component.html',
  styleUrl: './cottage-edit.component.css'
})
export class CottageEditComponent {

  constructor(
    private cottageService: CottageService,
    public activeModal: NgbActiveModal
  ) { }

  @Input() ownerId: number = 0;
  @Input() cottage: CottageResponse = new CottageResponse();

  cottageForm!: FormGroup;

  errorMessage: string = '';
  private imageListUpload: ImageListUpload = new ImageListUpload();

  ngOnInit(): void {
    this.cottageForm = new FormGroup({
      name: new FormControl<string>(this.cottage.name, {
        nonNullable: true,
        validators: Validators.required
      }),
      location: new FormControl<string>(this.cottage.location, {
        nonNullable: true,
        validators: Validators.required
      }),
      capacity: new FormControl<string>(this.cottage.capacity.toString(), {
        nonNullable: true,
        validators: [
          ParseUtil.Validators.isInteger(),
          Validators.required,
          ParseUtil.Validators.minValue(1)
        ]
      }),
      imageDelete: new FormControl<boolean>(false, {
        validators: Validators.required
      }),
      images: new FormControl<FileList|null>(null, {
        validators: [],
        asyncValidators: [ImageListUpload.imageAsyncValidator()],
        updateOn: 'change'
      }),
      services: new FormControl<string>(this.cottage.services?? '', {}),
      winterPriceAdult: new FormControl<string>(this.cottage.winterPriceAdult.toString(), {
        nonNullable: true,
        validators: [
          ParseUtil.Validators.isFloat(),
          Validators.required,
          ParseUtil.Validators.minValue(0)
        ]
      }),
      winterPriceChild: new FormControl<string>(this.cottage.winterPriceChild.toString(), {
        nonNullable: true,
        validators: [
          ParseUtil.Validators.isFloat(),
          Validators.required,
          ParseUtil.Validators.minValue(0)
        ]
      }),
      summerPriceAdult: new FormControl<string>(this.cottage.summerPriceAdult.toString(), {
        nonNullable: true,
        validators: [
          ParseUtil.Validators.isFloat(),
          Validators.required,
          ParseUtil.Validators.minValue(0)
        ]
      }),
      summerPriceChild: new FormControl<string>(this.cottage.summerPriceChild.toString(), {
        nonNullable: true,
        validators: [
          ParseUtil.Validators.isFloat(),
          Validators.required,
          ParseUtil.Validators.minValue(0)
        ]
      }),
      phoneNumber: new FormControl<string>(this.cottage.phoneNumber, {
        nonNullable: true,
        validators: [
          Validators.required,
          Validators.pattern('^\\+?[0-9]{10,15}$')
        ]
      }),
      latitude: new FormControl<string>(this.cottage.latitude.toString(), {
        nonNullable: true,
        validators: [
          ParseUtil.Validators.isFloat(),
          Validators.required
        ]
      }),
      longitude: new FormControl<string>(this.cottage.longitude.toString(), {
        nonNullable: true,
        validators: [
          ParseUtil.Validators.isFloat(),
          Validators.required
        ]
      })
    });
  }

  submitEdit(): void {
    if (this.cottageForm.valid) {
      const formValues = this.cottageForm.value;
      let cottageEdit = new CottageEdit();
      cottageEdit.id = this.cottage.id;
      cottageEdit.name = formValues.name ?? '';
      cottageEdit.location = formValues.location ?? '';
      cottageEdit.capacity = ParseUtil.parseIntSafe(formValues.capacity ?? '0');
      cottageEdit.services = formValues.services ?? null;
      cottageEdit.winterPriceAdult = ParseUtil.parseFloatSafe(formValues.winterPriceAdult ?? '0');
      cottageEdit.winterPriceChild = ParseUtil.parseFloatSafe(formValues.winterPriceChild ?? '0');
      cottageEdit.summerPriceAdult = ParseUtil.parseFloatSafe(formValues.summerPriceAdult ?? '0');
      cottageEdit.summerPriceChild = ParseUtil.parseFloatSafe(formValues.summerPriceChild ?? '0');
      cottageEdit.phoneNumber = formValues.phoneNumber ?? '';
      cottageEdit.latitude = ParseUtil.parseFloatSafe(formValues.latitude ?? '0');
      cottageEdit.longitude = ParseUtil.parseFloatSafe(formValues.longitude ?? '0');
      cottageEdit.imageDelete = formValues.imageDelete ?? false;

      const formData = new FormData();
      formData.append('cottage',
        new Blob([JSON.stringify(cottageEdit)], {type: 'application/json'})
      );

      const images = formValues.images;
      Array.from(images ?? []).forEach(image => {
        formData.append('images', image as File);
      });

      this.cottageService.editCottage(formData).subscribe({
        next: response => {
          this.activeModal.close(response);
        },
        error: err => {
          this.errorMessage = err.message;
        }
      })
    }
  }

  onImageFileSelected(event: Event): void {
    this.imageListUpload.onImagesSelected(event, this.cottageForm, 'images');
  }

  getImagePreviewUrlList(): string[] {
    return this.imageListUpload.getImagePreviewUrlList();
  }

}
