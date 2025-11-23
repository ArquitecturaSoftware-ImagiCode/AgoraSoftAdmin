import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlazasLista } from './plazas-lista';

describe('PlazasLista', () => {
  let component: PlazasLista;
  let fixture: ComponentFixture<PlazasLista>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlazasLista]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlazasLista);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
