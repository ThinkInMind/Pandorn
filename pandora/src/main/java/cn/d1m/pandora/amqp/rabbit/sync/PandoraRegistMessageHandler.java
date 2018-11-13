package cn.d1m.pandora.amqp.rabbit.sync;

import cn.d1m.pandora.dao.ConsumerCardMapper;
import cn.d1m.pandora.dao.RuleMapper;
import cn.d1m.pandora.entry.Consumer;
import cn.d1m.pandora.entry.ConsumerCard;
import cn.d1m.pandora.entry.Rule;
import cn.d1m.pandora.service.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Component
@Slf4j
public class PandoraRegistMessageHandler {

    @Autowired
    private ConsumerCardMapper consumerCardMapper;
    @Autowired
    private RuleMapper ruleMapper;

    @Autowired
    public RuleService ruleService;

    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = "${pandora.consumer.crmid.queue}")
    public String handleMessage(Consumer consumer) {
        List<Rule> list = ruleService.selectRegistRuleList();
        if (CollectionUtils.isEmpty(list)) {
            return "";
        } else {
            for (Rule aList : list) {
                send(aList, consumer);
            }
        }
        return "";
    }

    /**
     * 单个规则给用户发券
     *
     * @param rule     规则
     * @param consumer 用户信息
     */
    private void send(Rule rule, Consumer consumer) {
        //计算发券的张数
        for (int i = 0; i < rule.getQuantity(); i++) {
            //发送单张券
            sendSignleCard(rule, consumer);
        }
    }

    /**
     * 单个规则给用户发单个券
     *
     * @param rule
     * @param consumer
     */
    private void sendSignleCard(Rule rule, Consumer consumer) {
        ConsumerCard consumerCard = new ConsumerCard();
        //计算 券的有效期
        getCardBeginEndTime(rule, consumerCard);
        //应用等级
        if (!checkLoyaltyTier(rule, consumer.getLoyaltyTier())) {
            return;
        }
        //库存计算
        if (!checkQuality(rule)) {
            return;
        }
        consumerCardMapper.insertCard(consumerCard);
        consumerCardMapper.insertConsumerCard(consumerCard);
        consumerCardMapper.updateQuality(consumerCard);
    }

    /**
     * 检验库存是否充足
     *
     * @param rule
     * @return
     */
    private boolean checkQuality(Rule rule) {
        if (rule.getQuality() > 0) {
            return ruleMapper.getQuality(rule) > 0;
        }
        return false;
    }

    /**
     * 计算优惠券有效期
     *
     * @param rule
     * @param consumerCard
     */
    private void getCardBeginEndTime(Rule rule, ConsumerCard consumerCard) {
        if (rule.getBeginTimestamp() != null && rule.getEndTimestamp() != null) {
            consumerCard.setBeginTime(rule.getBeginTimestamp());
            consumerCard.setEndTime(rule.getEndTimestamp());
        } else {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_YEAR, Integer.parseInt(rule.getSendedBeginTimestamp()));
            consumerCard.setBeginTime(new Timestamp(c.getTime().getTime()));
            c.add(Calendar.DAY_OF_YEAR, Integer.parseInt(rule.getSendedEndTimestamp()) - Integer.parseInt(rule.getSendedBeginTimestamp()));
            consumerCard.setEndTime(new Timestamp(c.getTime().getTime()));
        }

    }

    private boolean checkLoyaltyTier(Rule rule, String loyalty_tier) {
        switch (loyalty_tier) {
            case "vip0":
                if (!"true".equals(rule.getVip0())) {
                    return false;
                }
                break;
            case "vip1":
                if (!"true".equals(rule.getVip1())) {
                    return false;
                }
                break;
            case "vip2":
                if (!"true".equals(rule.getVip2())) {
                    return false;
                }
                break;
            case "vip3":
                if (!"true".equals(rule.getVip3())) {
                    return false;
                }
                break;
            case "vip4":
                if (!"true".equals(rule.getVip4())) {
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }
}
