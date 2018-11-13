package cn.d1m.pandora.domain.dto;

import cn.d1m.pandora.domain.entity.BusinessBa;
import cn.d1m.pandora.domain.entity.BusinessPosBranch;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by jone.wang on 2018/9/27.
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessBaDto extends BusinessBa {

    private BusinessPosBranch businessPosBranch;
}
