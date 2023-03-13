create table Client
(
    id    serial primary key,
    name  varchar(100) not null,
    phone varchar(16) unique
);

create index phone_index on Client using hash (phone);