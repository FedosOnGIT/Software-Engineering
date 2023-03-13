create table Subscription
(
    id    serial primary key,
    until timestamp(3) with time zone
);

create index until_index on Subscription using btree (until);