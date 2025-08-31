import { Component } from '@angular/core';
import { CottageService } from '../services/cottage.service';
import { ActivatedRoute } from '@angular/router';
import { CottageResponse } from '../models/responses/cottageResponse';
import { NgbCarouselModule, NgbModal, NgbRatingModule } from '@ng-bootstrap/ng-bootstrap';
import { ImageUtil } from '../utils/images.util';
import { LeafletModule } from '@bluehalo/ngx-leaflet';
import { Icon, icon, latLng, Layer, MapOptions, marker, tileLayer } from 'leaflet';
import { CommonModule, DatePipe } from '@angular/common';
import { MakeReservationComponent } from '../make-reservation/make-reservation.component';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-cottage',
  standalone: true,
  imports: [NgbCarouselModule, LeafletModule, CommonModule, DatePipe, NgbRatingModule],
  templateUrl: './cottage.component.html',
  styleUrl: './cottage.component.css'
})
export class CottageComponent {

  constructor(
    private cottageService: CottageService,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private modalService: NgbModal
  ) { }

  tourist!: NonAdminResponse;
  cottage: CottageResponse = new CottageResponse;
  errorMessage: string = '';

  options!: MapOptions;
  layer!: Layer;

  ngOnInit(): void {
    this.loadNonadmin();
    this.loadCottage();
  }

  private loadNonadmin(): void {
    this.tourist = this.authService.getNonadmin()!;
  }

  private loadCottage(): void {
    this.cottageService.getById(this.activatedRoute.snapshot.paramMap.get('id')!).subscribe({
      next: cottage => {
        this.cottage = cottage;
        this.cottage.reservations.forEach((r) => {
          r.datetimeStart = new Date(r.datetimeStart);
          r.datetimeEnd = new Date(r.datetimeEnd);
        });
        this.cottage.reservations.sort((a, b) => {
          return b.datetimeStart.getTime() - a.datetimeStart.getTime();
        });
        this.showmap();
      },
      error: err => {
        this.errorMessage = err.message;
      }
    });
  }

  showmap(): void {
    this.options = {
      layers: [
        tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          maxZoom: 15,
          attribution: '&copy; OpenStreetMap contributors'
        })
      ],
      zoom: 10,
      center: latLng(this.cottage.latitude, this.cottage.longitude)
    };

    this.layer = marker([this.cottage.latitude, this.cottage.longitude], {
      icon: icon({
        ...Icon.Default.prototype.options,
        iconUrl: 'assets/marker-icon.png',
        iconRetinaUrl: 'assets/marker-icon-2x.png',
        shadowUrl: 'assets/marker-shadow.png'
      })
    }).bindPopup(`<b>${this.cottage.name}</b><br>${this.cottage.location}`);
  }

  getImageUrl(cottagePhotoPath: string): string {
    return ImageUtil.getImageUrl(cottagePhotoPath);
  }

  openMakeReservationModal() {
    const buttonElement = document.activeElement as HTMLElement;
    buttonElement.blur();
    const modalRef = this.modalService.open(MakeReservationComponent, {
      centered: true,
      size: 'lg'
    });
    modalRef.componentInstance.nonadmin = this.tourist;
    modalRef.componentInstance.cottage = this.cottage;

    modalRef.result.then(
      (isSuccess: boolean) => {
        if (isSuccess) {
          alert('Успешно сте направили резервацију.');
        }
      },
      () => {

      }
    );
  }

}
