package cn.d1m.pandora.service.impl;

import cn.d1m.pandora.domain.dto.CardDto;
import cn.d1m.pandora.domain.entity.Card;
import cn.d1m.pandora.domain.entity.CardConsumerLevel;
import cn.d1m.pandora.domain.mybatis.BaseMapper;
import cn.d1m.pandora.domain.mybatis.tkmapper.CardConsumerLevelMapper;
import cn.d1m.pandora.domain.mybatis.tkmapper.CardTKMapper;
import cn.d1m.pandora.service.CardTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jone.wang on 2018/9/21.
 * Description:
 */
@Service
public class CardTKServiceImpl implements CardTKService {

    @Autowired
    private CardTKMapper cardTKMapper;

    @Autowired
    private CardConsumerLevelMapper cardConsumerLevelMapper;

    @Override
    public BaseMapper<Card> getMapper() {
        return this.cardTKMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCardAndLevels(Card card, Collection<Integer> levelIds) {
        cardTKMapper.insertSelective(card);
        if (!CollectionUtils.isEmpty(levelIds)) {
            final List<CardConsumerLevel> levels = levelIds.stream()
                            .map(l -> CardConsumerLevel
                                    .builder()
                            .cardId(card.getId())
                            .consumerLevelId(l)
                            .build())
                    .collect(Collectors.toList());
            cardConsumerLevelMapper.insertList(levels);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStockById(Card card) {

        //根据card删除礼券
        cardTKMapper.delete(card);

    }

    @Override
    public List<CardDto> selectListCard(Card card) {
        return  cardTKMapper.selectListCard(card);
    }
}
