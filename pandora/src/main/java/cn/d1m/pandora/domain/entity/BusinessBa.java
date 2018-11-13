package cn.d1m.pandora.domain.entity;

import cn.d1m.pandora.domain.enumeration.BusinessBaStatus;
import cn.d1m.pandora.domain.mybatis.UUIdGenId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "`business_ba`")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessBa {
    @Id
    @Column(name = "`id`")
    @KeySql(genId = UUIdGenId.class)
    private String id;

    @Column(name = "`business_pos_branch_id`")
    private String businessPosBranchId;

    @Column(name = "`username`")
    private String username;

    @Column(name = "`email`")
    private String email;

    @Column(name = "`status`")
    private BusinessBaStatus status;

    @Column(name = "`password`")
    private String password;

    @Column(name = "`role`")
    private String role;

    @Column(name = "`created_at`")
    private Timestamp createdAt;

    @Column(name = "`updated_at`")
    private Timestamp updatedAt;

}