--
-- Table structure for table `inverter_details`
--
CREATE TABLE inverter_details(
`inverter_id` int(10) NOT NULL auto_increment,
`inverter_brand` varchar(100) NOT NULL,
`inverter_name` varchar(100) NOT NULL,
`inverter_model` varchar(100) NOT NULL,
`inverter_warranty` varchar(50) NOT NULL,
`inverter_capacity` varchar(50) NOT NULL,
`computer` varchar(15) NOT NULL,
`tubelights` varchar(15) NOT NULL,
`fans` varchar(15) NOT NULL,
`television` varchar(15) NOT NULL,
`description` text,
`icon_name` varchar(50) NOT NULL,
`icon_url` varchar(500) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar(20) default '',
 `updated_date` datetime default '0000-00-00 00:00:00',
 `updated_by` varchar(20) default '',
 PRIMARY KEY (`inverter_id`),
  KEY `inverter_brand` (`inverter_brand`),
  KEY `inverter_model` (`inverter_model`));


  --
-- Table structure for table `inverter_price_details`
--

CREATE TABLE inverter_price_details(
`inverter_price_id` int (10) NOT NULL auto_increment,
`inverter_brand` varchar(50) NOT NULL,
`inverter_capacity` varchar(50) NOT NULL,
`state` varchar(50) NOT NULL,
`city` varchar(50) NOT NULL,
`inverter_actual_price` integer(20) NOT NULL,
`inverter_discount_price` integer(20) NOT NULL,
`inverter_eretailer_price` integer(20) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar (20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar (20) default '',
 PRIMARY KEY (`inverter_price_id`),
 KEY `inverter_brand` (`inverter_brand`),
 KEY `state` (`state`));

  --
-- Table structure for table `battery_order_details`
--


CREATE TABLE inverter_order_details(
`order_id` int (10) NOT NULL auto_increment,
`order_number` varchar(15) NOT NULL,
`consumer_name` varchar(50) NOT NULL,
`consumer_mobnumber` varchar(20) NOT NULL,
`consumer_emailid` varchar(50) NOT NULL,
`consumer_verify_code` int(10) NOT NULL,
`retailer_name` varchar(50) NOT NULL,
`retailer_mobile_number` varchar(20) NOT NULL,
`retailer_emailid` varchar(50) NOT NULL,
`state` varchar (30) NOT NULL,
`city` varchar (30) NOT NULL,
`inverter_brand` varchar(50) NOT NULL,
`inverter_model` varchar(50) NOT NULL,
`inverter_capacity` varchar(50) NOT NULL,
`price` varchar (25) NOT NULL,
`area` varchar(50) NOT NULL,
`pincode` varchar(20) NOT NULL,
`order_status` varchar(50) default  'confirmed',
`operator` varchar(50) default  'no',
`postpone_date` date default '0000-00-00',
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar(20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar(20) default '',
 PRIMARY KEY (`order_id`));