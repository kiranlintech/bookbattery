--
-- Table structure for table `batterywale_user_profile`
--

DROP TABLE IF EXISTS `batterywale_user_profile`;
CREATE TABLE `batterywale_user_profile` (
  `user_id` int(20) NOT NULL auto_increment,
  `email_id` varchar(50) NOT NULL default '',
  `mobile_number` varchar(15) NOT NULL default '',
  `password` varchar(20) default '',
  `name` varchar(100) NOT NULL default '',
  `address` varchar(255) default '',
  `address1` varchar(255) default '',
  `zipcode` varchar(15) default NULL,
  `city` varchar(50) default '',
  `state` varchar(50) default '',
  `mobile_verify_code` varchar(15) default '',
  `creation_date` datetime default '0000-00-00 00:00:00',
  `created_by` varchar(20) default '',
  `updated_date` datetime default '0000-00-00 00:00:00',
  `updated_by` varchar(20) default '',
  PRIMARY KEY  (`user_id`),
  KEY `umaster_email_id` (`email_id`),
  KEY `umaster_password` (`password`));


 insert into batterywale_user_profile (email_id,mobile_number,password,name,address,address1,zipcode,city,state,mobile_verify_code,creation_date) select email_id,mobile_number,password,first_name,address,address1,zipcode,city,state,mobile_verify_code,creation_date from user_profile;
 delete FROM batterywale_user_profile WHERE email_id NOT IN (select consumer_emailid from battery_order_details);
 delete  from leads where email_id NOT IN (select email_id from user_profile);