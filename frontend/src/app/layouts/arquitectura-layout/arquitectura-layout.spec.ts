import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArquitecturaLayout } from './arquitectura-layout';

describe('ArquitecturaLayout', () => {
  let component: ArquitecturaLayout;
  let fixture: ComponentFixture<ArquitecturaLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArquitecturaLayout]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ArquitecturaLayout);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
