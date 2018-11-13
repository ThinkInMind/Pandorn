package cn.d1m.pandora.domain.dto;

import cn.d1m.pandora.domain.entity.Card;
import cn.d1m.pandora.domain.entity.Consumer;
import cn.d1m.pandora.domain.entity.ConsumerCard;
import cn.d1m.pandora.domain.entity.Rule;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by jone.wang on 2018/10/8.
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConsumerCardDto extends ConsumerCard {
    private Card card;
    private Rule rule;
    private Consumer consumer;
}
