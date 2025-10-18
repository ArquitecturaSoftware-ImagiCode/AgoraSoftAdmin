import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlazasListComponent } from './plazas-list';

describe('PlazasList', () => {
  let component: PlazasListComponent;
  let fixture: ComponentFixture<PlazasListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlazasListComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PlazasListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
