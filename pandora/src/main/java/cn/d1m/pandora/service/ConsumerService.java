package cn.d1m.pandora.service;

import cn.d1m.pandora.entry.Consumer;
import cn.d1m.pandora.entry.output.CommonResponse;

public interface ConsumerService {

    CommonResponse findByOpenid(String open_id, String mobile_phone);

    CommonResponse add(Consumer consumer);


    Consumer findByOpenid(String open_id);

    CommonResponse queryConsumer(Consumer consumer);

    CommonResponse registAdd(Consumer consumer);

    int updateConsumerRegistered(Consumer consumer);


    CommonResponse beforeLoginBound(Consumer queryConsumer);

    CommonResponse login(Consumer consumer, String loginId, String password, String token);

    CommonResponse loginUpd(Consumer consumer, String token);


    CommonResponse updateConsumer(Consumer consumer);

    int updateConsumerLogin(Consumer consumer);

    CommonResponse getConsumer(String token);

    Consumer getloyaltyPoints(String token);

    CommonResponse getloyaltyPointsHis(String token);

    CommonResponse verify(String token);

}
