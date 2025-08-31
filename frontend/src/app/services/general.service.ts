import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GeneralInfo } from '../models/responses/generalInfo';

@Injectable({
  providedIn: 'root'
})
export class GeneralService {

  constructor(private http: HttpClient) { }

  private baseBackPath = 'http://localhost:8080';
  private curBackPath = this.baseBackPath;

  getInfo() {
    return this.http.get<GeneralInfo>(`${this.curBackPath}/getInfo`);
  }

}
