create table schedule_history(
id	Varchar(32) COMMENT	'UUID 32位',
successful	Varchar(8) COMMENT	'是否成功',
fail	Varchar(8) COMMENT	'是否失败',
begin_time	timestamp COMMENT	'开始时间' DEFAULT now(),
end_time	timestamp COMMENT	'结束时间' DEFAULT now(),
api_error_log_id	Varchar(32) COMMENT	'Api_error_log表id uuid 32位',
created_time	timestamp	DEFAULT now()
);
