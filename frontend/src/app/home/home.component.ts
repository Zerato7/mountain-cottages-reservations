import { Component } from '@angular/core';
import { AllCottagesComponent } from '../all-cottages/all-cottages.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [AllCottagesComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
