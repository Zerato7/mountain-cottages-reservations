import { Component } from '@angular/core';
import { CottageService } from '../services/cottage.service';
import { AuthService } from '../services/auth.service';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { CottageResponse } from '../models/responses/cottageResponse';
import { ReservationStatus } from '../models/requests/makeReservation';
import { ChartData } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

type CottageWeekStat = {
  cottageName: string,
  weekdayCount: number,
  weekendCount: number
}

@Component({
  selector: 'app-statistics',
  standalone: true,
  imports: [BaseChartDirective],
  templateUrl: './statistics.component.html',
  styleUrl: './statistics.component.css'
})
export class StatisticsComponent {

  constructor(
    private cottageService: CottageService,
    private authService: AuthService
  ) { }

  host!: NonAdminResponse;
  cottages: CottageResponse[] = [];
  barChartData: ChartData<'bar'> = {
    labels: [],
    datasets: []
  };
  weekStats: CottageWeekStat[] = [];

  ngOnInit(): void {
    this.loadNonadmin();
    this.loadCottages();
  }

  private loadNonadmin(): void {
    this.host = this.authService.getNonadmin()!;
  }

  private loadCottages(): void {
    this.cottageService.getMy(this.host.id).subscribe({
      next: cottages => {
        this.barChartData = this.barChartsPerMonth(cottages);
        this.calculateWeekStats(cottages);
      },
      error: err => {

      }
    });
  }

  private barChartsPerMonth(cottages: CottageResponse[]): ChartData<'bar'> {
    const months = [
      'Јан', 'Феб', 'Мар', 'Апр',
      'Мај', 'Јун', 'Јул', 'Авг',
      'Сеп', 'Окт', 'Нов', 'Дец'];

    const datasets = cottages.map((cottage) => {
      const counts = new Array(12).fill(0);

      cottage.reservations.filter(r =>
        r.status === ReservationStatus.ACCEPTED_BY_OWNER && r.cottageName === cottage.name)
        .forEach(r => {
          const month = new Date(r.datetimeStart).getMonth();
          counts[month]++;
        });

      return {
        label: cottage.name,
        data: counts
      };
    });

    return {
      labels: months,
      datasets
    };
  }

  isBarChartEmpty(): boolean {
    return !this.barChartData.datasets?.length ||
      this.barChartData.datasets.every(ds =>
        !ds.data || ds.data.every(v => !v)
      );
  }

  private calculateWeekStats(cottages: CottageResponse[]): void {
    cottages.forEach((cottage) => {
      let weekdayCount = 0;
      let weekendCount = 0;

      cottage.reservations.filter(r =>
        r.status === ReservationStatus.ACCEPTED_BY_OWNER && r.cottageName === cottage.name)
        .forEach(r => {
          const day = new Date(r.datetimeStart).getDay();
          if (day == 0 || day == 6) weekendCount++; else
            weekdayCount++;
        });

      this.weekStats.push({
        cottageName: cottage.name,
        weekdayCount: weekdayCount,
        weekendCount: weekendCount
      });
    });
  }

}
