import { Inject, Injectable } from '@angular/core';
import { InventoryResource } from './dto/inventoryResource';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { gatewayBasePath } from '../../../utils/src/lib/gateway';
import { Page, PageRequest } from '../../../utils/src/lib/data/page';
import { CreateInventoryResource } from './dto/createInventoryResource';
import { InventoryIdResource } from './dto/inventoryIdResource';
import { WorkflowResource } from './dto/workflowResource';
import { Link, Resource } from '../../../utils/src/lib/hateos-action/resource';
import { InventorySummaryQuery } from './dto/inventorySummaryQuery';
import { InventorySummaryResource } from './dto/inventorySummaryResource';

@Injectable({
  providedIn: 'root',
})
export class InventoriesService {
  constructor(@Inject(gatewayBasePath) private gateway: string, private http: HttpClient) {}

  list(
    link: Link,
    request: PageRequest<InventorySummaryResource>,
    query: InventorySummaryQuery
  ): Observable<Page<InventorySummaryResource>> {
    return this.http.get<Page<InventorySummaryResource>>(`${this.gateway}${link.path}`);
  }

  getById(link: Link): Observable<InventoryResource> {
    return this.http.get<InventoryResource>(link.path);
  }

  save(link: Link, resource: CreateInventoryResource): Observable<InventoryIdResource> {
    return this.http.post<InventoryIdResource>(`${this.gateway}${link.path}`, resource);
  }

  workflow(link: Link, resource: WorkflowResource): Observable<InventoryIdResource> {
    return this.http.put<InventoryIdResource>(`${this.gateway}${link.path}`, resource);
  }

  getActions(): Observable<Resource> {
    return this.http.get<Resource>(`${this.gateway}/apps/actions`);
  }
}
