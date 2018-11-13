package cn.d1m.pandora;

import cn.d1m.pandora.domain.entity.ApiErrorLog;
import cn.d1m.pandora.domain.mybatis.tkmapper.ApiErrorLogTKMapper;
import cn.d1m.pandora.service.BusinessBaService;
import cn.d1m.pandora.utils.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Created by jone.wang on 2018/9/21.
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
//@Slf4j
public class PandoraTest {

    @Autowired
    private ApiErrorLogTKMapper apiErrorLogTKMapper;

    @Autowired
    private BusinessBaService businessBaService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void apiErrorLogTKMapperTest() {
        final ApiErrorLog log = ApiErrorLog.builder()
                .api("/login")
                .createdTime(Timestamp.valueOf(LocalDateTime.now()))
                .header("content-type:json")
                .method("post")
                .parameters("{}")
                .url("/hello")
                .build();
        apiErrorLogTKMapper.insert(log);
        Assert.notNull(log.getId(), "must has id");
    }

    @Test
    @Rollback(value = false)
    public void createBusinessBaTest() {

        businessBaService.createBusinessBaWithEmail("ipm001@pandora.net", "123456", "1");

    }


    @Test
    public void setTokenTest(){
        stringRedisTemplate.opsForValue().set(CommonConstants.TOKEN_PRE +"tokenTest","fddssdfsdfsdfdsfgdfsgdfhg",10, TimeUnit.DAYS);
    }


}
