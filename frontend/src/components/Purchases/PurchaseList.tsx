import * as React from 'react';
import { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { Purchase } from '../../domain/purchase/Purchase';
import { PurchaseApi } from '../../domain/purchase/PurchaseApi';

interface Props {
  purchaseApi: PurchaseApi,
  updateShowPurchases: Dispatch<SetStateAction<boolean>>,
}

export const PurchaseList: React.FC<Props> = ({ purchaseApi, updateShowPurchases }) => {
  const [purchases, setPurchases] = useState<Purchase[]>([]);

  useEffect(() => {
    purchaseApi.list()
      .then(items => {
        setPurchases(items);
      });
  }, []);

  return (
    <section className="purchase-list">
      <button
        className={ 'close-purchases' }
        onClick={ () => updateShowPurchases(false) }>&raquo;</button>
      <header>
        <h2>{ purchases.length } Purchases</h2>
      </header>
      <div>
        <table>
          <tbody>
          <tr>
            <th>Purchase ID</th>
            <th>Total Cost</th>
            <th>Purchase Status</th>
          </tr>
          {purchases.map(({id, totalCost, purchaseStatus}, i) => (
            <tr key={i}>
              <td>{id}</td>
              <td>{totalCost}</td>
              <td>{purchaseStatus}</td>
            </tr>))}
          </tbody>
        </table>
      </div>
    </section>
  );
};
