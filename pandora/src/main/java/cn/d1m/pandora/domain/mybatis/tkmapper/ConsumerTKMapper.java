package cn.d1m.pandora.domain.mybatis.tkmapper;

import cn.d1m.pandora.domain.entity.Consumer;
import cn.d1m.pandora.domain.mybatis.BaseMapper;

public interface ConsumerTKMapper extends BaseMapper<Consumer> {

    Consumer findConsumerByCode(String cardId);
}