import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Heather } from './header';

describe('Heather', () => {
  let component: Heather;
  let fixture: ComponentFixture<Heather>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Heather]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Heather);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
