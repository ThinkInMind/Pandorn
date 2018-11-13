package cn.d1m.pandora.service.impl;

import cn.d1m.pandora.domain.entity.CardSender;
import cn.d1m.pandora.domain.mybatis.BaseMapper;
import cn.d1m.pandora.domain.mybatis.tkmapper.CardSenderMapper;
import cn.d1m.pandora.service.CardSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jone.wang on 2018/9/21.
 * Description:
 */
@Service
public class CardSenderServiceImpl implements CardSenderService {

    @Autowired
    private CardSenderMapper cardSenderMapper;

    @Override
    public BaseMapper<CardSender> getMapper() {
        return this.cardSenderMapper;
    }
}
