import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpleadoDetails } from './empleado-details';

describe('EmpleadoDetails', () => {
  let component: EmpleadoDetails;
  let fixture: ComponentFixture<EmpleadoDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmpleadoDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmpleadoDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
