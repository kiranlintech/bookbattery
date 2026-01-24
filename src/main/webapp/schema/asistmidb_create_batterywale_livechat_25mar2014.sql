DROP TABLE IF EXISTS `batterywale_livechat_agentlogin`;
create table  `batterywale_livechat_agentlogin` (
`agent_id` int(10) NOT NULL auto_increment,
`agentfirst_name` varchar(50) default NULL,
`agentlast_name` varchar(50) default NULL,
`agent_loginname` varchar(50) default NULL,
`agent_emailid` varchar(50) default NULL,
`agent_password` varchar(50) default NULL,
`agent_picture_url` varchar(500) default NULL,
`agent_status` varchar(20) default 'inactive',
`agent_chatlimit` int(3) default NULL,
`creation_date` datetime default NULL,
`created_by` varchar(20) NOT NULL,
`agent_logintime` datetime default NULL,
`agent_logouttime` datetime default NULL,
KEY  (`agent_id`),
KEY (`agent_loginname`),
KEY (`agent_status`)
);

DROP TABLE IF EXISTS `batterywale_livechat_userlogin`;
create table  `batterywale_livechat_userlogin` (
`chatuser_id` int(10) NOT NULL auto_increment,
`user_name` varchar(50) default NULL,
`user_emailid` varchar(50) default NULL,
`user_status` varchar(10) default 'active',
`creation_date` datetime default NULL,
`created_by` varchar(20) NOT NULL,
`agent_id` int(10),
`last_modified` datetime default NULL,
KEY  (`chatuser_id`)
);

DROP TABLE IF EXISTS `batterywale_livechat_messages`;
create table  `batterywale_livechat_messages` (
`agent_id` int(10) Default NULL,
`agentfirst_name` varchar(50) default NULL,
`agentlast_name` varchar(50) default NULL,
`agent_loginname` varchar(50) default NULL,
`agent_emailid` varchar(50) default NULL,
`from_name` varchar(50) default NULL,
`to_name` varchar(50) default NULL,
`user_name` varchar(50) default NULL,
`message` text default NULL,
`user_email` varchar(50) default NULL,
`creation_date` datetime default NULL,
`msg_id` int(10) NOT NULL auto_increment,
`from_id` int(10) Default NULL,
`to_id` int(10) Default NULL,
`user_id` int(10) Default NULL,
PRIMARY KEY  (`msg_id`),
KEY  (`agent_id`),
KEY (`user_name`)
);

DROP TABLE IF EXISTS `batterywale_agent_rating`;
create table  `batterywale_agent_rating` (
`agent_id` int(10) default NULL,
`agent_emailid` varchar(50) default NULL,
`user_rating` varchar(20) default NULL,
`user_email` varchar(50) default NULL,
`user_id` int(10) default NULL,
`creation_date` datetime default NULL,
KEY  (`user_rating`)
);