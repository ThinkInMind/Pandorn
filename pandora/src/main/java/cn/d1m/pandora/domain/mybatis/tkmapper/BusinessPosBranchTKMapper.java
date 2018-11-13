package cn.d1m.pandora.domain.mybatis.tkmapper;

import cn.d1m.pandora.domain.entity.BusinessPosBranch;
import cn.d1m.pandora.domain.mybatis.BaseMapper;

public interface BusinessPosBranchTKMapper extends BaseMapper<BusinessPosBranch> {

    @Override
    BusinessPosBranch selectByPrimaryKey(Object key);
}