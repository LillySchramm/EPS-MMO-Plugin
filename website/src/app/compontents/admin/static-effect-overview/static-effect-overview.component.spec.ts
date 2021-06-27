import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StaticEffectOverviewComponent } from './static-effect-overview.component';

describe('StaticEffectOverviewComponent', () => {
  let component: StaticEffectOverviewComponent;
  let fixture: ComponentFixture<StaticEffectOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StaticEffectOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StaticEffectOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
