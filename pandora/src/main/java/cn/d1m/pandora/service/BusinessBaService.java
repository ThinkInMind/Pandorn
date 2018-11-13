package cn.d1m.pandora.service;

import cn.d1m.pandora.domain.dto.BusinessBaDto;
import cn.d1m.pandora.domain.entity.BusinessBa;

import java.util.Map;

/**
 * Created by jone.wang on 2018/9/27.
 * Description:
 */
public interface BusinessBaService extends BaseService<BusinessBa> {
    BusinessBaDto findBusinessBaDtoByParams(Map params);

    void createBusinessBaWithEmail(String email, String rawPassword, String businessPosBranchId);

    void createBusinessBaWithUsername(String username, String rawPassword, String businessPosBranchId);

    BusinessBa findByEmailAndRawPassword(String email, String rawPassword);
}
