alter table battery_order_details add order_reasons varchar(250) NOT NULL Default 0;
alter table battery_order_details add agent_name varchar(50) NOT NULL Default 0;
alter table battery_order_details add order_agent_comments text;
alter table battery_order_details add installed_date datetime NOT NULL Default 0;
alter table battery_order_details drop column postpone_message;
alter table battery_order_details drop column cancelled_message;
alter table battery_order_details drop column cancelled_regenerated_message;
alter table battery_order_details drop column outofstock_message;
alter table battery_order_details drop column notresponded_message;
alter table battery_order_details drop column offbusiness_message;
alter table battery_order_details drop column franchiseedenied_message;
CREATE INDEX order_status ON battery_order_details (order_status);
CREATE INDEX postpone_date ON battery_order_details (postpone_date);
CREATE INDEX installed_date ON battery_order_details (installed_date);


alter table inverter_order_details add order_reasons varchar(250) NOT NULL Default 0;
alter table inverter_order_details add agent_name varchar(50) NOT NULL Default 0;
alter table inverter_order_details add order_agent_comments text;
alter table inverter_order_details add installed_date datetime NOT NULL Default 0;
alter table inverter_order_details drop column postpone_message;
alter table inverter_order_details drop column cancelled_message;
alter table inverter_order_details drop column cancelled_regenerated_message;
alter table inverter_order_details drop column outofstock_message;
alter table inverter_order_details drop column notresponded_message;
alter table inverter_order_details drop column offbusiness_message;
alter table inverter_order_details drop column franchiseedenied_message;
CREATE INDEX order_status ON inverter_order_details (order_status);
CREATE INDEX postpone_date ON inverter_order_details (postpone_date);
CREATE INDEX installed_date ON inverter_order_details (installed_date);


