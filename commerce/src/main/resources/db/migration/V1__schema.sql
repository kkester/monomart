create table cart_items (id uuid not null, quantity integer, product_id uuid not null, primary key (id));
create table purchases (id uuid not null, primary key (id));
create table purchases_items (purchase_id uuid not null, items_id uuid not null);
alter table purchases_items add constraint UK_am09jj6mg8aj3wm5ac71mwih6 unique (items_id);
alter table purchases_items add constraint FKqltptd8d2mimamx2ovydwfdw8 foreign key (purchase_id) references purchases;