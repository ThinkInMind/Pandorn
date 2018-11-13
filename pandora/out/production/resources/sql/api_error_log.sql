create table api_error_log (
id	Varchar(32) COMMENT	'32 UUID',
api	Varchar(64)	COMMENT	'/api/v1/xx',
url	Varchar(128)	COMMENT	'链接url',
prameter	Varchar(32)	COMMENT	'参数',
header	Varchar(1024)	COMMENT	'ContentType: application/json, ContentType: form-data',
method	Varchar(4)	COMMENT	'GET,POST',
created_time	timestamp


);