--
-- Table structure for table `batteryoperator`
--

CREATE TABLE batteryoperator(
`opt_id` int (10) NOT NULL auto_increment,
`operator_loginname` varchar (30) NOT NULL,
`operator_passwd` varchar (30) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar (20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar (20) default '',
 PRIMARY KEY (`opt_id`),
 KEY `admin_loginname` (`operator_loginname`),
 KEY `admin_passwd` (`operator_passwd`));



 insert into batteryoperator(opt_id,operator_loginname,operator_passwd,creation_date) values (NULL,'operator','operator',now());