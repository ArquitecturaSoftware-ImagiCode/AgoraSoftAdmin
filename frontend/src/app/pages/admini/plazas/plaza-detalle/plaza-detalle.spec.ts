import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlazaDetalle } from './plaza-detalle';

describe('PlazaDetalle', () => {
  let component: PlazaDetalle;
  let fixture: ComponentFixture<PlazaDetalle>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlazaDetalle]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlazaDetalle);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
