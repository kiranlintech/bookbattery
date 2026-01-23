--
-- Table structure for table `laptop_battery_details`
--
CREATE TABLE laptop_battery_details(
`battery_id` int(10) NOT NULL auto_increment,
`battery_brand` varchar(100) NOT NULL,
`battery_model` varchar(100) NOT NULL,
`battery_warranty` varchar(100) NOT NULL,
`battery_cellcount` varchar(50) NOT NULL,
`voltage` varchar(50) NOT NULL,
`watt_hr` varchar(50) NOT NULL,
`battery_actual_price` integer(20) NOT NULL,
`description` text,
`amazonlink` text,
`icon_name` varchar(50) NOT NULL,
`icon_url` varchar(225) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar(20) default '',
 `updated_date` datetime default '0000-00-00 00:00:00',
 `updated_by` varchar(20) default '',
 PRIMARY KEY (`battery_id`),
  KEY `bat_brand` (`battery_brand`),
  KEY `bat_model` (`battery_model`));



--
-- Table structure for table `laptop_details`
--
 CREATE TABLE laptop_details(
`laptop_id` int(10) NOT NULL auto_increment,
`batterytype_id` int(10) NOT NULL,
`battery_type` varchar(50) NOT NULL,
`laptop_name` varchar(50) NOT NULL,
`laptop_model` varchar(50) NOT NULL,
`description` text,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar(20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar(20) default '',
 PRIMARY KEY (`laptop_id`),
 KEY `vehicle_name` (`laptop_name`),
 KEY `battery_type` (`battery_type`));


--
-- Table structure for table `laptop_application_chart_mapping`
--

 CREATE TABLE laptop_application_chart_mapping(
`map_id` int (10) NOT NULL auto_increment,
`batterytype_id` int (10) NOT NULL,
`laptop_id` int (10) NOT NULL,
`battery_id` int (10) NOT NULL,
`battery_brand` varchar(100) NOT NULL,
`battery_model` varchar(10) NOT NULL,
`laptop_name` varchar(50) NOT NULL,
`laptop_model` varchar(50) NOT NULL,
`battery_type` varchar(50) NOT NULL,
`battery_warranty` varchar(100) NOT NULL,
`battery_cellcount` varchar(50) NOT NULL,
`voltage` varchar(50) NOT NULL,
`watt_hr` varchar(50) NOT NULL,
`battery_actual_price` int (20) NOT NULL,
`icon_url` varchar(150) NOT NULL,
`description` text,
`amazonlink` text,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar(20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar(20) default '',
 PRIMARY KEY (`map_id`),
 KEY `battery_type` (`battery_type`),
 KEY `battery_brand` (`battery_brand`),
 KEY `laptop_name` (`laptop_name`),
 KEY `laptop_model` (`laptop_model`));

