package cn.d1m.pandora.domain.mybatis.tkmapper;

import cn.d1m.pandora.domain.entity.Rule;
import cn.d1m.pandora.domain.mybatis.BaseMapper;

import java.util.List;
import java.util.Map;

public interface RuleTKMapper extends BaseMapper<Rule> {

    List<Rule> findByParams(Map<String, Object> params);

    Rule findOneByParams(Map<String, Object> params);

}