DROP TABLE IF EXISTS `batterywale_livechat_busymessages`;
create table  `batterywale_livechat_busymessages` (
`user_id` int(10) default NULL,
`user_emailid` varchar(50) default NULL,
`user_name` varchar(50) default NULL,
`user_message` text default NULL,
`agent_message` text default NULL,
`creation_date` datetime default NULL,
`status` varchar(10) default 'new',
KEY  (`status`)
);
