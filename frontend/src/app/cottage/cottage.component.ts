import { Component } from '@angular/core';
import { CottageService } from '../services/cottage.service';
import { ActivatedRoute } from '@angular/router';
import { CottageResponse } from '../models/responses/cottageResponse';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';
import { ImageUtil } from '../utils/images.util';
import { LeafletModule } from '@bluehalo/ngx-leaflet';
import { Icon, icon, latLng, Layer, MapOptions, marker, tileLayer } from 'leaflet';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cottage',
  standalone: true,
  imports: [NgbCarouselModule, LeafletModule, CommonModule],
  templateUrl: './cottage.component.html',
  styleUrl: './cottage.component.css'
})
export class CottageComponent {

  constructor(
    private cottageService: CottageService,
    private activatedRoute: ActivatedRoute
  ) { }

  cottage: CottageResponse = new CottageResponse;
  errorMessage: string = '';

  options!: MapOptions;
  layer!: Layer;

  ngOnInit(): void {
    this.cottageService.getByName(this.activatedRoute.snapshot.paramMap.get('name')!).subscribe({
      next: cottage => {
        this.cottage = cottage;
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

}
