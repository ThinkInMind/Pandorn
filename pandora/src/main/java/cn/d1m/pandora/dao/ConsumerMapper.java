package cn.d1m.pandora.dao;

import org.springframework.stereotype.Repository;

import cn.d1m.pandora.entry.Consumer;

@Repository("ConsumerMapper")
public interface ConsumerMapper {
	
	public Consumer findByOpenid(String open_id);
	public Consumer findById(String id);

	public int add(Consumer consumer);

	public int update(Consumer consumer);
	
	public int updateWithOaurh(Consumer consumer);
	public int updateConsumerRegistered(Consumer consumer);
	public int updateConsumerLogin(Consumer consumer);
	
}
