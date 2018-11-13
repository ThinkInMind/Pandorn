package cn.d1m.pandora.service.impl;

import cn.d1m.pandora.domain.entity.SmsSender;
import cn.d1m.pandora.domain.mybatis.BaseMapper;
import cn.d1m.pandora.domain.mybatis.tkmapper.SmsSenderTKMapper;
import cn.d1m.pandora.service.SmsSenderTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jone.wang on 2018/9/21.
 * Description:
 */
@Service
public class SmsSenderTKServiceImpl implements SmsSenderTKService {

    @Autowired
    private SmsSenderTKMapper smsSenderTKMapper;

    @Override
    public BaseMapper<SmsSender> getMapper() {
        return this.smsSenderTKMapper;
    }
}
