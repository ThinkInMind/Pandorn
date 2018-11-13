package cn.d1m.pandora.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class Rule implements Serializable {

    @NotBlank(groups = {Second.class, First.class, Delete.class})
    @Length(max = 32, groups = {Second.class, First.class, Delete.class, Redeemed.class})
    private String id;// varchar(32) NOT NU
    private String cardId;// varchar(32) D
    @NotBlank(groups = {Third.class})
    @Length(max = 16, groups = {Third.class, First.class})
    private String rule;// varchar(16) DEFA

     @NotNull(groups = {Third.class})
    @Range(max = 5,groups = {Third.class, First.class})
    private Integer quantity;

    @NotBlank(groups = {Third.class})
    @Length( max = 32, groups = {Third.class, First.class})
    private String name;// varchar(32) DEFA
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp beginTimestamp;// times
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp endTimestamp;// timesta
    @Length(max = 3, groups = {Third.class, First.class})
    private String sendedBeginTimestamp;//
    @Length(max = 3, groups = {Third.class, First.class})
    private String sendedEndTimestamp;//
    @NotBlank(groups = {Third.class})
    @Length(max = 64, groups = {Third.class, First.class})
    private String title;//` varchar(64) DEF
    @NotBlank(groups = {Third.class})
    @Length(max = 8, groups = {Third.class, First.class})
    private String property;//` varchar(8) D
    @NotBlank(groups = {Third.class})
    @Length(max = 2, groups = {Third.class, First.class})
    private String cycle;//` varchar(2) DEFA
    @NotBlank(groups = {Second.class})
    @Length(max = 8, groups = {Second.class, First.class})
    private String status;//` varchar(8) DEF
    //@NotBlank(groups = {Third.class})
    @NotNull(groups = {Third.class})
    @Range(max = 5, groups = {Third.class, First.class})
    private Integer quality;//` int(5) DEFAUL
    @Length(max = 5, groups = {Third.class, First.class})
    private String vip0;//
    @Length(max = 5, groups = {Third.class, First.class})
    private String vip1;//` varchar(5) DEFAU
    @Length(max = 5, groups = {Third.class, First.class})
    private String vip2;//` varchar(5) DEFAU
    @Length(max = 5, groups = {Third.class, First.class})
    private String vip3;//` varchar(5) DEFAU
    @Length(max = 5, groups = {Third.class, First.class})
    private String vip4;//` varchar(5) DEFAU
    @Length(max = 256, groups = {Third.class, First.class})
    private String description;//` varchar(2
    private String isdelete;//` varchar(8) D
    //	`createdTime` timestam
    @NotBlank(groups = {Second.class, First.class, Delete.class})
    @Length(max = 32)
    private String createdBy;//` varchar(32
    //	`lastUpdatedTime` tim
//	`last_updated_by` varch
    private String code;

}
