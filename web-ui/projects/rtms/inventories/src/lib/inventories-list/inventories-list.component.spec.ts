import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoriesListComponent } from './inventories-list.component';

describe('GridComponent', () => {
  let component: InventoriesListComponent;
  let fixture: ComponentFixture<InventoriesListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InventoriesListComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoriesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
