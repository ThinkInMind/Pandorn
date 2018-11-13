package cn.d1m.pandora.config;

import com.xxl.job.core.executor.XxlJobExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jone.wang on 2018/10/23.
 * Description:
 */
@Configuration
public class XxlJobConfig {

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.executor.appName}")
    private String appName;

    @Value("${xxl.job.executor.port}")
    private Integer port;

//    @Value("${xxl.job.accessToken}")
//    private String accessToken;

    @Value("${xxl.job.executor.logPath}")
    private String logPath;

//    @Value("${xxl.job.executor.logRetentionDays}")
//    private Integer logRetentionDays;

//    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobExecutor xxlJobExecutor() {
        final XxlJobExecutor xxlJobExecutor = new XxlJobExecutor();
        xxlJobExecutor.setAdminAddresses(adminAddresses);
        xxlJobExecutor.setAppName(appName);
        xxlJobExecutor.setPort(port);
//        xxlJobExecutor.setAccessToken(accessToken);
        xxlJobExecutor.setLogPath(logPath);
//        xxlJobExecutor.setLogRetentionDays(logRetentionDays);
        return xxlJobExecutor;
    }
}
