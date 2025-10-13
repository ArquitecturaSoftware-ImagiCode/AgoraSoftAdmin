import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaneacionDashboard } from './planeacion-dashboard';

describe('PlaneacionDashboard', () => {
  let component: PlaneacionDashboard;
  let fixture: ComponentFixture<PlaneacionDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlaneacionDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlaneacionDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
