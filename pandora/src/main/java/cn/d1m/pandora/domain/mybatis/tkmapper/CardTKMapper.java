package cn.d1m.pandora.domain.mybatis.tkmapper;

import cn.d1m.pandora.domain.dto.CardDto;
import cn.d1m.pandora.domain.entity.Card;
import cn.d1m.pandora.domain.mybatis.BaseMapper;

import java.util.List;
import java.util.Map;

public interface CardTKMapper extends BaseMapper<Card> {

    @Override
    Card selectByPrimaryKey(Object id);

    List<CardDto> findCardDtoByParams(Map<String, Object> params);

   List<CardDto>  selectListCard(Card card);
}