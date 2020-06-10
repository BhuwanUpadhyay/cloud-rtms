import { AfterContentChecked, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { InventoriesService } from '../inventories.service';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { InventoryResource } from '../dto/inventoryResource';
import { CreateInventoryResource } from '../dto/createInventoryResource';
import { Link } from '../../../../utils/src/lib/hateos-action/resource';

@Component({
  selector: 'rtms-inventory-save',
  templateUrl: './inventory-save.component.html',
  styleUrls: ['./inventory-save.component.scss'],
})
export class InventorySaveComponent implements OnInit, AfterContentChecked {
  form: FormGroup;
  formProductLines: FormArray;
  entity: InventoryResource = {} as any;
  displayedColumns: string[] = ['productId', 'quantity', 'action'];
  dataSource = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private inventories: InventoriesService,
    private cdRef: ChangeDetectorRef
  ) {}

  async ngOnInit() {
    this.form = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(5)]],
      productLines: this.fb.array([]),
    });
    this.formProductLines = this.form.get('productLines') as FormArray;
    this.entity._links = [{ rel: 'create', method: 'POST', path: '/inventories' }];
    const params: Params = await this.route.params.toPromise();
    const id = params?.id;
    this.entity = await this.inventories.getById(id).toPromise();
  }

  ngAfterContentChecked() {
    this.cdRef.detectChanges();
  }

  async onLinkAction(link: Link) {
    switch (link?.rel) {
      case 'create':
        const resource: CreateInventoryResource = { name: this.form.get('name').value, productLines: [] };
        await this.inventories.save(link, resource).toPromise();
        await this.router.navigate(['../'], { relativeTo: this.route });
        break;
    }
  }

  addProductLine() {
    this.dataSource.push({ productId: '', quantity: null });
    this.setProductLinesForm();
  }

  removeProductLine(rowIndex: number) {
    this.dataSource.splice(rowIndex, 1);
    this.setProductLinesForm();
  }

  private setProductLinesForm() {
    this.dataSource.forEach((product) => {
      this.formProductLines.push(this.setProductLinesFormArray(product));
    });
    this.dataSource = [...this.dataSource]; // refresh the dataSource
  }

  private setProductLinesFormArray(product) {
    return this.fb.group({
      productId: [product?.productId],
      quantity: [product?.quantity],
    });
  }
}
