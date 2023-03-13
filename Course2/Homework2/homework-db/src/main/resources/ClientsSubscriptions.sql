create table Clients_Subscriptions
(
    client_id       int,
    subscription_id int,
    primary key (client_id, subscription_id),
    foreign key (client_id) references Client (id),
    foreign key (subscription_id) references Subscription (id)
);

create index client_id_index on Clients_Subscriptions using hash (client_id);
create index subscription_id_index on Clients_Subscriptions using hash (subscription_id);

