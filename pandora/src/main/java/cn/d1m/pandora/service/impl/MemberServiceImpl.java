package cn.d1m.pandora.service.impl;

import org.springframework.stereotype.Service;

import cn.d1m.pandora.dao.MemberMapper;
import cn.d1m.pandora.entry.Member;
import cn.d1m.pandora.service.MemberService;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	
	private MemberMapper memberMapper;

	@Override
	public Member findByOpenid(String open_id) {
		return memberMapper.findByOpenid(open_id);
	}

}
