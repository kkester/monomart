import * as React from 'react';
import { useState } from 'react';
import './Purchases.css';
import { PurchasesSummary } from './PurchasesSummary';
import { PurchaseList } from './PurchaseList';
import { PurchaseApi } from '../../domain/purchase/PurchaseApi';

interface Props {
  purchaseApi: PurchaseApi,
}

export const Purchases: React.FC<Props> = ({purchaseApi}) => {
  const [showPurchases, updateShowPurchases] = useState<boolean>(false);

  return (
    <section className='purchases'>
      { !showPurchases ?
        <PurchasesSummary updateShowPurchases={ updateShowPurchases }/>
        :
        <PurchaseList purchaseApi={purchaseApi} updateShowPurchases={updateShowPurchases}/>
      }
    </section>
  );
};
