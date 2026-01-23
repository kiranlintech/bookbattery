alter table battery_order_details add column cancelled_message varchar(500) NOT NULL Default 0;
alter table inverter_order_details add column cancelled_message varchar(500) NOT NULL Default 0;
