package cn.d1m.pandora.domain.mybatis;

import tk.mybatis.mapper.genid.GenId;

import java.util.UUID;

/**
 * Created by jone.wang on 2018/9/21.
 * Description:
 */
public class UUIdGenId implements GenId<String> {
    @Override
    public String genId(String table, String column) {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
