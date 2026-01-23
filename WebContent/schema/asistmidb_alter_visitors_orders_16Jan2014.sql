alter table visitors_orders add operator_flag varchar(5) default 'no';
alter table battery_order_details modify order_status varchar(50) default 'confirmed';