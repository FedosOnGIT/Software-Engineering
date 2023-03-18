create table Clients
(
    id    serial primary key,
    name  varchar(100) not null,
    phone varchar(16) unique
);

create index phone_index on Clients using hash (phone);