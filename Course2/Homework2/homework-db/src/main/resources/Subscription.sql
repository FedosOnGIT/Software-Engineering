create table Subscriptions
(
    id    serial primary key,
    until timestamp(3) with time zone
);

create index until_index on Subscriptions using btree (until);