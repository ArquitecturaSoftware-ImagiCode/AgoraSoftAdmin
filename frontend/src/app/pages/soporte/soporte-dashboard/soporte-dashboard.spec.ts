import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SoporteDashboard } from './soporte-dashboard';

describe('SoporteDashboard', () => {
  let component: SoporteDashboard;
  let fixture: ComponentFixture<SoporteDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SoporteDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SoporteDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
