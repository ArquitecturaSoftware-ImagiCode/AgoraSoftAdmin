import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModulosArquitectura } from './modulos-arquitectura';

describe('ModulosArquitectura', () => {
  let component: ModulosArquitectura;
  let fixture: ComponentFixture<ModulosArquitectura>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModulosArquitectura]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModulosArquitectura);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
