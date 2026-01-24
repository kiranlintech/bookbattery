--
-- Table structure for table `percentage`
--

 CREATE TABLE percentage(
`percent_id` int (10) NOT NULL auto_increment,
`city` varchar (50) NOT NULL,
`city_percentage` varchar (15) NOT NULL,
`company_percentage` varchar (15) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar (20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar (20) default '',
 PRIMARY KEY (`percent_id`),
 KEY `city` (`city`));


 --
-- Table structure for table `retailer_invoice`
--

 CREATE TABLE retailer_invoice(
`ret_inv_id` int (10) NOT NULL auto_increment,
`invoice_number` varchar (25) NOT NULL,
`retailer_name` varchar(50) NOT NULL,
`from_date` datetime default '0000-00-00 00:00:00',
`to_date` datetime default '0000-00-00 00:00:00',
`ret_pdf_url` varchar (200) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar (20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar (20) default '',
 PRIMARY KEY (`ret_inv_id`),
 KEY `retailer_name` (`retailer_name`));


 --
-- Table structure for table `customer_percentage`
--

 CREATE TABLE customer_percentage(
`cus_percent_id` int (10) NOT NULL auto_increment,
`city` varchar (50) NOT NULL,
`city_percentage` varchar (15) NOT NULL,
`city_local_percentage` varchar (15) default '0.00',
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar (20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar (20) default '',
 PRIMARY KEY (`cus_percent_id`),
 KEY `city` (`city`));