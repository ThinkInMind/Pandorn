create table card_sender (
id	Varchar(32) COMMENT	'32 UUID',
schedule_history_id	Varchar(32) COMMENT	'Schedule_history_id uuid 32位',
rule_id	Varchar(32) COMMENT	'卡券id',
sended_quality	int(5) COMMENT	'已经发送',
tags	Varchar(64) COMMENT	'Split by ,',
send_by	varchar(32) COMMENT	'system,user',
send_time	timestamp COMMENT	'创建时间' DEFAULT now(),
created_time	timestamp DEFAULT now()

);