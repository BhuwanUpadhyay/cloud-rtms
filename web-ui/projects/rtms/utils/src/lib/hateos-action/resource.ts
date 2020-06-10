export interface Resource {
  _links?: Link[];
}

export type Method = 'GET' | 'POST' | 'DELETE' | 'PUT';

export interface Link {
  path: string;
  rel: string;
  method: Method;
}
