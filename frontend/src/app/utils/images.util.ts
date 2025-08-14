import { AbstractControl, AsyncValidatorFn, FormGroup, ValidationErrors } from "@angular/forms";
import { Observable, of } from "rxjs";

const validImageFormats = ['image/jpeg', 'image/png'];
const min_width = 100, max_width = 300;
const min_height = 100, max_height = 300;
const baseBackPath = 'http://localhost:8080';

export namespace ImageUtil {

  export function getImageUrl(profilePicturePath: string): string {
    return baseBackPath + '/' + profilePicturePath;
  }

}

export class ImageUpload {

  constructor() { }

  private imageName: string | null = null;
  private imagePreviewUrl: string | null = null;
  private imageSize: number | null = null;

  static imageAsyncValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      const file = control.value as File;
      if (!file) return of(null);

      // console.log(file.type);
      if (!validImageFormats.includes(file.type)) return of({invalidType: true});

      return new Observable<ValidationErrors | null>(observer => {
        const reader = new FileReader();
        reader.onload = (event: any) => {
          const image = new Image();
          image.src = reader.result as string;

          image.onload = () => {
            const {width, height} = image;
            if (width < min_width || height < min_height || width > max_width || height > max_height) {
              observer.next({invalidDimensions: true});
            } else {
              observer.next(null);
            }
            observer.complete();
          };

          image.onerror = () => {
            observer.next({invalidType: true});
            observer.complete();
          }
        };

        reader.readAsDataURL(file);
      });
    };
  }

  onImageSelected(event: Event, formGroup: FormGroup, fieldName: string): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0] ?? null;

    formGroup.get(fieldName)?.setValue(file);
    formGroup.get(fieldName)?.markAsTouched();
    formGroup.get(fieldName)?.updateValueAndValidity();

    this.imageName = file?.name ?? null;
    this.imageSize = file?.size ?? null;
    if (file) {
      const reader = new FileReader();
      reader.onload = e => this.imagePreviewUrl = reader.result as string;
      reader.readAsDataURL(file);
    } else {
      this.imagePreviewUrl = null;
    }
  }

  getImageName(): string | null {
    return this.imageName;
  }

  getImageSize(): number | null {
    return this.imageSize;
  }

  getImagePreviewUrl(): string | null {
    return this.imagePreviewUrl;
  }

  clearImageName(): void {
    this.imageName = null;
  }

  clearImageSize(): void {
    this.imageSize = null;
  }

  clearImagePreviewUrl(): void {
    this.imagePreviewUrl = null;
  }

}

export class ImageListUpload {

  private imageNameList: string[] = [];
  private imagePreviewUrlList: string[] = [];
  private imageSizeList: number[] = [];

  static imageAsyncValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      const files = control.value as FileList;
      if (!files) return of(null);

      const invalidFileExists = Array.from(files).some(file => !validImageFormats.includes(file.type));
      if (invalidFileExists) {
        return of({invalidType: true});
      } else {
        return of(null);
      }
    };
  }

  onImagesSelected(event: Event, formGroup: FormGroup, fieldName: string):void {
    const input = event.target as HTMLInputElement;
    const files = input.files;

    formGroup.get(fieldName)?.setValue(files);
    formGroup.get(fieldName)?.markAsTouched();
    formGroup.get(fieldName)?.updateValueAndValidity();

    Array.from(files ?? []).forEach(file => {
      this.imageNameList.push(file.name);
      this.imageSizeList.push(file.size);

      const reader = new FileReader();
      reader.onload = e => this.imagePreviewUrlList.push(reader.result as string);
      reader.readAsDataURL(file);
    })
  }

  getImageName(position: number): string | null {
    return this.imageNameList.at(position) ?? null;
  }

  getImagePreviewUrl(position: number): string | null {
    return this.imagePreviewUrlList.at(position) ?? null;
  }

  getImagePreviewUrlList(): string[] {
    return this.imagePreviewUrlList;
  }

}
