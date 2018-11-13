package cn.d1m.pandora.domain.mybatis.tkmapper;

import cn.d1m.pandora.domain.entity.ConsumerLevel;
import cn.d1m.pandora.domain.mybatis.BaseMapper;

public interface ConsumerLevelMapper extends BaseMapper<ConsumerLevel> {
    ConsumerLevel findByCardId(String cardId);
}