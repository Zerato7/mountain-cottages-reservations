import { Component, Input } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ParseUtil } from '../utils/parse.util';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { CottageInsert } from '../models/requests/cottageInsert';
import { CottageService } from '../services/cottage.service';
import { ImageListUpload } from '../utils/images.util';

const allowedKeys = new Set([
  'name', 'location', 'capacity', 'services',
  'winterPriceAdult', 'winterPriceChild',
  'summerPriceAdult', 'summerPriceChild',
  'phoneNumber',
  'latitude', 'longitude'
]);

const numericFields = [
  'capacity', 'winterPriceAdult', 'winterPriceChild',
  'summerPriceAdult', 'summerPriceChild',
  'latitude', 'longitude'
];

@Component({
  selector: 'app-cottage-insert',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './cottage-insert.component.html',
  styleUrl: './cottage-insert.component.css'
})
export class CottageInsertComponent {

  constructor(
    private cottageService: CottageService,
    public activeModal: NgbActiveModal
  ) { }

  @Input() ownerId: number = 0;

  cottageForm = new FormGroup({
    name: new FormControl<string>('', {nonNullable: true, validators: Validators.required}),
    location: new FormControl<string>('', {nonNullable: true, validators: Validators.required}),
    capacity: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        ParseUtil.Validators.isInteger(),
        Validators.required,
        ParseUtil.Validators.minValue(1)
      ]
    }),
    images: new FormControl<FileList|null>(null, {
      validators: [],
      asyncValidators: [ImageListUpload.imageAsyncValidator()],
      updateOn: 'change'
    }),
    services: new FormControl<string>('', {}),
    winterPriceAdult: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        ParseUtil.Validators.isFloat(),
        Validators.required,
        ParseUtil.Validators.minValue(0)
      ]
    }),
    winterPriceChild: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        ParseUtil.Validators.isFloat(),
        Validators.required,
        ParseUtil.Validators.minValue(0)
      ]
    }),
    summerPriceAdult: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        ParseUtil.Validators.isFloat(),
        Validators.required,
        ParseUtil.Validators.minValue(0)
      ]
    }),
    summerPriceChild: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        ParseUtil.Validators.isFloat(),
        Validators.required,
        ParseUtil.Validators.minValue(0)
      ]
    }),
    phoneNumber: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.pattern('^\\+?[0-9]{10,15}$')
      ]
    }),
    latitude: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        ParseUtil.Validators.isFloat(),
        Validators.required
      ]
    }),
    longitude: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        ParseUtil.Validators.isFloat(),
        Validators.required
      ]
    })
  });

  errorMessage: string = '';
  jsonError: string = '';
  private imageListUpload: ImageListUpload = new ImageListUpload();

  submitInsert(): void {
    if (this.cottageForm.valid) {
      const formValues = this.cottageForm.value;
      let cottageInsert = new CottageInsert();
      cottageInsert.name = formValues.name ?? '';
      cottageInsert.location = formValues.location ?? '';
      cottageInsert.capacity = ParseUtil.parseIntSafe(formValues.capacity ?? '0');
      cottageInsert.services = formValues.services ?? null;
      cottageInsert.winterPriceAdult = ParseUtil.parseFloatSafe(formValues.winterPriceAdult ?? '0');
      cottageInsert.winterPriceChild = ParseUtil.parseFloatSafe(formValues.winterPriceChild ?? '0');
      cottageInsert.summerPriceAdult = ParseUtil.parseFloatSafe(formValues.summerPriceAdult ?? '0');
      cottageInsert.summerPriceChild = ParseUtil.parseFloatSafe(formValues.summerPriceChild ?? '0');
      cottageInsert.phoneNumber = formValues.phoneNumber ?? '';
      cottageInsert.latitude = ParseUtil.parseFloatSafe(formValues.latitude ?? '0');
      cottageInsert.longitude = ParseUtil.parseFloatSafe(formValues.longitude ?? '0');
      cottageInsert.ownerId = this.ownerId;

      const formData = new FormData();
      formData.append('cottage',
        new Blob([JSON.stringify(cottageInsert)], {type: 'application/json'})
      );

      const images = formValues.images;
      Array.from(images ?? []).forEach(image => {
        formData.append('images', image);
      })

      this.cottageService.createCottage(formData).subscribe({
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

  onJsonFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) {
      this.jsonError = 'Nijedan fajl nije selektovan';
      return;
    }

    const file = input.files[0];

    if (file.type != 'application/json') {
      this.jsonError = 'Изабрани фајл није .json формата.';
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      try {
        const json = JSON.parse(reader.result as string);
        this.fillFormFromJson(json);
      } catch (e) {
        this.jsonError = 'Фајл није исправан JSON.';
      }
    };

    reader.readAsText(file);
  }

  fillFormFromJson(data: any): void {
    this.jsonError = '';

    const dataKeys = Object.keys(data);

    const unexpectedKeys = dataKeys.filter(key => !allowedKeys.has(key));
    if (unexpectedKeys.length > 0) {
      this.jsonError = `Неочекивана поља у JSON-у: ${unexpectedKeys.join(', ')}`;
      return;
    }

    let missingKeys: string[] = [];
    allowedKeys.forEach((key) => {
      if (!dataKeys.includes(key)) missingKeys.push(key)
    });
    if (missingKeys.length > 0) {
      this.jsonError = `Недостају обавезна поља: ${missingKeys.join(', ')}`;
      return;
    }

    for (const key of numericFields) {
      if (data[key] !== undefined && isNaN(Number(data[key]))) {
        this.jsonError = `Поље "${key}" мора бити број.`;
        return;
      }
    }

    this.cottageForm.patchValue({
      name: data.name,
      location: data.location,
      capacity: data.capacity.toString(),
      services: data.services,
      winterPriceAdult: data.winterPriceAdult.toString(),
      winterPriceChild: data.winterPriceChild.toString(),
      summerPriceAdult: data.summerPriceAdult.toString(),
      summerPriceChild: data.summerPriceChild.toString(),
      phoneNumber: data.phoneNumber,
      latitude: data.latitude.toString(),
      longitude: data.longitude.toString()
    });
    this.cottageForm.markAllAsTouched();
  }

  getImagePreviewUrlList(): string[] {
    return this.imageListUpload.getImagePreviewUrlList();
  }

}
