import * as React from 'react';
import {render} from '@testing-library/react';
import { Purchases } from './Purchases';
import { PurchaseApi } from '../../domain/purchase/PurchaseApi';
import { act } from 'react-dom/test-utils';

describe('Purchases', () => {
  it('displays PurchasesSummary by default', () => {
    const { getByText } = render(<Purchases purchaseApi={ new PurchaseApi() }/>);
    getByText('History');
  });

  it('shows PurchaseTable when history button is clicked', async () =>{
    const items = [
      {
        id: '1',
        totalCost: 100,
        purchaseStatus: 0
      }
    ];

    const promise = Promise.resolve(items);
    const purchaseApi = new PurchaseApi();
    purchaseApi.list = jest.fn().mockReturnValue(promise);

    const { getByText, getAllByRole, getByRole } = render(<Purchases purchaseApi={ purchaseApi }/>);
    let purchasesButton = getByText('History');
    purchasesButton.click();
    await act(async () => {
      await promise;
    });

    getByRole('columnheader', { name: /purchase id/i });
    getByRole('columnheader', { name: /total cost/i });
    getByRole('columnheader', { name: /purchase status/i });

    expect(getAllByRole('cell', {name: /1/})[0]).toHaveTextContent(/1/)
    expect(getAllByRole('cell', {name: /1/})[0]).toHaveTextContent(/100/)
    expect(getAllByRole('cell', {name: /1/})[0]).toHaveTextContent(/new/)
  })

  it('shows PurchasesList when history button is clicked', async () => {
    const items = [
      {
        id: '1'
      }
    ];
    const promise = Promise.resolve(items);
    const purchaseApi = new PurchaseApi();
    purchaseApi.list = jest.fn().mockReturnValue(promise);

    const { getByText } = render(<Purchases purchaseApi={ purchaseApi }/>);
    let purchasesButton = getByText('History');
    purchasesButton.click();
    await act(async () => {
      await promise;
    });

    getByText(/1 purchases/i);
  });

  it('shows PurchasesSummary when hide button is clicked in PurchasesList', () => {
    const { getByText } = render(<Purchases purchaseApi={ new PurchaseApi() }/>);

    const historyButton = getByText('History');
    historyButton.click();

    const hideButton = getByText('»');
    hideButton.click();

    getByText('History');
  });
});
