package cn.d1m.pandora.service;

import cn.d1m.pandora.entry.ConsumerCard;
import cn.d1m.pandora.entry.Rule;
import cn.d1m.pandora.entry.output.CommonResponse;

public interface ConsumerCardService {
	
	public CommonResponse findInvalidCardByToken(String token);
	public CommonResponse findvalidCardByToken(String openId );
	public CommonResponse redeemedCoupon(ConsumerCard consumerCard);
	public CommonResponse selectListCoupon(Rule cardRule);
	public CommonResponse selectlistConsumerCoupon(ConsumerCard cardRule);
}
