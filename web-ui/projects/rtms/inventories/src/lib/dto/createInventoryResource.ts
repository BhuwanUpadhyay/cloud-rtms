import { ProductLineResource } from './productLineResource';

export interface CreateInventoryResource {
  name: string;
  productLines: ProductLineResource[];
}
