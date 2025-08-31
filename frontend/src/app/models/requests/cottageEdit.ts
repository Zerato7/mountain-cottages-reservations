export class CottageEdit {
  id: number = 0;
  name: string = '';
  location: string = '';
  capacity: number = 0;
  services: string | null = null;
  winterPriceAdult: number = 0.0;
  winterPriceChild: number = 0.0;
  summerPriceAdult: number = 0.0;
  summerPriceChild: number = 0.0;
  phoneNumber: string = '';
  latitude: number = 0.0;
  longitude: number = 0.0;

  imageDelete: boolean = false;
}
