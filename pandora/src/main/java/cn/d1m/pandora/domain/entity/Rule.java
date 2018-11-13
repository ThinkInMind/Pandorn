package cn.d1m.pandora.domain.entity;

import cn.d1m.pandora.domain.mybatis.UUIdGenId;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "`rule`")
@Data
public class Rule {
    /**
     * 32 UUID
     */
    @Id
    @Column(name = "`id`")
    @KeySql(genId = UUIdGenId.class)
    private String id;

    /**
     * 显示编号
     */
    @Column(name = "`code`")
    private String code;

    /**
     * 应用规则
     */
    @Column(name = "`rule`")
    private String rule;

    /**
     * 名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 张数
     */
    @Column(name = "`quantity`")
    private Integer quantity;

    /**
     * 性质
     */
    @Column(name = "`property`")
    private String property;

    /**
     * 0表示单次 周期
     */
    @Column(name = "`cycle`")
    private String cycle;

    /**
     * 创建时间
     */
    @Column(name = "`created_time`")
    private Timestamp createdTime;

    /**
     * 创建人
     */
    @Column(name = "`created_by`")
    private String createdBy;

    /**
     * 最后更新时间
     */
    @Column(name = "`last_updated_time`")
    private Timestamp lastUpdatedTime;

    /**
     * 最后更新人
     */
    @Column(name = "`last_updated_by`")
    private String lastUpdatedBy;
}