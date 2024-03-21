create table catalogs
(
    id           varchar(255) not null,
    display_name varchar(255),
    primary key (id)
);

create table products
(
    id          uuid not null,
    description varchar(255),
    image_alt   varchar(255),
    image_src   varchar(255),
    name        varchar(255),
    price       varchar(255),
    catalog_id  varchar(255),
    quantity    integer,
    primary key (id)
);
alter table products
    add constraint FKr9g72vsfwc9lupwutnut4w7kf foreign key (catalog_id) references catalogs;

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