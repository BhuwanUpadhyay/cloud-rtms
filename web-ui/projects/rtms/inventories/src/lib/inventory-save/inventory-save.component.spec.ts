import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InventorySaveComponent } from './inventory-save.component';

describe('InventoryCreateComponent', () => {
  let component: InventorySaveComponent;
  let fixture: ComponentFixture<InventorySaveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InventorySaveComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventorySaveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
