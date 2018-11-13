package cn.d1m.pandora.dao;

import org.springframework.stereotype.Repository;

import cn.d1m.pandora.entry.Member;

@Repository("memberMapper")
public interface MemberMapper {
	
	public Member findByOpenid(String open_id);
}
