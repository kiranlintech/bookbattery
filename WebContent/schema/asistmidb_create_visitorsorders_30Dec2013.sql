--
-- Table structure for table `visitors_orders`
--

CREATE TABLE visitors_orders(
`vis_ord_id` int(10) NOT NULL auto_increment,
`bat_type` varchar(50) NOT NULL,
`veh_make` varchar(50) NOT NULL,
`veh_model` varchar(50) NOT NULL,
`bat_brand` varchar(30) NOT NULL,
`bat_capacity` varchar(15) NOT NULL,
`state` varchar (50) NOT NULL,
`city` varchar (50) NOT NULL,
`area` varchar (50) NOT NULL,
`pincode` varchar(20) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar(20) default '',
 `updated_date` datetime default '0000-00-00 00:00:00',
 `updated_by` varchar(20) default '',
 PRIMARY KEY (`vis_ord_id`));