package cn.d1m.pandora.domain.dto;

import cn.d1m.pandora.domain.entity.Card;
import cn.d1m.pandora.domain.entity.ConsumerLevel;
import cn.d1m.pandora.domain.entity.Rule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by jone.wang on 2018/10/22.
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CardDto extends Card {
    private Rule rule;
    private List<ConsumerLevel> levels;
}
