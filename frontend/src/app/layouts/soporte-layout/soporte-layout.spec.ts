import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SoporteLayout } from './soporte-layout';

describe('SoporteLayout', () => {
  let component: SoporteLayout;
  let fixture: ComponentFixture<SoporteLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SoporteLayout]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SoporteLayout);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
