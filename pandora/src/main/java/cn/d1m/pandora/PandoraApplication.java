package cn.d1m.pandora;

import org.apache.commons.io.FileUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 指定扫描包可以增加启动速度
 */
@EnableDiscoveryClient
@RestController
@SpringBootApplication(scanBasePackages = {
        "cn.d1m.pandora.amqp",
        "cn.d1m.pandora.config",
        "cn.d1m.pandora.controller",
        "cn.d1m.pandora.redis",
        "cn.d1m.pandora.service"
})
@MapperScan(basePackages = "cn.d1m.pandora.dao")
@EnableFeignClients(basePackages = "cn.d1m.pandora.feign")
@tk.mybatis.spring.annotation.MapperScan(basePackages = "cn.d1m.pandora.domain.mybatis.tkmapper")
public class PandoraApplication {

    public static void main(String[] args) throws IOException {
        final InputStream inputStream = PandoraApplication.class
                .getClassLoader()
                .getResourceAsStream("log4j2.xml");
        final File file = new File("logs/log4j2.xml");
        FileUtils.copyInputStreamToFile(inputStream, file);
        SpringApplication.run(PandoraApplication.class, args);
    }

}
