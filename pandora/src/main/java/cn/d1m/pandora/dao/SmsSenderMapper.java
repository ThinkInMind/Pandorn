package cn.d1m.pandora.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import cn.d1m.pandora.entry.SmsSender;

@Repository("smsSenderMapper")
public interface SmsSenderMapper {
	
	public SmsSender findByMobilePhone(String mobile);
	
	public int upateTimesByMobilePhone(SmsSender SmsSender);
	
	public int insertSmsSender(SmsSender SmsSender);
	
}
