package cn.d1m.pandora.service.impl;

import cn.d1m.pandora.domain.dto.BusinessBaDto;
import cn.d1m.pandora.domain.entity.BusinessBa;
import cn.d1m.pandora.domain.mybatis.BaseMapper;
import cn.d1m.pandora.domain.mybatis.tkmapper.BusinessBaMapper;
import cn.d1m.pandora.domain.mybatis.tkmapper.BusinessPosBranchTKMapper;
import cn.d1m.pandora.service.BusinessBaService;
import cn.d1m.pandora.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by jone.wang on 2018/9/27.
 * Description:
 */

@Slf4j
@Service
public class BusinessBaServiceImpl implements BusinessBaService {

    @Autowired
    private BusinessBaMapper businessBaMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private BusinessPosBranchTKMapper businessPosBranchTKMapper;

    @Override
    public BaseMapper<BusinessBa> getMapper() {
        return this.businessBaMapper;
    }

    @Override
    public BusinessBaDto findBusinessBaDtoByParams(Map params) {
        return businessBaMapper.findBusinessBaDtoByParams(params);
    }

    @Override
    @Transactional
    public void createBusinessBaWithEmail(String email, String rawPassword, String businessPosBranchId) {
        AssertUtils.notBlank(email, "Email不能为空！");
        AssertUtils.notBlank(rawPassword, "密码不能为空！");
        AssertUtils.notBlank(businessPosBranchId, "加入门店不能为空！");
        final List<BusinessBa> validate = businessBaMapper
                .select(BusinessBa.builder().email(email).build());
        AssertUtils.isTrue(businessPosBranchTKMapper.existsWithPrimaryKey(businessPosBranchId), "门店不存在");
        AssertUtils.isEmpty(validate, "Email已经存在！");
        businessBaMapper.insertSelective(BusinessBa
                .builder()
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .businessPosBranchId(businessPosBranchId)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build());
    }

    @Override
    @Transactional
    public void createBusinessBaWithUsername(String username, String rawPassword, String businessPosBranchId) {
        AssertUtils.notBlank(username, "用户名不能为空！");
        AssertUtils.notBlank(rawPassword, "密码不能为空！");
        AssertUtils.notBlank(businessPosBranchId, "加入门店不能为空！");
        final List<BusinessBa> validate = businessBaMapper
                .select(BusinessBa.builder().email(username).build());
        AssertUtils.isTrue(businessPosBranchTKMapper.existsWithPrimaryKey(businessPosBranchId), "门店不存在");
        AssertUtils.isEmpty(validate, "用户名已经存在！");
        businessBaMapper.insertSelective(BusinessBa
                .builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .businessPosBranchId(businessPosBranchId)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build());
    }

    @Override
    public BusinessBa findByEmailAndRawPassword(String email, String rawPassword) {
        AssertUtils.notBlank(email, "Email不能为空！");
        AssertUtils.notBlank(rawPassword, "密码不能为空！");
        final BusinessBa businessBa = businessBaMapper.
                selectOne(BusinessBa.builder().email(email).build());
        AssertUtils.notNull(businessBa, "找不到用户");
        AssertUtils.isTrue(passwordEncoder.matches(rawPassword, businessBa.getPassword()), "密码不正确");
        return businessBa;
    }


}
