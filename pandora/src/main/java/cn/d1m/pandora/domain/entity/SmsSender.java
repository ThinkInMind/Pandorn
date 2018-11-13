package cn.d1m.pandora.domain.entity;

import cn.d1m.pandora.domain.mybatis.UUIdGenId;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "`sms_sender`")
@Data
public class SmsSender {
    /**
     * 32 UUID
     */
    @Id
    @Column(name = "`id`")
    @KeySql(genId = UUIdGenId.class)
    private String id;

    /**
     * 验证码
     */
    @Column(name = "`verification_code`")
    private String verificationCode;

    /**
     * 手机号
     */
    @Column(name = "`mobile`")
    private String mobile;

    /**
     * 失效时间
     */
    @Column(name = "`expire_time`")
    private Timestamp expireTime;

    /**
     * 尝试次数
     */
    @Column(name = "`times`")
    private String times;

    @Column(name = "`created_time`")
    private Timestamp createdTime;

}