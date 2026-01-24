alter table visitors_orders add visitor_status varchar(250) NOT NULL Default 'Not Called';
alter table visitors_orders add visitor_reasons varchar(250) NOT NULL Default 0;
alter table visitors_orders add agent_name varchar(50) NOT NULL Default 0;
alter table visitors_orders add postponed_date date NOT NULL Default 0;
CREATE INDEX visitor_status ON visitors_orders (visitor_status);
CREATE INDEX creation_date ON visitors_orders (creation_date);
CREATE INDEX postponed_date ON visitors_orders (postponed_date);
CREATE INDEX option_type ON visitors_orders (option_type);
CREATE INDEX operator_flag ON visitors_orders (operator_flag);


