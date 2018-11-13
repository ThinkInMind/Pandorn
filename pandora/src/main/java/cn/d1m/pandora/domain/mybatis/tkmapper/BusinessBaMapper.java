package cn.d1m.pandora.domain.mybatis.tkmapper;

import cn.d1m.pandora.domain.dto.BusinessBaDto;
import cn.d1m.pandora.domain.entity.BusinessBa;
import cn.d1m.pandora.domain.mybatis.BaseMapper;

import java.util.Map;

public interface BusinessBaMapper extends BaseMapper<BusinessBa> {

    BusinessBaDto findBusinessBaDtoByParams(Map params);
}