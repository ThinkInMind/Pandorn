package cn.d1m.pandora.service.impl;

import cn.d1m.pandora.domain.entity.ApiErrorLog;
import cn.d1m.pandora.domain.mybatis.BaseMapper;
import cn.d1m.pandora.domain.mybatis.tkmapper.ApiErrorLogTKMapper;
import cn.d1m.pandora.service.ApiErrorLogTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jone.wang on 2018/9/21.
 * Description:
 */
@Service
public class ApiErrorLogTKServiceImpl implements ApiErrorLogTKService {

    @Autowired
    private ApiErrorLogTKMapper apiErrorLogTKMapper;

    @Override
    public BaseMapper<ApiErrorLog> getMapper() {
        return this.apiErrorLogTKMapper;
    }
}
