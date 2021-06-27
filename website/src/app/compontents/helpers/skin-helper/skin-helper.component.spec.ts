import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SkinHelperComponent } from './skin-helper.component';

describe('SkinHelperComponent', () => {
  let component: SkinHelperComponent;
  let fixture: ComponentFixture<SkinHelperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SkinHelperComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SkinHelperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
