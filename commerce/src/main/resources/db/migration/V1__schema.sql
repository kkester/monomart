create table cart_items
(
    id         uuid not null,
    quantity   integer,
    product_id uuid not null,
    primary key (id)
);
create table purchases
(
    id uuid not null,
    primary key (id)
);

create table purchased_items
(
    id          uuid not null,
    purchase_id uuid not null,
    product_id  uuid not null,
    quantity    integer,
    primary key (id)
);

alter table purchased_items
    add constraint FKqltptd8d2mimamx2ovydwfdw8 foreign key (purchase_id) references purchases;