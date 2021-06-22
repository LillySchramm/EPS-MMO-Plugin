import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PosHelperComponent } from './pos-helper.component';

describe('PosHelperComponent', () => {
  let component: PosHelperComponent;
  let fixture: ComponentFixture<PosHelperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PosHelperComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PosHelperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
