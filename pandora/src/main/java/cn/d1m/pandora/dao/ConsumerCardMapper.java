package cn.d1m.pandora.dao;

import cn.d1m.pandora.entry.ConsumerCard;
import cn.d1m.pandora.entry.ConsumerCardCount;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("consumerCardMapper")
public interface ConsumerCardMapper {
    List<ConsumerCard> findvalidCardByOpenid(String openid);

    List<ConsumerCard> findInvalidCardByOpenid(String openid);

    List<ConsumerCardCount> getCardCount(String openid);

    int redeemedCoupon(ConsumerCard consumerCard);

    int insertCard(ConsumerCard consumerCard);

    int insertConsumerCard(ConsumerCard consumerCard);

    void updateQuality(ConsumerCard consumerCard);
}
