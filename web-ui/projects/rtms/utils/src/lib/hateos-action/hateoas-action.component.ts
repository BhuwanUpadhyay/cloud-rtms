import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Link, Method, Resource } from './resource';
import { ThemePalette } from '@angular/material/core';

@Component({
  selector: 'rtms-hateoas-action',
  templateUrl: './hateoas-action.component.html',
  styleUrls: ['./hateoas-action.component.scss'],
})
export class HateoasActionComponent implements OnInit {
  @Input() resource: Resource;
  @Input() row = true;
  @Output() linkAction: EventEmitter<Link> = new EventEmitter();

  constructor() {}

  ngOnInit(): void {}

  getColor(method: Method): ThemePalette {
    return 'primary';
  }
}
