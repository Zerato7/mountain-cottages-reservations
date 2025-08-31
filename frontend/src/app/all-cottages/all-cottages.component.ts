import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { CottageService } from '../services/cottage.service';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { CottageResponse } from '../models/responses/cottageResponse';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DateTimeUtil } from '../utils/datetime.util';
import { Router } from '@angular/router';
import { NgbRatingModule } from '@ng-bootstrap/ng-bootstrap';

type SortCategory = 'name' | 'location' | 'avg-rating';

type CottageExt = {
  cottage: CottageResponse,
  avgRating: number
}

@Component({
  selector: 'app-all-cottages',
  standalone: true,
  imports: [FormsModule, CommonModule, NgbRatingModule],
  templateUrl: './all-cottages.component.html',
  styleUrl: './all-cottages.component.css'
})
export class AllCottagesComponent {

  constructor(
    public authService: AuthService,
    private cottageService: CottageService,
    private router: Router
  ) { }

  tourist: NonAdminResponse | null = null;
  cottagesExt: CottageExt[] = [];
  filteredCottagesExt: CottageExt[] = [];
  name: string = '';
  location: string = '';
  isAscending: Record<SortCategory, boolean> = { 'name': false, 'location': false, 'avg-rating': false };
  sortCategory: SortCategory = 'name';

  ngOnInit() {
    this.loadNonadmin();
    this.loadMyCottages();
  }

  private loadNonadmin(): void {
    this.tourist = this.authService.getNonadmin()!;
  }

  private loadMyCottages(): void {
    this.cottageService.getAll().subscribe({
      next: cottages => {
        this.cottagesExt = cottages.map(cottage => ({
            cottage: cottage,
            avgRating: this.cottageService.avgRating(cottage)
        }));
        this.filteredCottagesExt = this.cottagesExt;
        //console.log(this.cottagesExt);
        this.sort(this.sortCategory);
      },
      error: err => {

      }
    })
  }

  search(): void {
    this.filteredCottagesExt = this.cottagesExt.filter((cottageExt) => {
      if (this.name &&
          !cottageExt.cottage.name.toLowerCase().includes(this.name.toLowerCase()))
          return false;
      if (this.location &&
          !cottageExt.cottage.location.toLowerCase().includes(this.location.toLowerCase()))
          return false;
      return true;
    });
  }

  cancelSearch(): void {
    this.name = '';
    this.location = '';
    this.filteredCottagesExt = this.cottagesExt;
    this.sort(this.sortCategory);
  }

  onSort(category: SortCategory): void {
    this.sortCategory = category;
    this.isAscending[category] = !this.isAscending[category];
    this.sort(category);
  }

  sort(category: SortCategory): void {
    const order = this.isAscending[category] ? 1 : -1;
    this.filteredCottagesExt.sort((a, b) => {
      switch (this.sortCategory) {
        case 'name':
          return a.cottage.name < b.cottage.name ?
            order : a.cottage.name > b.cottage.name ? -order : 0;
        case 'location':
          return a.cottage.location < b.cottage.location ?
            order : a.cottage.location > b.cottage.location ? -order : 0;
        case 'avg-rating':
          return (b.avgRating - a.avgRating) * order;
      }
    });
  }

  getAvgRating(cottage: CottageResponse): number {
    return this.cottageService.avgRating(cottage);
  }

  showDetails(cottage: CottageResponse): void {
    if (this.tourist == null) return;

    this.router.navigate(['/cottage', cottage.id]);
  }

}
