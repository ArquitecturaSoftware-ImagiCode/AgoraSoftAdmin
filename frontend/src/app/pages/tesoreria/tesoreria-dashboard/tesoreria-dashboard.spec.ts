import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TesoreriaDashboard } from './tesoreria-dashboard';

describe('TesoreriaDashboard', () => {
  let component: TesoreriaDashboard;
  let fixture: ComponentFixture<TesoreriaDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TesoreriaDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TesoreriaDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
