import { Matchers, Pact } from '@pact-foundation/pact';
import * as path from 'path';
import { TestBed } from '@angular/core/testing';
import { InventoriesService } from '../projects/rtms/inventories/src/lib/inventories.service';
import { gatewayBasePath } from '../projects/rtms/utils/src/lib/gateway';
import { InventoryResource } from '../projects/rtms/inventories/src/lib/dto/inventoryResource';
import { HttpClientModule } from '@angular/common/http';
import { CreateInventoryResource } from '../projects/rtms/inventories/src/lib/dto/createInventoryResource';
import { InventoryIdResource } from '../projects/rtms/inventories/src/lib/dto/inventoryIdResource';
import { WorkflowResource } from '../projects/rtms/inventories/src/lib/dto/workflowResource';
import { Link, Resource } from '../projects/rtms/utils/src/lib/hateos-action/resource';
import { InventorySummaryResource } from '../projects/rtms/inventories/src/lib/dto/inventorySummaryResource';
import { Page } from '../projects/rtms/utils/src/lib/data/page';

describe('InventoriesServicePact', () => {
  const mockPort = 1234;
  const url = `http://localhost:${mockPort}`;

  const provider: Pact = new Pact({
    port: mockPort,
    log: path.resolve(process.cwd(), 'dist', 'logs', 'pacts.log'),
    dir: path.resolve(process.cwd(), 'dist', 'pacts'),
    spec: 3,
    logLevel: 'info',
    consumer: 'web-ui',
    provider: 'rtms-inventory',
  });

  // Setup Pact mock server for this service
  beforeAll(async () => {
    await provider.setup();
  });

  // Configure Angular Testbed for this service
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        InventoriesService,
        {
          provide: gatewayBasePath,
          useValue: `${url}`,
        },
      ],
    });
  });

  // Verify mock service
  afterEach(async () => {
    await provider.verify();
  });

  // Create contract
  afterAll(async () => {
    await provider.finalize();
  });

  function GET(pactState: string, pactUponReceiving: string, link: Link, expected: any) {
    return provider.addInteraction({
      state: pactState,
      uponReceiving: pactUponReceiving,
      withRequest: {
        method: link.method,
        path: link.path,
      },
      willRespondWith: {
        status: 200,
        body: Matchers.somethingLike(expected),
      },
    });
  }

  function POST_OR_PUT(
    pactState: string,
    pactUponReceiving: string,
    pactRequest: any,
    link: Link,
    expected: any,
    httpStatus: number
  ) {
    return provider.addInteraction({
      state: pactState,
      uponReceiving: pactUponReceiving,
      withRequest: {
        method: link.method,
        path: link.path,
        body: pactRequest,
        headers: {
          'Content-Type': 'application/json',
        },
      },
      willRespondWith: {
        status: httpStatus,
        body: Matchers.somethingLike(expected),
        headers: {
          'Content-Type': 'application/json',
        },
      },
    });
  }

  function POST(pactState: string, pactUponReceiving: string, pactRequest: any, link: Link, expected: any) {
    return POST_OR_PUT(pactState, pactUponReceiving, pactRequest, link, expected, 201);
  }

  function PUT(pactState: string, pactUponReceiving: string, pactRequest: any, link: Link, expected: any) {
    return POST_OR_PUT(pactState, pactUponReceiving, pactRequest, link, expected, 200);
  }

  describe('getActions()', () => {
    const link: Link = {
      path: `/inventories/actions`,
      method: 'GET',
      rel: 'get-actions',
    };

    const expected: Resource = {
      _links: [
        {
          rel: 'create',
          method: 'POST',
          path: `/inventories`,
        },
        {
          rel: 'list',
          method: 'GET',
          path: `/inventories`,
        },
      ],
    };

    beforeAll(async () => {
      const pactState = `inventory actions`;
      const pactUponReceiving = 'a request to GET inventory actions';
      await GET(pactState, pactUponReceiving, link, expected);
    });

    it('should get inventory actions', async () => {
      const service: InventoriesService = TestBed.inject(InventoriesService);

      await service
        .getActions()
        .toPromise()
        .then((response) => {
          expect(response).toEqual(expected);
        });
    });
  });

  describe('create()', () => {
    const link: Link = {
      path: `/inventories`,
      method: 'POST',
      rel: 'create',
    };

    const id = '2';

    const expected: InventoryIdResource = {
      inventoryId: id,
      _links: [
        {
          rel: 'get',
          method: 'GET',
          path: `/inventories/${id}`,
        },
      ],
    };

    const request: CreateInventoryResource = {
      name: 'name',
      productLines: [
        {
          productId: '1',
          quantity: 10,
        },
      ],
    };

    beforeAll(async () => {
      const pactState = `provider accepts a new inventory`;
      const pactUponReceiving = 'a request to POST inventory';
      await POST(pactState, pactUponReceiving, request, link, expected);
    });

    it('should create inventory', async () => {
      const service: InventoriesService = TestBed.inject(InventoriesService);
      await service
        .save(link, request)
        .toPromise()
        .then((response) => {
          expect(response).toEqual(expected);
        });
    });
  });

  describe('list()', () => {
    const link: Link = {
      path: `/inventories`,
      method: 'GET',
      rel: 'list',
    };
    const id = '1';

    const expected: Page<InventorySummaryResource> = {
      content: [
        {
          name: 'name',
          createdAt: '2020-05-16T19:20:30.45+01:00',
          productLines: [
            {
              productId: '1',
              quantity: 10,
            },
          ],
          inventoryId: id,
          _links: [
            {
              rel: 'get',
              method: 'GET',
              path: `/inventories/${id}`,
            },
          ],
        },
      ],
      number: 1,
      size: 20,
      totalElements: 200,
    };

    beforeAll(async () => {
      const pactState = `inventories list`;
      const pactUponReceiving = 'a request to GET inventories';
      await GET(pactState, pactUponReceiving, link, expected);
    });

    it('should create inventory', async () => {
      const service: InventoriesService = TestBed.inject(InventoriesService);
      await service
        .list(link, null, null)
        .toPromise()
        .then((response) => {
          expect(response).toEqual(expected);
        });
    });
  });

  describe('get()', () => {
    const id = '1';

    const link: Link = {
      path: `/inventories/${id}`,
      method: 'GET',
      rel: 'get',
    };

    const expected: InventoryResource = {
      name: 'name',
      createdAt: '2020-05-16T19:20:30.45+01:00',
      productLines: [
        {
          productId: '1',
          quantity: 10,
        },
      ],
      inventoryId: id,
      _links: [
        {
          rel: 'repair',
          method: 'PUT',
          path: `/inventories/${id}/repair`,
        },
        {
          rel: 'verify',
          method: 'PUT',
          path: `/inventories/${id}/verify`,
        },
      ],
    };

    beforeAll(async () => {
      const pactState = `inventory 1 exists`;
      const pactUponReceiving = 'a request to GET inventory';
      await GET(pactState, pactUponReceiving, link, expected);
    });

    it('should get inventory', async () => {
      const service: InventoriesService = TestBed.inject(InventoriesService);

      await service
        .getById(link)
        .toPromise()
        .then((response) => {
          expect(response).toEqual(expected);
        });
    });
  });

  describe('workflow()', () => {
    const id = '1';

    const link: Link = {
      path: `/inventories/${id}/verify`,
      method: 'PUT',
      rel: 'verify',
    };

    const expected: InventoryIdResource = {
      inventoryId: id,
      _links: [
        {
          rel: 'get',
          method: 'GET',
          path: `/inventories/${id}`,
        },
      ],
    };

    const request: WorkflowResource = {
      comment: 'my comment',
    };

    beforeAll(async () => {
      const pactState = `submit workflow for inventory 1`;
      const pactUponReceiving = 'a request to PUT inventory workflow';
      await PUT(pactState, pactUponReceiving, request, link, expected);
    });

    it('should submit inventory workflow', async () => {
      const service: InventoriesService = TestBed.inject(InventoriesService);
      await service
        .workflow(link, request)
        .toPromise()
        .then((response) => {
          expect(response).toEqual(expected);
        });
    });
  });

  describe('payloadWorkflow()', () => {
    const id = '1';

    const link: Link = {
      path: `/inventories/${id}/repair`,
      method: 'PUT',
      rel: 'repair',
    };

    const expected: InventoryIdResource = {
      inventoryId: id,
      _links: [
        {
          rel: 'get',
          method: 'GET',
          path: `/inventories/${id}`,
        },
      ],
    };

    const request: WorkflowResource = {
      comment: 'my comment',
      payloadJson: JSON.stringify({
        name: 'name',
        productLines: [
          {
            productId: '1',
            quantity: 10,
          },
          {
            productId: '2',
            quantity: 20,
          },
        ],
      }),
    };

    beforeAll(async () => {
      const pactState = `submit payload workflow for inventory 1`;
      const pactUponReceiving = 'a request to PUT inventory payload workflow';
      await PUT(pactState, pactUponReceiving, request, link, expected);
    });

    it('should submit inventory payload workflow', async () => {
      const service: InventoriesService = TestBed.inject(InventoriesService);
      await service
        .workflow(link, request)
        .toPromise()
        .then((response) => {
          expect(response).toEqual(expected);
        });
    });
  });
});
