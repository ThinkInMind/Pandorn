package cn.d1m.pandora.service;

import cn.d1m.pandora.entry.Member;

public interface MemberService {

	public Member findByOpenid(String open_id);
}
