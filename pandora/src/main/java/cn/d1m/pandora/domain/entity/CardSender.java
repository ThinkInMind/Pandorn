package cn.d1m.pandora.domain.entity;

import cn.d1m.pandora.domain.mybatis.UUIdGenId;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Table(name = "`card_sender`")
@Data
public class CardSender {
    /**
     * 32 UUID
     */
    @Id
    @Column(name = "`id`")
    @KeySql(genId = UUIdGenId.class)
    private String id;

    /**
     * Schedule_history_id uuid 32位
     */
    @Column(name = "`schedule_history_id`")
    private String scheduleHistoryId;

    /**
     * 卡券id
     */
    @Column(name = "`rule_id`")
    private String ruleId;

    /**
     * 已经发送
     */
    @Column(name = "`sended_quality`")
    private Integer sendedQuality;

    /**
     * Split by ,
     */
    @Column(name = "`tags`")
    private String tags;

    /**
     * system,user
     */
    @Column(name = "`send_by`")
    private String sendBy;

    /**
     * 创建时间
     */
    @Column(name = "`send_time`")
    private Date sendTime;

    @Column(name = "`created_time`")
    private Timestamp createdTime;

}