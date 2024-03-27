import * as React from 'react';
import { Dispatch, SetStateAction } from 'react';

interface Props {
  updateShowPurchases: Dispatch<SetStateAction<boolean>>;
}

export const PurchasesSummary: React.FC<Props> = ({ updateShowPurchases }) => (
  <div className="summary">
    <button
      className={ 'show-purchases' }
      onClick={ () => updateShowPurchases(true) }>
      <span role="img" aria-label="show purchase history">History</span>
    </button>
  </div>
);
