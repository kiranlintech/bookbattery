alter table battery_service add agent_name varchar(50) NOT NULL default '0';
alter table battery_service add order_status varchar(250) NOT NULL default '0';
alter table battery_service add order_reasons varchar(250) NOT NULL default '0';
alter table battery_service add order_agent_comments varchar(250) NOT NULL default '0';
alter table battery_service add postponed_date datetime default '0000-00-00 00:00:00';
create index mobile_number on visitors_orders(mobile_number);
create index agent_name on visitors_orders(agent_name);