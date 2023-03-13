create table Entrance
(
    subscription_id int,
    enter           bool,
    time            timestamp(3) with time zone,
    primary key (subscription_id, enter, time)
);

create index subscription_index on Entrance using hash (time);
create unique index time_index on Entrance using btree (time);