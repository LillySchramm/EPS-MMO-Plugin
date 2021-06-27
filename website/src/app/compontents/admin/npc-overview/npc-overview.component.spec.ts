import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NpcOverviewComponent } from './npc-overview.component';

describe('NpcOverviewComponent', () => {
  let component: NpcOverviewComponent;
  let fixture: ComponentFixture<NpcOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NpcOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NpcOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
