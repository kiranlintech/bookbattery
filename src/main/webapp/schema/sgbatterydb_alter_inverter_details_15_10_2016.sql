ALTER TABLE inverter_details ADD inverter_battery_supported_ah varchar(50);
ALTER TABLE inverter_details ADD inverter_battery_pcs varchar(10);

UPDATE inverter_details SET inverter_battery_supported_ah ="100 Ah", inverter_battery_pcs = '1' WHERE inverter_capacity ='400 VA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '1' WHERE inverter_capacity ='650 VA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '1' WHERE inverter_capacity ='675 VA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '1' WHERE inverter_capacity ='700 VA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '1' WHERE inverter_capacity ='825 VA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '1' WHERE inverter_capacity ='850 VA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '1' WHERE inverter_capacity ='880 VA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '1' WHERE inverter_capacity ='900 VA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '2' WHERE inverter_capacity ='1250 VA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '2' WHERE inverter_capacity ='1400 VA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '2' WHERE inverter_capacity ='1450 VA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '2' WHERE inverter_capacity ='1500 VA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '2' WHERE inverter_capacity ='1650 VA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '2' WHERE inverter_capacity ='1700 VA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '3' WHERE inverter_capacity ='2 KVA';
UPDATE inverter_details SET inverter_battery_supported_ah ="150 Ah", inverter_battery_pcs = '4' WHERE inverter_capacity ='3.5 KVA';