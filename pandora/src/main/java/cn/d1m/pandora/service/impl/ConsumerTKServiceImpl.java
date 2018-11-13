package cn.d1m.pandora.service.impl;

import cn.d1m.pandora.domain.entity.Consumer;
import cn.d1m.pandora.domain.mybatis.BaseMapper;
import cn.d1m.pandora.domain.mybatis.tkmapper.ConsumerTKMapper;
import cn.d1m.pandora.service.ConsumerTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jone.wang on 2018/9/21.
 * Description:
 */
@Service
public class ConsumerTKServiceImpl implements ConsumerTKService {

    @Autowired
    private ConsumerTKMapper consumerTKMapper;

    @Override
    public BaseMapper<Consumer> getMapper() {
        return this.consumerTKMapper;
    }
}
