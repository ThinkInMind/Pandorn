package cn.d1m.pandora.domain.entity;

import cn.d1m.pandora.domain.enumeration.ConsumerCardStatus;
import cn.d1m.pandora.domain.mybatis.UUIdGenId;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "`consumer_card`")
@Data
public class ConsumerCard {
    /**
     * 32 UUID
     */
    @Id
    @Column(name = "`id`")
    @KeySql(genId = UUIdGenId.class)
    private String id;

    /**
     * 卡券id
     */
    @Column(name = "`card_id`")
    private String cardId;

    /**
     * openid
     */
    @Column(name = "`openid`")
    private String openid;

    @Column(name = "`created_time`")
    private Timestamp createdTime;

    @Column(name = "`rule_id`")
    private String ruleId;

    /**
     * 状态redeemed,expired,normal
     */
    @Column(name = "`status`")
    private ConsumerCardStatus status;

    @Column(name = "`code`")
    @KeySql(genId = UUIdGenId.class)
    private String code;

    /**
     * 核销柜台账户id
     */
    @Column(name = "`business_pos_branch_id`")
    private String businessPosBranchId;

    /**
     * 消费小票
     */
    @Column(name = "`pos_ticket_no`")
    private String posTicketNo;

    /**
     * 收到卡券时间
     */
    @Column(name = "`receive_time`")
    private Timestamp receiveTime;

    /**
     * 核销时间
     */
    @Column(name = "`redeem_time`")
    private Timestamp redeemTime;

}