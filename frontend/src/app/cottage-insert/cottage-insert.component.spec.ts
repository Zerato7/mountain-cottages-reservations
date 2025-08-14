import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageInsertComponent } from './cottage-insert.component';

describe('CottageInsertComponent', () => {
  let component: CottageInsertComponent;
  let fixture: ComponentFixture<CottageInsertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CottageInsertComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CottageInsertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
