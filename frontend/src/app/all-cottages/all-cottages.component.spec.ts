import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllCottagesComponent } from './all-cottages.component';

describe('AllCottagesComponent', () => {
  let component: AllCottagesComponent;
  let fixture: ComponentFixture<AllCottagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AllCottagesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllCottagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
