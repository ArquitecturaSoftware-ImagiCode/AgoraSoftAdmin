import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaneacionLayout } from './planeacion-layout';

describe('AuditoriaLayout', () => {
  let component: PlaneacionLayout;
  let fixture: ComponentFixture<PlaneacionLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlaneacionLayout]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlaneacionLayout);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
