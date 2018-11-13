package cn.d1m.pandora.domain.entity;

import cn.d1m.pandora.domain.mybatis.UUIdGenId;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "`consumer`")
@Data
public class Consumer {
    /**
     * 32 UUID
     */
    @Id
    @Column(name = "`id`")
    @KeySql(genId = UUIdGenId.class)
    private String id;

    /**
     * 名
     */
    @Column(name = "`first_name`")
    private String firstName;

    /**
     * 姓
     */
    @Column(name = "`last_name`")
    private String lastName;

    /**
     * 邮箱
     */
    @Column(name = "`email_address`")
    private String emailAddress;

    /**
     * 手机
     */
    @Column(name = "`mobile_phone`")
    private String mobilePhone;

    /**
     * 区号
     */
    @Column(name = "`mobile_area_code`")
    private String mobileAreaCode;

    /**
     * 2018-06-27
     */
    @Column(name = "`birthday_day`")
    private String birthdayDay;

    /**
     * 月
     */
    @Column(name = "`birthday_month`")
    private String birthdayMonth;

    /**
     * 年
     */
    @Column(name = "`birthday_year`")
    private String birthdayYear;

    /**
     * 称谓
     */
    @Column(name = "`title_salutation`")
    private String titleSalutation;

    /**
     * 性别
     */
    @Column(name = "`gender`")
    private String gender;

    /**
     * Vip编号
     */
    @Column(name = "`vip_number`")
    private String vipNumber;

    /**
     * 积分
     */
    @Column(name = "`loyalty_points`")
    private String loyaltyPoints;

    /**
     * 等级
     */
    @Column(name = "`loyalty_tier`")
    private String loyaltyTier;

    /**
     * 2018-06-29
     */
    @Column(name = "`loyalty_points_expiry_date`")
    private String loyaltyPointsExpiryDate;

    /**
     * 下一个等级
     */
    @Column(name = "`next_loyalty_tier`")
    private String nextLoyaltyTier;

    /**
     * 下一等级需要的积分
     */
    @Column(name = "`points_to_next_tier`")
    private String pointsToNextTier;

    /**
     * 前一个等级
     */
    @Column(name = "`previous_loyalty_tier`")
    private String previousLoyaltyTier;

    /**
     * 最后生日礼券兑换时间
     */
    @Column(name = "`last_birthday_discount_redemption_date`")
    private String lastBirthdayDiscountRedemptionDate;

    /**
     * 微信openid
     */
    @Column(name = "`openid`")
    private String openid;

    /**
     * 微信unionid
     */
    @Column(name = "`unionid`")
    private String unionid;

    /**
     * 绑定状态：pending,registered,bound
     */
    @Column(name = "`status`")
    private String status;

    /**
     * scv scrmid
     */
    @Column(name = "`crmid`")
    private String crmid;

    /**
     * weibo/wechat/wechat ad/baidu/brandsite/other
     */
    @Column(name = "`channel`")
    private String channel;

    /**
     * WE,WPOS,WSITE(ESTORE,CLUB)
     */
    @Column(name = "`source`")
    private String source;

    @Column(name = "`created_time`")
    private Timestamp createdTime;

    @Column(name = "`last_updated_time`")
    private Timestamp lastUpdatedTime;

    @Column(name = "`country`")
    private String country;

    /**
     * 用户是否允许获取手机号
     */
    @Column(name = "`is_oauth`")
    private String isOauth;

}