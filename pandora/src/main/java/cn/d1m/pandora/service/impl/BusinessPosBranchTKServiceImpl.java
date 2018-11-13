package cn.d1m.pandora.service.impl;

import cn.d1m.pandora.domain.entity.BusinessPosBranch;
import cn.d1m.pandora.domain.mybatis.BaseMapper;
import cn.d1m.pandora.domain.mybatis.tkmapper.BusinessPosBranchTKMapper;
import cn.d1m.pandora.service.BusinessPosBranchTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jone.wang on 2018/9/21.
 * Description:
 */
@Service
public class BusinessPosBranchTKServiceImpl implements BusinessPosBranchTKService {

    @Autowired
    private BusinessPosBranchTKMapper businessPosBranchTKMapper;

    @Override
    public BaseMapper<BusinessPosBranch> getMapper() {
        return this.businessPosBranchTKMapper;
    }
}
