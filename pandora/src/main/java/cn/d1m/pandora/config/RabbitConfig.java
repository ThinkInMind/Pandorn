package cn.d1m.pandora.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jone.wang on 2018/9/13.
 * Description:
 */
@Configuration
public class RabbitConfig {

    /**
     * 会自动被{@link org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration}识别
     *
     * @param objectMapper json序列化实现类
     * @return mq 消息序列化工具
     */
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
