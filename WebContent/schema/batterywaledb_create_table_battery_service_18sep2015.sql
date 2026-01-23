
--
-- Table structure for table battery_service
--

CREATE TABLE battery_service (
  `bat_service_id` int(10) NOT NULL auto_increment,
  `bat_ins_month` varchar(3) NOT NULL,
  `bat_ins_year` varchar(50) NOT NULL,
  `veh_type` varchar(50) NOT NULL,
  `veh_model` varchar(50) NOT NULL,
  `bat_brand` varchar(50) NOT NULL,
  `bat_model` varchar(50) NOT NULL,
  `customer_name` varchar(50) NOT NULL,
  `customer_mobilnumber` varchar(20) NOT NULL,
  `support_name` varchar(50) NOT NULL,
  `support_mobilnumber` varchar(55) NOT NULL,
  `city` varchar(30) NOT NULL,
  `area` varchar(50) NOT NULL,
  `pincode` varchar(20) NOT NULL,
  `creation_date` datetime default '0000-00-00 00:00:00',
  `created_by` varchar(20) default '',
  `updated_date` datetime default '0000-00-00 00:00:00',
  `updated_by` varchar(20) default '',
  `bat_type` varchar(50) NOT NULL,
  `service_need_date` date NOT NULL default '0000-00-00',
  PRIMARY KEY  (`bat_service_id`));

--
-- Table structure for table service_engineer_detls
--


  CREATE TABLE service_engineer_detls (
  `service_id` int(10) NOT NULL auto_increment,
  `state` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `area` varchar(50) NOT NULL,
  `engineer_name` varchar(75) NOT NULL,
  `engineer_mobile_number` varchar(15) NOT NULL,
  `engineer_email_id` varchar(75) NOT NULL,
  `pincode` varchar(20) default NULL,
  `creation_date` datetime default '0000-00-00 00:00:00',
  `created_by` varchar(20) default '',
  `updated_date` datetime default '0000-00-00 00:00:00',
  `updated_by` varchar(20) default '',
  PRIMARY KEY  (`service_id`),
  KEY `city` (`city`),
  KEY `area` (`area`),
  KEY `state` (`state`),
  KEY `engineer_name` (`engineer_name`),
  KEY `pinnumber` (`pincode`));