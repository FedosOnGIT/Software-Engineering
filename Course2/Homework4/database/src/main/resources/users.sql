create table users
(
    id                 serial primary key,
    name               varchar(100),
    preferred_currency varchar(10) check ( preferred_currency in ('DOLLAR', 'EURO', 'RUBLES') )
);