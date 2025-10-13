import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArquitecturaDashboard } from './arquitectura-dashboard';

describe('ArquitecturaDashboard', () => {
  let component: ArquitecturaDashboard;
  let fixture: ComponentFixture<ArquitecturaDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArquitecturaDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ArquitecturaDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
