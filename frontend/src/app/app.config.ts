import { ApplicationConfig, importProvidersFrom, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { OWL_DATE_TIME_LOCALE, OwlDateTimeIntl, OwlDateTimeModule, OwlNativeDateTimeModule } from '@danielmoncada/angular-datetime-picker';

import { registerLocaleData } from '@angular/common';
import localeSrCyrl from '@angular/common/locales/sr-Cyrl';
import { SerbianCyrillicIntl } from './utils/datetime.util';
import { provideCharts, withDefaultRegisterables } from 'ng2-charts';
import { Chart, registerables } from 'chart.js';

registerLocaleData(localeSrCyrl);
Chart.register(...registerables);

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(),
    provideAnimations(),
    importProvidersFrom(OwlDateTimeModule, OwlNativeDateTimeModule),
    { provide: OWL_DATE_TIME_LOCALE, useValue: 'sr-Cyrl' },
    { provide: OwlDateTimeIntl, useClass: SerbianCyrillicIntl},
    provideCharts(withDefaultRegisterables())
  ]
};
