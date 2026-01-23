CREATE TABLE adminaccess(
`accs_admin_id` int (10) NOT NULL auto_increment,
`accs_admin_passwd` varchar (30) NOT NULL,
`creation_date` datetime default '0000-00-00 00:00:00',
`created_by` varchar (20) default '',
`updated_date` datetime default '0000-00-00 00:00:00',
`updated_by` varchar (20) default '',
 PRIMARY KEY (`accs_admin_id`),
 KEY `admin_passwd` (`accs_admin_passwd`));

 insert into adminaccess(accs_admin_id,accs_admin_passwd,creation_date)values(NULL,'MP2780D',now());

