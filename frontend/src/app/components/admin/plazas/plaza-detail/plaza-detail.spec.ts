import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlazaDetail } from './plaza-detail';

describe('PlazaDetail', () => {
  let component: PlazaDetail;
  let fixture: ComponentFixture<PlazaDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlazaDetail]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlazaDetail);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
