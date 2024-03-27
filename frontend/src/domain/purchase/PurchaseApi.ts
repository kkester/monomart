import { Purchase } from './Purchase';

export class PurchaseApi {
  list(): Promise<Purchase[]> {
    return fetch('/api/purchases')
      .then(response => response.json());
  }
}
