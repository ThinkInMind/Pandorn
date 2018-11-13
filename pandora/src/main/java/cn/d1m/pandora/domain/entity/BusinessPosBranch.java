package cn.d1m.pandora.domain.entity;

import cn.d1m.pandora.domain.mybatis.UUIdGenId;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "`business_pos_branch`")
@Data
public class BusinessPosBranch {
    /**
     * 32 UUID
     */
    @Id
    @Column(name = "`id`")
    @KeySql(genId = UUIdGenId.class)
    private String id;

    /**
     * 名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * Pos编号
     */
    @Column(name = "`pos_branch_code`")
    private String posBranchCode;

    @Column(name = "`created_time`")
    private Timestamp createdTime;

}