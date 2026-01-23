alter table retailer_location_mapping add column bat_brand varchar(250) NOT NULL Default 0;
update retailer_location_mapping set bat_brand="Amaron";
update retailer_location_mapping set bat_brand="Exide" where retailer_id=1272;