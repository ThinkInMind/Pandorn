package cn.d1m.pandora.controller;

import cn.d1m.pandora.entry.output.CommonResponse;
import cn.d1m.pandora.feign.IntegrationLayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jone.wang on 2018/9/18.
 * Description:
 */
@Profile({"dev", "local"})
@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired //注入一个DiscoveryClient
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IntegrationLayerService integrationLayerService;


    @GetMapping("/rpc")
    public CommonResponse rpcTest() {
        final String health = integrationLayerService.health();
        return CommonResponse.ok().msg(health).build();
    }
}
