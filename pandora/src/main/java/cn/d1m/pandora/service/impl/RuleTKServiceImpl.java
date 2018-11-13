package cn.d1m.pandora.service.impl;

import cn.d1m.pandora.domain.entity.Rule;
import cn.d1m.pandora.domain.mybatis.BaseMapper;
import cn.d1m.pandora.domain.mybatis.tkmapper.RuleTKMapper;
import cn.d1m.pandora.service.RuleTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jone.wang on 2018/9/21.
 * Description:
 */
@Service
public class RuleTKServiceImpl implements RuleTKService {

    @Autowired
    private RuleTKMapper ruleTKMapper;

    @Override
    public BaseMapper<Rule> getMapper() {
        return this.ruleTKMapper;
    }
}
