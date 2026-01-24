
--
-- Table structure for table `battery_details`
--

CREATE TABLE battery_details(
`bat_id` int(10) NOT NULL auto_increment,
`bat_name` varchar(50) NOT NULL,
`bat_model` varchar(50) NOT NULL,
`bat_brand` varchar(50) NOT NULL,
`bat_warranty` varchar(100) NOT NULL,
`bat_capacity` varchar(50) NOT NULL,
`bat_act_price` integer(20) NOT NULL,
`bat_witbat_price` integer(20) NOT NULL,
`description` text,
`icon_name` varchar(50) NOT NULL,
`icon_url` varchar(150) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar(20) default '',
 `updated_date` datetime default '0000-00-00 00:00:00',
 `updated_by` varchar(20) default '',
 PRIMARY KEY (`bat_id`),
  KEY `bat_brand` (`bat_brand`),
  KEY `bat_model` (`bat_model`));


--
-- Table structure for table `vehicle_details`
--

CREATE TABLE vehicle_details(
`veh_id` int(10) NOT NULL auto_increment,
`battype_id` int(10) NOT NULL,
`battery_type` varchar(50) NOT NULL,
`vehicle_name` varchar(50) NOT NULL,
`vehicle_model` varchar(50) NOT NULL,
`description` text,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar(20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar(20) default '',
 PRIMARY KEY (`veh_id`),
 KEY `vehicle_name` (`vehicle_name`),
 KEY `battery_type` (`battery_type`));

--
-- Table structure for table `application_chat_mapping`
--

CREATE TABLE application_chat_mapping(
`map_id` int (10) NOT NULL auto_increment,
`battype_id` int (10) NOT NULL,
`veh_id` int (10) NOT NULL,
`bat_id` int (10) NOT NULL,
`bat_brand` varchar(50) NOT NULL,
`bat_model` varchar(50) NOT NULL,
`bat_name` varchar(50) NOT NULL,
`veh_name` varchar(50) NOT NULL,
`veh_model` varchar(50) NOT NULL,
`bat_type` varchar(50) NOT NULL,
`bat_capacity` varchar(50) NOT NULL,
`bat_warranty` varchar(100) NOT NULL,
`bat_act_price` int (20) NOT NULL,
`bat_witbat_price` int (20) NOT NULL,
`icon_url` varchar(150) NOT NULL,
`description` text,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar(20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar(20) default '',
 PRIMARY KEY (`map_id`),
 KEY `bat_type` (`bat_type`),
 KEY `bat_capacity` (`bat_capacity`),
 KEY `bat_brand` (`bat_brand`),
 KEY `veh_name` (`veh_name`),
 KEY `veh_model` (`veh_model`));

--
-- Table structure for table `batteryadmin`
--

CREATE TABLE batteryadmin(
`admin_id` int (10) NOT NULL auto_increment,
`admin_loginname` varchar (30) NOT NULL,
`admin_passwd` varchar (30) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar (20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar (20) default '',
 PRIMARY KEY (`admin_id`),
 KEY `admin_loginname` (`admin_loginname`),
 KEY `admin_passwd` (`admin_passwd`));

--
-- Table structure for table `location_area`
--

 CREATE TABLE location_area(
`loc_area_id` int (10) NOT NULL auto_increment,
`location` varchar (50) NOT NULL,
`area` varchar (50) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar (20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar (20) default '',
 PRIMARY KEY (`loc_area_id`),
 KEY `location` (`location`),
 KEY `area` (`area`));


--
-- Table structure for table `batteryprice`
--

CREATE TABLE batteryprice(
`batprice_id` int (10) NOT NULL auto_increment,
`bat_brand` varchar(50) NOT NULL,
`bat_model` varchar(50) NOT NULL,
`state` varchar(50) NOT NULL,
`bat_act_price` varchar (25) NOT NULL,
`bat_witbat_price` varchar (25) NOT NULL,
`bat_ret_price` varchar (25) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar (20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar (20) default '',
 PRIMARY KEY (`batprice_id`),
 KEY `bat_name` (`bat_brand`),
 KEY `bat_model` (`bat_model`),
 KEY `state` (`state`));

 --
-- Table structure for table `pincode`
--

CREATE TABLE pincode(
`pin_id` int (10) NOT NULL auto_increment,
`pinnumber` varchar(20) NOT NULL,
`state` varchar(50) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar (20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar (20) default '',
 PRIMARY KEY (`pin_id`),
 KEY `pinnumber` (`pinnumber`),
 KEY `state` (`state`));


 --
-- Table structure for table `retailer_location_mapping`
--

CREATE TABLE retailer_location_mapping(
`ret_loc_id` int (10) NOT NULL auto_increment,
`state` varchar(50) NOT NULL,
`city` varchar(50) NOT NULL,
`area` varchar(50) NOT NULL,
`retailer_id` int (10) NOT NULL,
`retailer_name` varchar (75) NOT NULL,
`pincode` varchar(20) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar (20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar (20) default '',
 PRIMARY KEY (`ret_loc_id`),
 KEY `city` (`city`),
 KEY `area` (`area`),
 KEY `state` (`state`),
 KEY `retailer_id` (`retailer_id`),
 KEY `retailer_name` (`retailer_name`),
 KEY `pinnumber` (`pinnumber`));


 --
-- Table structure for table `battery_order_details`
--


CREATE TABLE battery_order_details(
`ord_id` int (10) NOT NULL auto_increment,
`order_number` varchar(15) NOT NULL,
`consumer_name` varchar(50) NOT NULL,
`consumer_mobnumber` varchar(20) NOT NULL,
`consumer_emailid` varchar(50) NOT NULL,
`consumer_verif_code` int(10) NOT NULL,
`retailer_name` varchar(50) NOT NULL,
`retailer_mobilnumber` varchar(20) NOT NULL,
`retailer_emailis` varchar(50) NOT NULL,
`city` varchar (30) NOT NULL,
`battery_brand` varchar(50) NOT NULL,
`battery_model` varchar(50) NOT NULL,
`price` varchar (25) NOT NULL,
`area` varchar(50) NOT NULL,
`pincode` varchar(20) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar(20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar(20) default '',
 PRIMARY KEY (`ord_id`));

insert into batteryadmin(admin_id,admin_loginname,admin_passwd,creation_date) values (NULL,'admin','batteryadmin',now());

insert into pincode(pin_id,pinnumber,state,creation_date)values(NULL,11,'Delhi',now()),(NULL,12,'Haryana',now()),(NULL,13,'Haryana',now()),(NULL,14,'Punjab',now()),(NULL,15,'Punjab',now()),(NULL,16,'Punjab',now()),(NULL,17,'Himachal Pradesh',now()),(NULL,18,'Jammu and Kashmir',now()),(NULL,19,'Jammu and Kashmir',now()),(NULL,20,'Uttar Pradesh',now()),(NULL,21,'Uttar Pradesh',now()),(NULL,22,'Uttar Pradesh',now()),
(NULL,23,'Uttar Pradesh',now()),(NULL,24,'Uttar Pradesh',now()),(NULL,25,'Uttar Pradesh',now()),(NULL,26,'Uttar Pradesh',now()),(NULL,27,'Uttar Pradesh',now()),(NULL,28,'Uttar Pradesh',now()),(NULL,30,'Rajasthan',now()),(NULL,31,'Rajasthan',now()),(NULL,32,'Rajasthan',now()),(NULL,33,'Rajasthan',now()),(NULL,34,'Rajasthan',now()),(NULL,36,'Gujarat',now()),(NULL,37,'Gujarat',now()),(NULL,38,'Gujarat',now()),
(NULL,39,'Gujarat',now()),(NULL,40,'Maharashtra',now()),(NULL,41,'Maharashtra',now()),(NULL,42,'Maharashtra',now()),(NULL,43,'Maharashtra',now()),(NULL,44,'Maharashtra',now()),(NULL,45,'Madhya Pradesh',now()),(NULL,46,'Madhya Pradesh',now()),(NULL,47,'Madhya Pradesh',now()),(NULL,48,'Madhya Pradesh',now()),(NULL,49,'Chhattisgarh',now()),(NULL,50,'Andhra Pradesh',now()),(NULL,51,'Andhra Pradesh',now()),(NULL,52,'Andhra Pradesh',now()),
(NULL,53,'Andhra Pradesh',now()),(NULL,56,'Karnataka',now()),(NULL,57,'Karnataka',now()),(NULL,58,'Karnataka',now()),(NULL,59,'Karnataka',now()),(NULL,60,'Tamil Nadu',now()),(NULL,61,'Tamil Nadu',now()),(NULL,62,'Tamil Nadu',now()),(NULL,63,'Tamil Nadu',now()),(NULL,64,'Tamil Nadu',now()),(NULL,67,'Kerala',now()),(NULL,68,'Kerala',now()),(NULL,69,'Kerala',now()),(NULL,70,'West Bengal',now()),(NULL,71,'West Bengal',now()),(NULL,72,'West Bengal',now()),
(NULL,73,'West Bengal',now()),(NULL,74,'West Bengal',now()),(NULL,75,'Orissa',now()),(NULL,76,'Orissa',now()),(NULL,77,'Orissa',now()),(NULL,78,'Assam',now()),(NULL,79,'Arunachal Pradesh',now()),(NULL,80,'Bihar',now()),(NULL,81,'Bihar',now()),(NULL,82,'Bihar',now()),(NULL,84,'Bihar',now()),(NULL,85,'Bihar',now()),(NULL,83,'Jharkhand',now()),(NULL,92,'Jharkhand',now());

