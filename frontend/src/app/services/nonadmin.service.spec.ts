import { TestBed } from '@angular/core/testing';

import { NonadminService } from './nonadmin.service';

describe('NonadminService', () => {
  let service: NonadminService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NonadminService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
