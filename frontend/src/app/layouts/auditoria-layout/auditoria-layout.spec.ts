import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditoriaLayout } from './auditoria-layout';

describe('AuditoriaLayout', () => {
  let component: AuditoriaLayout;
  let fixture: ComponentFixture<AuditoriaLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuditoriaLayout]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuditoriaLayout);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
