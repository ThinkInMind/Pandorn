package cn.d1m.pandora.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ConsumerCard implements Serializable {

    private String id;//` VARCHAR(32) COLLATE
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private Timestamp beginTime;//` TIMESTAMP NOT NU
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private Timestamp endTime;//` TIMESTAMP NOT NULL
    private String ruleId;//` VARCHAR(32) CO
    private String openid;//` VARCHAR(28) COL
    private String status;//` VARCHAR(8) COLL
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private Timestamp receiveTime;//` TIMESTAMP
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private Timestamp redeemTime;//` TIMESTAMP
    private String businessPosBranchId;//
    private Timestamp createdTime;//` TIMESTAMP
    private String description;//描述
    private String name;//名称
    private String type;//类型
    private String beginTimestamp;//固定时间起
    private String endTimestamp;//固定时间止
    private String title;//标题
    private String property;//性质
    private String cycle;//0表示单次 周期
    private String posBranchCode;
    private String posBranchName;// 门店名称
    private String code;
    private String posTicketNo;
    private String cardId;

}
