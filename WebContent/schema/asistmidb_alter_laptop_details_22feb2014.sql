alter table laptop_details add laptop_product varchar(50) NOT NULL;
alter table laptop_application_chart_mapping add laptop_product varchar(50) NOT NULL;
alter table laptop_application_chart_mapping add battery_part_no varchar(100) NOT NULL;
alter table laptop_battery_details add battery_part_no varchar(100) NOT NULL;
