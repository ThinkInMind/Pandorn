package cn.d1m.pandora.domain.wechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by jone.wang on 2018/9/18.
 * Description:
 */
@Data
public class MiniAppSession {
    private String openid;
    @JsonProperty("session_key")
    private String sessionKey;
    private String id;
}
