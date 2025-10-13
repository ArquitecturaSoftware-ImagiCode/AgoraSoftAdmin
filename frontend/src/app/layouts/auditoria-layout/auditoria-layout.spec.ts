import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditorLayout } from './auditoria-layout';

describe('AuditorLayout', () => {
  let component: AuditorLayout;
  let fixture: ComponentFixture<AuditorLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuditorLayout]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuditorLayout);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
