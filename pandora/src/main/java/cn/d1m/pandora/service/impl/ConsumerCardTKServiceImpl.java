package cn.d1m.pandora.service.impl;

import cn.d1m.pandora.domain.dto.ConsumerCardDto;
import cn.d1m.pandora.domain.entity.Consumer;
import cn.d1m.pandora.domain.entity.ConsumerCard;
import cn.d1m.pandora.domain.mybatis.BaseMapper;
import cn.d1m.pandora.domain.mybatis.tkmapper.ConsumerCardTKMapper;
import cn.d1m.pandora.domain.mybatis.tkmapper.ConsumerTKMapper;
import cn.d1m.pandora.domain.web.BaseResponse;
import cn.d1m.pandora.service.ConsumerCardTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * Created by jone.wang on 2018/9/21.
 * Description:
 */
@Service
public class ConsumerCardTKServiceImpl implements ConsumerCardTKService {

    @Autowired
    private ConsumerCardTKMapper consumerCardTKMapper;


    @Override
    public BaseMapper<ConsumerCard> getMapper() {
        return this.consumerCardTKMapper;
    }

    public List<ConsumerCardDto> findCardDtoByParams(Map<String, Object> params) {
        return consumerCardTKMapper.findCardDtoByParams(params);
    }




}
