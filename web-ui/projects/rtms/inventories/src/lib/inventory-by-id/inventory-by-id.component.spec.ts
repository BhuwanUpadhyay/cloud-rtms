import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryByIdComponent } from './inventory-by-id.component';

describe('InventoryDetailComponent', () => {
  let component: InventoryByIdComponent;
  let fixture: ComponentFixture<InventoryByIdComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InventoryByIdComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryByIdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
