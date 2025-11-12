import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlazaAprobacion } from './plaza-aprobacion';

describe('PlazaAprobacion', () => {
  let component: PlazaAprobacion;
  let fixture: ComponentFixture<PlazaAprobacion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlazaAprobacion]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlazaAprobacion);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
