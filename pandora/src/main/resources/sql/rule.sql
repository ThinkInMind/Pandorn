create table rule (
id	Varchar(32) COMMENT	'32 UUID',
cardId		Varchar(32) COMMENT 'card表id',
rule	Varchar(16) COMMENT	'应用规则',
name	Varchar(32)	COMMENT '名称',
type	Varchar(32)	COMMENT '类型',
beginTimestamp	timestamp COMMENT	'固定时间起'  DEFAULT now(),
endTimestamp	timestamp COMMENT	'固定时间止'  DEFAULT now(),
sendedBeginTimestamp	Varchar(3) COMMENT '发放后时间起',
sendedEndTimestamp	Varchar(3) COMMENT	'发放后时间止',
title	Varchar(64) COMMENT	'标题',
property	Varchar(8) COMMENT	'性质',
cycle	Varchar(2)	COMMENT '0表示单次 周期',
status	Varchar(8) COMMENT	'状态',
quality	int(5)	COMMENT '-1 表示无限',
vip1	Varchar(5)	COMMENT 'true/false',
vip2	Varchar(5) COMMENT	'true/false',
vip3	Varchar(5) COMMENT	'true/false',
vip4	Varchar(5) COMMENT	'true/false',
description	Varchar(256) COMMENT	'描述',
isdelete	Varchar(8) COMMENT	'是否删除',
created_time	timestamp COMMENT	'创建时间' DEFAULT now(),
created_by	Varchar(32) COMMENT	'创建人',
last_updated_time	timestamp COMMENT	'最后更新时间'  DEFAULT now(),
last_updated_by	Varchar(32) COMMENT	'最后更新人'
);