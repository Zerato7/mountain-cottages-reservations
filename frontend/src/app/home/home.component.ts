import { Component } from '@angular/core';
import { AllCottagesComponent } from '../all-cottages/all-cottages.component';
import { GeneralService } from '../services/general.service';
import { GeneralInfo } from '../models/responses/generalInfo';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [AllCottagesComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  constructor(
    private generalService: GeneralService
  ) { }

  generalInfo: GeneralInfo = new GeneralInfo();

  ngOnInit() {
    this.generalService.getInfo().subscribe({
      next: gi => {
        this.generalInfo = gi;
      },
      error: err => {

      }
    });
  }

}
