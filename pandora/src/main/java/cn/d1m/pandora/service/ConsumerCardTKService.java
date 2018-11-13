package cn.d1m.pandora.service;

import cn.d1m.pandora.domain.dto.ConsumerCardDto;
import cn.d1m.pandora.domain.entity.ConsumerCard;
import cn.d1m.pandora.domain.web.BaseResponse;
import cn.d1m.pandora.entry.output.CommonResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by jone.wang on 2018/9/21.
 * Description:
 */
public interface ConsumerCardTKService extends BaseService<ConsumerCard> {
    List<ConsumerCardDto> findCardDtoByParams(Map<String, Object> params);



}
