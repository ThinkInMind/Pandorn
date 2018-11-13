package cn.d1m.pandora.service;

import cn.d1m.pandora.domain.dto.CardDto;
import cn.d1m.pandora.domain.entity.Card;

import java.util.Collection;
import java.util.List;

/**
 * Created by jone.wang on 2018/9/21.
 * Description:
 */
public interface CardTKService extends BaseService<Card> {

    void addCardAndLevels(Card card, Collection<Integer> levels);

    //根据ID删除接口
     void deleteStockById(Card card);
      //根据Card条件查询对应的礼券信息
    List<CardDto> selectListCard(Card card);
}
