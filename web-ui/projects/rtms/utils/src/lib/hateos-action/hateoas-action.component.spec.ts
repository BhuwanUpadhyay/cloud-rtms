import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HateoasActionComponent } from './hateoas-action.component';

describe('HateosActionComponent', () => {
  let component: HateoasActionComponent;
  let fixture: ComponentFixture<HateoasActionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [HateoasActionComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HateoasActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
