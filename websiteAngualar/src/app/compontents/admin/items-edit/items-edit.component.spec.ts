import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemsEditComponent } from './items-edit.component';

describe('ItemsEditComponent', () => {
  let component: ItemsEditComponent;
  let fixture: ComponentFixture<ItemsEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ItemsEditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemsEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
