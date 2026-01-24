alter table battery_order_details add pdfurl varchar(150) NOT NULL;
alter table battery_order_details add order_status varchar(20) default 'confirmed';