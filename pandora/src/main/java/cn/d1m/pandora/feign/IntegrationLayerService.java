package cn.d1m.pandora.feign;

import cn.d1m.pandora.entry.Consumer;
import cn.d1m.pandora.entry.output.AccountsSearchResponse;
import cn.d1m.pandora.entry.output.scv.ConsumerFindResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static cn.d1m.pandora.utils.URLConstants.*;

/**
 * Created by jone.wang on 2018/9/19.
 * Description:
 */
@FeignClient(value = "integration-layer", decode404 = true)
public interface IntegrationLayerService {

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    String health();

    @RequestMapping(value = CONSUMER_FIND_BY_CONTRY_LAYER, method = RequestMethod.POST)
    ConsumerFindResponse consumerFindByContryLayer(@RequestBody Map consumer);

    @RequestMapping(value = CONSUMER_FIND_LAYER, method = RequestMethod.POST)
    ConsumerFindResponse consumerFindLayer(@RequestBody Map consumer);

    @RequestMapping(value = CONSUMER_UPDATE_LAYER, method = RequestMethod.POST)
    ConsumerFindResponse consumerUpdateLayer(@RequestBody Consumer consumer);

    @RequestMapping(value = CONSUMER_CREATE_LAYER, method = RequestMethod.POST)
    ConsumerFindResponse consumerCreateLayer(@RequestBody Consumer consumer);

    @RequestMapping(value = ACCTOUNTS_REGISTER_LAYER, method = RequestMethod.POST)
    ConsumerFindResponse accountsRegisterLayer(@RequestBody Consumer consumer);

    @RequestMapping(value = ACCTOUNTS_SEARCH_lAYER, method = RequestMethod.GET)
    AccountsSearchResponse accountsSearchLayer(@RequestParam("mobilePhone") String mobilePhone,
                                               @RequestParam(name = "emailAddress", required = false) String emailAddress);

    @RequestMapping(value = ACCTOUNTS_LOGIN_lAYER, method = RequestMethod.POST)
    AccountsSearchResponse accountsLoginLayer(@RequestBody Map consumer);


}
