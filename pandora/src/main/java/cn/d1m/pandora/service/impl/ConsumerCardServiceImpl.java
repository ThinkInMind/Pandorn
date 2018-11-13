package cn.d1m.pandora.service.impl;

import cn.d1m.pandora.dao.ConsumerCardMapper;
import cn.d1m.pandora.dao.ConsumerMapper;
import cn.d1m.pandora.entry.Consumer;
import cn.d1m.pandora.entry.ConsumerCard;
import cn.d1m.pandora.entry.ConsumerCardCount;
import cn.d1m.pandora.entry.Rule;
import cn.d1m.pandora.entry.output.CommonResponse;
import cn.d1m.pandora.service.ConsumerCardService;
import cn.d1m.pandora.utils.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ConsumerCardService")
public class ConsumerCardServiceImpl implements ConsumerCardService {

    @Autowired
    private ConsumerCardMapper consumerCardMapper;

    @Autowired
    private ConsumerMapper consumerMapper;

    @Override
    public CommonResponse findvalidCardByToken(String token ) {
        Consumer consumer = consumerMapper.findById(token);
        if (consumer == null) {
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "no consumer");
        }
        List<ConsumerCardCount> count = consumerCardMapper.getCardCount(consumer.getOpenid());
        List<ConsumerCard> rlt = consumerCardMapper.findvalidCardByOpenid(consumer.getOpenid());
        if (rlt != null && rlt.size() > 0) {
            count.get(0).setList(rlt);
        }
        return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS, count);
    }







    @Override
    public CommonResponse findInvalidCardByToken(String token) {
        Consumer consumer = consumerMapper.findById(token);
        if (consumer == null) {
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "no consumer");
        }
        List<ConsumerCard> rlt = consumerCardMapper.findInvalidCardByOpenid(consumer.getOpenid());
        List<ConsumerCardCount> count = consumerCardMapper.getCardCount(consumer.getOpenid());
        if (rlt != null && rlt.size() > 0) {
            count.get(1).setList(rlt);
        }
        return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS, count);
    }

    @Override
    public CommonResponse selectListCoupon(Rule cardRule) {
//		Consumer consumer =  consumerMapper.findById(token);
//		if(consumer == null ) {
//			return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL,"no consumer");
//		}
//		List<ConsumerCard>  rlt=  consumerCardMapper.findInvalidCardByOpenid(consumer.getOpenid());
//		List<ConsumerCardCount> count = consumerCardMapper.getCardCount(consumer.getOpenid());
//		if(rlt != null && rlt.size() > 0) {
//			count.shortUUID(1).setList(rlt);
//		}
        List<Rule> list = null;
        return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS, list);
    }

    @Override
    public CommonResponse redeemedCoupon(ConsumerCard consumerCard) {
        consumerCard.setStatus("redeemed");
        int count = consumerCardMapper.redeemedCoupon(consumerCard);
        if (count > 0) {
            return CommonResponse.ok().status(CommonConstants.HTTP_STATUS_CODE_SUCCESS).build();
        } else {
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "coupon is disabled !");
        }

    }

    @Override
    public CommonResponse selectlistConsumerCoupon(ConsumerCard cardRule) {
        // TODO Auto-generated method stub


        return null;
    }

}
