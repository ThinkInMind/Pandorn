package cn.d1m.pandora.domain.mybatis.tkmapper;

import cn.d1m.pandora.domain.dto.ConsumerCardDto;
import cn.d1m.pandora.domain.entity.ConsumerCard;
import cn.d1m.pandora.domain.mybatis.BaseMapper;

import java.util.List;
import java.util.Map;

public interface ConsumerCardTKMapper extends BaseMapper<ConsumerCard> {

    List<ConsumerCardDto> findCardDtoByParams(Map<String, Object> params);

}