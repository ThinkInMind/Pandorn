package cn.d1m.pandora.amqp.rabbit.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MessageTemplateHandler {
	
	private final static Logger log = LoggerFactory.getLogger(MessageTemplateHandler.class);
	int count = 0;
	
//	private MessageTemplateService messageTemplateService;
	
//	public MessageTemplateHandler(MessageTemplateService messageTemplateService) {
//		this.messageTemplateService = messageTemplateService;
//	}
	
	public void handleMessage(String json) {
		try {
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
