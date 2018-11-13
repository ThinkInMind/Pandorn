create table consumer_card (
id	Varchar(32) COMMENT	'32 UUID',
begin	timestamp	COMMENT	'开始时间' DEFAULT now(),
end	timestamp	COMMENT	'结束时间' DEFAULT now(),
rule_id	Varchar(32)	COMMENT	'卡券id',
openid	varchar(28)	COMMENT	'openid',
status	Varchar(8)	COMMENT	'状态redeemed,expired,normal',
receive_time	timestamp	COMMENT	'接收时间' DEFAULT now(),
redeem_time	timestamp	COMMENT	'兑换时间' DEFAULT now(),
business_pos_branch_id	Varchar(32)	COMMENT	'门店表id',
created_time	timestamp	 DEFAULT now()


);