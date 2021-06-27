import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemsOverviewComponent } from './items-overview.component';

describe('ItemsOverviewComponent', () => {
  let component: ItemsOverviewComponent;
  let fixture: ComponentFixture<ItemsOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ItemsOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemsOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
