import { Home } from './Home';
import { cleanup, render, RenderResult } from '@testing-library/react';
import * as React from 'react';
import { Product } from '../domain/product/Product';
import { Catalog, CatalogModel } from '../domain/catalog/Catalog';
import { act } from 'react-dom/test-utils';
import { ProductApi } from '../domain/product/ProductApi';
import { Gateway } from '../domain/Gateway';
import { CartModel } from '../domain/cart/CartModel';
import { PurchaseApi } from '../domain/purchase/PurchaseApi';

describe('Home', () => {
  afterEach(cleanup);

  it('should render welcome dialogue and not the category section if no categories to filter', async () => {
    const productApi = new ProductApi();
    productApi.getProducts = jest.fn().mockReturnValue(Promise.resolve([]));
    let home: RenderResult | null = null;
    act(() => {
      home = render(
        <Home cartModel={ new CartModel() }
              purchaseApi={ new PurchaseApi() }
              gateway={ new Gateway({ productApi }) }
              categories={ [] }
        />
      );
    });

    const { queryByText, findByText } = home!;
    expect(queryByText('Filters:')).toBeNull();
    expect(await findByText('Monomart')).not.toBeNull();
  });

  it('should render the passed in categories and products', async () => {
    const categories: Catalog[] = [
      new CatalogModel('category1', 'Category 1'),
      new CatalogModel('category2', 'Category 2')
    ];

    const products: Product[] = [
      {
        id: '12345',
        name: 'Fake Product 1',
        description: 'Fake Product 1 description',
        price: '19.99',
        imageSrc: 'https://picsum.photos/200/300',
        imageAlt: 'Product 1 Alt',
        quantity: 10
      },
      {
        id: '12346',
        name: 'Fake Product 2',
        description: 'Fake Product 2 description',
        price: '19.99',
        imageSrc: 'https://picsum.photos/200/300',
        imageAlt: 'Product 2 Alt',
        quantity: 10
      },
      {
        id: '12347',
        name: 'Fake Product 3',
        description: 'Fake Product 3 description',
        price: '19.99',
        imageSrc: 'https://picsum.photos/200/300',
        imageAlt: 'Product 3 Alt',
        quantity: 10
      }
    ];
    const productApi = new ProductApi();
    productApi.getProducts = jest.fn().mockReturnValue(Promise.resolve(products));

    let container: RenderResult | null = null;
    act(() => {
      container = render(
        <Home cartModel={ new CartModel() }
              purchaseApi={ new PurchaseApi() }
              gateway={ new Gateway({ productApi }) }
              categories={ categories }
        />
      );
    });

    const { queryByText, findByText } = container!;
    expect(queryByText('Category 1')).not.toBeNull();
    expect(queryByText('Category 2')).not.toBeNull();
    expect(await findByText('Fake Product 1')).not.toBeNull();
    expect(await findByText('Fake Product 2')).not.toBeNull();
    expect(await findByText('Fake Product 3')).not.toBeNull();
  });
});
