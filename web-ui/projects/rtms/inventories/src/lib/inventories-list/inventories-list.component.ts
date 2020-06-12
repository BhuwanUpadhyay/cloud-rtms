import { AfterContentChecked, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { InventoriesService } from '../inventories.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Sort } from '../../../../utils/src/lib/data/page';
import { PaginatedDataSource } from '../../../../utils/src/lib/data/paginated-datasource';
import { Link, Resource } from '../../../../utils/src/lib/hateos-action/resource';
import { InventorySummaryQuery } from '../dto/inventorySummaryQuery';
import { Optional } from '../../../../utils/src/lib/optional';
import { InventorySummaryResource } from '../dto/inventorySummaryResource';

@Component({
  selector: 'rtms-inventories-list',
  templateUrl: './inventories-list.component.html',
  styleUrls: ['./inventories-list.component.scss'],
})
export class InventoriesListComponent implements OnInit, AfterContentChecked {
  displayedColumns = ['inventoryId', 'name', 'createdAt'];
  initialSort: Sort<InventorySummaryResource> = { property: 'inventoryId', order: 'asc' };

  data: PaginatedDataSource<InventorySummaryResource, InventorySummaryQuery>;

  resource: Resource;

  constructor(
    private inventories: InventoriesService,
    private cdRef: ChangeDetectorRef,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  async ngOnInit() {
    this.resource = await this.inventories.getActions().toPromise();
    Optional.of(this.resource)
      .map((v) => v._links)
      .map((v) => v.find((i) => i.rel === 'list'))
      .ifPresent((v) => {
        this.data = new PaginatedDataSource<InventorySummaryResource, InventorySummaryQuery>(
          (request, query) => this.inventories.list(v, request, query),
          this.initialSort,
          { search: '', createdAt: null },
          20
        );
      });
  }

  ngAfterContentChecked() {
    this.cdRef.detectChanges();
  }

  async onLinkAction(link: Link) {
    switch (link?.rel) {
      case 'create':
        await this.router.navigate(['create'], { relativeTo: this.route });
        break;
      case 'list':
        this.data.disconnect();
        break;
    }
  }
}
