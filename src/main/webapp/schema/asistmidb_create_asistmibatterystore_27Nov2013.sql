  --
-- Table structure for table `battery_health_check`
--


CREATE TABLE battery_health_check(
`bat_chk_id` int (10) NOT NULL auto_increment,
`bat_ins_month` varchar(3) NOT NULL,
`bat_ins_year` varchar(50) NOT NULL,
`veh_type` varchar(50) NOT NULL,
`veh_model` varchar(50) NOT NULL,
`bat_brand` varchar(50) NOT NULL,
`bat_model` varchar(50) NOT NULL,
`customer_name` varchar(50) NOT NULL,
`customer_mobilnumber` varchar(20) NOT NULL,
`support_name` varchar(50) NOT NULL,
`support_mobilnumber` varchar(20) NOT NULL,
`city` varchar (30) NOT NULL,
`area` varchar(50) NOT NULL,
`pincode` varchar(20) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar(20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar(20) default '',
 PRIMARY KEY (`bat_chk_id`));

 --
-- Table structure for table `battery_registration`
--


CREATE TABLE battery_registration(
`bat_reg_id` int (10) NOT NULL auto_increment,
`bat_ins_month` varchar(3) NOT NULL,
`bat_ins_year` varchar(50) NOT NULL,
`bat_type` varchar(50) NOT NULL,
`bat_brand` varchar(50) NOT NULL,
`bat_model` varchar(50) NOT NULL,
`veh_type` varchar(50) NOT NULL,
`veh_model` varchar(50) NOT NULL,
`customer_name` varchar(50) NOT NULL,
`customer_mobilnumber` varchar(20) NOT NULL,
`city` varchar (30) NOT NULL,
`area` varchar(50) NOT NULL,
`pincode` varchar(20) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar(20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar(20) default '',
 PRIMARY KEY (`bat_reg_id`));