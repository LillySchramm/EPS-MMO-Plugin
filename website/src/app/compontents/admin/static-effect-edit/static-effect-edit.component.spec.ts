import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StaticEffectEditComponent } from './static-effect-edit.component';

describe('StaticEffectEditComponent', () => {
  let component: StaticEffectEditComponent;
  let fixture: ComponentFixture<StaticEffectEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StaticEffectEditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StaticEffectEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
