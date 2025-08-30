import { Component } from '@angular/core';
import { CottageService } from '../services/cottage.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CottageInsertComponent } from '../cottage-insert/cottage-insert.component';
import { AuthService } from '../services/auth.service';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { CottageResponse } from '../models/responses/cottageResponse';
import { CommonModule } from '@angular/common';
import { BgColourUtil } from '../utils/bg-colour.util';
import { DateTimeUtil } from '../utils/datetime.util';
import { UserType } from '../models/responses/userResponse';

type CottageExt = {
  cottage: CottageResponse,
  errorMessage: string
}

@Component({
  selector: 'app-my-cottages',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-cottages.component.html',
  styleUrl: './my-cottages.component.css'
})
export class MyCottagesComponent {

  constructor(
    private authService: AuthService,
    private cottageService: CottageService,
    private modalService: NgbModal
  ) { }

  host!: NonAdminResponse;
  cottagesExt: CottageExt[] = [];

  ngOnInit() {
    this.loadNonadmin();
    this.loadMyCottages();
  }

  private loadNonadmin(): void {
    this.host = this.authService.getNonadmin()!;
  }

  private loadMyCottages(): void {
    this.cottageService.getMy(this.host.id).subscribe({
      next: cottages => {
        this.cottagesExt = cottages.map(cottage => ({
          cottage: cottage,
          errorMessage: ''
        }));
      },
      error: err => {

      }
    })
  }

  openAddModal() {
    const buttonElement = document.activeElement as HTMLElement;
    buttonElement.blur();
    const modalRef = this.modalService.open(CottageInsertComponent, {
      centered: true,
      size: 'lg'
    });
    modalRef.componentInstance.ownerId = this.host.id;

    modalRef.result.then(
      (cottage: CottageResponse) => {
        this.cottagesExt.push({cottage: cottage, errorMessage: ''});
      },
      () => {

      }
    );
  }

  editCottage(cottageExt: CottageExt): void {
    cottageExt.errorMessage = '';
  }

  deleteCottage(cottageExt: CottageExt): void {
    this.cottageService.deleteCottage(cottageExt.cottage.id).subscribe({
      next: response => {
        this.loadMyCottages();
      },
      error: err => {
        cottageExt.errorMessage = err.message;
      }
    })
  }

  getBackgroundCardBodyClass(): string {
    return BgColourUtil.getCardBodyClass(UserType.HOST);
  }

  getBackgroundClass(): string {
    return BgColourUtil.getFooterBgClass(UserType.HOST);
  }

  isSummer(): boolean {
    return DateTimeUtil.isSummer();
  }

}
