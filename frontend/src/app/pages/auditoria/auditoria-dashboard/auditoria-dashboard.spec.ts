import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditoriaDashboard } from './auditoria-dashboard';

describe('AuditoriaDashboard', () => {
  let component: AuditoriaDashboard;
  let fixture: ComponentFixture<AuditoriaDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuditoriaDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuditoriaDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
