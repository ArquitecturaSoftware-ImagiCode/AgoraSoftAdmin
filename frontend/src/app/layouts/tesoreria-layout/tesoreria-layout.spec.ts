import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TesoreriaLayout } from './tesoreria-layout';

describe('TesoreriaLayout', () => {
  let component: TesoreriaLayout;
  let fixture: ComponentFixture<TesoreriaLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TesoreriaLayout]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TesoreriaLayout);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
