import * as React from 'react';
import { useEffect, useState } from 'react';
import { Product } from '../domain/product/Product';
import { ProductCard } from '../components/ProductCard';
import { ProductFilters } from '../components/ProductFilters';
import { Catalog } from '../domain/catalog/Catalog';
import { Gateway } from '../domain/Gateway';
import './Home.css';
import { ShoppingCart } from '../components/ShoppingCart';
import { CartModel } from '../domain/cart/CartModel';
import { getDefaultCatalog } from '../domain/catalog/CatalogApi';
import { useCartModel } from '../domain/cart/useCartModel';
import { Purchases } from '../components/Purchases/Purchases';
import { PurchaseApi } from '../domain/purchase/PurchaseApi';

interface Props {
  gateway: Gateway,
  categories: Catalog[],
  cartModel: CartModel,
  purchaseApi: PurchaseApi,
}

export const Home: React.FC<Props> = ({categories, gateway, cartModel, purchaseApi}) => {
  const {productApi} = gateway;
  const catalog = getDefaultCatalog(categories);
  const [activeCategory, setActiveCategory] = useState<string>(catalog);
  const [currentProducts, setCurrentProducts] = useState<Product[]>([]);

  useEffect(() => {
    productApi.getProducts(activeCategory)
      .then(newProducts => {
        setCurrentProducts(newProducts);
      });
  }, [productApi, activeCategory, cartModel]);

  useCartModel(cartModel);

  return (
    <main>
      <div className="header">
        <Purchases purchaseApi={purchaseApi}/>
        <div/>
        <ShoppingCart cartModel={cartModel}/>
      </div>

      <header>
        <h1 className={"title"}>Monomart</h1>
      </header>

      {categories.length > 0 && (
        <ProductFilters
          filterCategories={categories}
          activeCategory={activeCategory}
          setActiveFilterCategory={setActiveCategory}
        />
      )}

      <section className="product-catalog">
        {currentProducts.map((product: Product, i) =>
          <ProductCard
            addToCart={(p) => {
              cartModel.addItem(p);
            }}
            key={i}
            product={product}/>
        )}
      </section>
    </main>
  );
};

