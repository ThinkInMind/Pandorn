package cn.d1m.pandora.domain.entity;

import cn.d1m.pandora.domain.enumeration.CardNature;
import cn.d1m.pandora.domain.enumeration.CardStatus;
import cn.d1m.pandora.domain.enumeration.DiscountType;
import cn.d1m.pandora.domain.enumeration.ValidityPeriodType;
import cn.d1m.pandora.domain.mybatis.UUIdGenId;
import cn.d1m.pandora.domain.mybatis.WithVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.Version;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Table(name = "`card`")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card implements WithVersion {
    /**
     * 32 UUID
     */
    @Id
    @Column(name = "`id`")
    @KeySql(genId = UUIdGenId.class)
    private String id;

    /**
     * 开始时间
     */
    @Column(name = "`begin_time`")
    private Timestamp beginTime;

    /**
     * 结束时间
     */
    @Column(name = "`end_time`")
    private Timestamp endTime;

    /**
     * rule表id
     */
    @Column(name = "`rule_id`")
    private String ruleId;

    /**
     * 描述
     */
    @Column(name = "`description`")
    private String description;

    @Column(name = "`created_time`")
    private Timestamp createdTime;

    /**
     * 优惠券类型码
     */
    @Column(name = "`type_code`")
    private String typeCode;

    /**
     * 礼券名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 礼券标题
     */
    @Column(name = "`title`")
    private String title;

    /**
     * 库存
     */
    @Column(name = "`stock`")
    private Integer stock;

    /**
     * 乐观锁版本号
     */
    @Column(name = "`version`")
    @Version
    private Integer version;

    /**
     * 发放当天开始计算x天后开始
     */
    @Column(name = "`begin_day_after_send`")
    private Integer beginDayAfterSend;

    /**
     * 有效期持续天数
     */
    @Column(name = "`duration_day`")
    private Integer durationDay;

    /**
     * 卡券最低消费
     */
    @Column(name = "`minimum_charge`")
    private BigDecimal minimumCharge;

    /**
     * 现金优惠
     */
    @Column(name = "`cash_discounts`")
    private BigDecimal cashDiscounts;

    /**
     * 折扣率，0-100之间
     */
    @Column(name = "`discount_rate`")
    private Integer discountRate;

    /**
     * 性质；0 自动，1 手动
     */
    @Column(name = "`nature`")
    private CardNature nature;

    /**
     * 优惠形式; 满减券/折扣券/其它
     */
    @Column(name = "`discount_type`")
    private DiscountType discountType;

    /**
     * 有效期计算方法；0 固定区间，1 收到开始计时
     */
    @Column(name = "`validity_period_type`")
    private ValidityPeriodType validityPeriodType;

    /**
     * 过期前提醒，单位天
     */
    @Column(name = "`reminder_before_expiration`")
    private Integer reminderBeforeExpiration;

    /**
     * 张数
     */
    @Column(name = "`quantity`")
    private Integer quantity;


    /**
     * card礼券状态；0 生效中，1 未生效
     */
    @Column(name = "`status`")
    private CardStatus status;

}