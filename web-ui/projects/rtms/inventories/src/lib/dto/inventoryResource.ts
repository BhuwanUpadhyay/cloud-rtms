import { Resource } from '../../../../utils/src/lib/hateos-action/resource';
import { ProductLineResource } from './productLineResource';

export interface InventoryResource extends Resource {
  inventoryId: string;
  name: string;
  createdAt: string;
  productLines: ProductLineResource[];
}
