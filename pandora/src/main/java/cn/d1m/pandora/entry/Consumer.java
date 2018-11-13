package cn.d1m.pandora.entry;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.json.JSONObject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

//@GroupSequence({First.class})
@Data
public class Consumer implements Serializable {


    /**
     *
     */
    @NotBlank(message = "not be empty", groups = {Login.class})
    private String loginID;
    @NotBlank(message = "not be empty", groups = {Login.class})
    private String password;

    @NotBlank(message = "not be empty", groups = {LoginBefor.class})
//    @JsonProperty("is_oauth")
    private String isOauth;


    @Email(message = "输入正确的邮箱", groups = {First.class, Update.class, Add.class})
    @NotNull(message = "not be empty", groups = {First.class})
    @Size(max = 64, message = "size is too b long", groups = {Update.class, Add.class})
//    @JsonProperty("email_address")
    private String emailAddress;//邮件
    @NotNull(message = "not be empty", groups = {LoginUpd.class})
    @Size(max = 11, message = "手机号不正确", groups = {First.class, Add.class, Query.class, LoginUpd.class})
//    @JsonProperty("mobile_phone")
    private String mobilePhone;//电话
    private String id;//32UUID
    @NotBlank(message = "firstName not be empty", groups = {Add.class, Update.class, LoginUpd.class})
    @Size(max = 16, message = "size is too b long", groups = {Update.class, Add.class, LoginUpd.class})
//    @JsonProperty("first_name")
    private String firstName;//名
    @Size(max = 16, message = "size is too b long", groups = {Update.class, Add.class, LoginUpd.class})
    @NotBlank(message = "lastName not be empty", groups = {Add.class, Update.class, LoginUpd.class})
//    @JsonProperty("last_name")
    private String lastName;//姓

    //    @JsonProperty("mobile_area_code")
    private String mobileAreaCode;//区号
    @Size(max = 10, message = "size is too b long", groups = {Update.class, Add.class, LoginUpd.class})
    @NotBlank(message = "birthdayDay not be empty", groups = {Add.class, Update.class, LoginUpd.class})
//    @JsonProperty("birthday_day")
    private String birthdayDay;//生日
    @Size(max = 2, message = "size is too b long", groups = {Update.class, Add.class, LoginUpd.class})
    @NotBlank(message = "birthdayDay not be empty", groups = {Add.class, Update.class, LoginUpd.class})
//    @JsonProperty("birthday_month")
    private String birthdayMonth;//月
    @Size(max = 4, message = "size is too b long", groups = {Update.class, Add.class, LoginUpd.class})
    @NotBlank(message = "birthdayDay not be empty", groups = {Add.class, Update.class, LoginUpd.class})
//    @JsonProperty("birthday_year")
    private String birthdayYear;
    private String birthday;
    //    @JsonProperty("title_salutation")
    private String titleSalutation;//称谓
    @Size(max = 16, message = "size is too b long", groups = {Update.class, Add.class, LoginUpd.class})
    @NotBlank(message = "gender not be empty", groups = {Add.class, Update.class, LoginUpd.class})
    private String gender;//性别
    //    @JsonProperty("vip_number")
    private String vipNumber;// VIP编号

    //    @JsonProperty("loyalty_points")
    private String loyaltyPoints;//
    //    @JsonProperty("loyalty_tier")
    private String loyaltyTier;// VARC
    //    @JsonProperty("loyalty_points_expiry_date")
    private String loyaltyPointsExpiryDate;//
    //    @JsonProperty("next_loyalty_tier")
    private String nextLoyaltyTier;//
    //    @JsonProperty("points_to_next_tier")
    private String pointsToNextTier;//
    //    @JsonProperty("previous_loyalty_tier")
    private String previousLoyaltyTier;//
    //    @JsonProperty("last_birthday_discount_redemption_date")
    private String lastBirthdayDiscountRedemptionDate;//
    //	@NotBlank(message = "openid is null",groups = {Add.class,Query.class,First.class,LoginBefor.class,Login.class})
//	@Size(max=40,message="size is too b long", groups = {First.class, Update.class,Add.class,LoginBefor.class,Login.class})
    private String openid;//
    private String unionid;//
    private String status;//
    private String crmid;//
    private String channel;//
    private String source;//
    //    @JsonProperty("createdTime")
    private Timestamp createdTime;//
    //    @JsonProperty("last_updated_time")
    private Timestamp lastUpdatedTime;//
    private String country;
    //    @JsonProperty("verificationCode")
    private String verificationCode;// 验证码
    private String sourceId;


    public static JSONObject response(Consumer consumer) {
        JSONObject json = new JSONObject();
        json.put("firstName", consumer.getFirstName());
        json.put("lastName", consumer.getLastName());
        json.put("titleSalutation", consumer.getTitleSalutation());
        json.put("birthdayDay", consumer.getBirthdayDay());
        json.put("birthdayMonth", consumer.getBirthdayMonth());
        json.put("birthdayYear", consumer.getBirthdayYear());
        json.put("gender", consumer.getGender());
        json.put("emailAddress", consumer.getEmailAddress());
        json.put("mobilePhone", consumer.getMobilePhone());
        json.put("country", consumer.getCountry());
        json.put("createdTime", consumer.getCreatedTime());
        json.put("status", consumer.getStatus());
        json.put("lastUpdatedTime", consumer.getLastUpdatedTime());
        json.put("vipNumber", consumer.getVipNumber());
        json.put("loyaltyTier", consumer.getLoyaltyTier());
        json.put("loyaltyPoints", consumer.getLoyaltyPoints());
        json.put("pointsToNextTier", consumer.getPointsToNextTier());
        json.put("pointsToNextTier", consumer.getSource());
        json.put("crmid", consumer.getCrmid());
        return json;
    }

    public static JSONObject responseLoyaltyPoints(Consumer consumer) {
        JSONObject json = new JSONObject();
        json.put("vipNumber", consumer.getVipNumber());
        json.put("loyaltyTier", consumer.getLoyaltyTier());
        json.put("loyaltyPoints", consumer.getLoyaltyPoints());
        json.put("pointsToNextTier", consumer.getPointsToNextTier());
        json.put("registrationDate", consumer.getCreatedTime());
//		json.put("r", consumer.getSource());
        return json;
    }

    public static JSONObject responseCommon(Consumer consumer) {
        JSONObject json = new JSONObject();
        json.put("token", consumer.getId());

        return json;
    }

    public static JSONObject queryConsumer(Consumer consumer) {
        JSONObject json = new JSONObject();
        json.put("token", consumer.getId());
        json.put("mobilePhone", consumer.getMobilePhone());
        return json;
    }
}
