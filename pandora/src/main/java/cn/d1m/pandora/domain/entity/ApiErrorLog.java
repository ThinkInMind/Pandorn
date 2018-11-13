package cn.d1m.pandora.domain.entity;

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

@Table(name = "`api_error_log`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorLog {
    /**
     * 32 UUID
     */
    @Id
    @Column(name = "`id`")
    @KeySql(genId = UUIdGenId.class)
    private String id;

    /**
     * /api/v1/xx
     */
    @Column(name = "`api`")
    private String api;

    /**
     * 链接url
     */
    @Column(name = "`url`")
    private String url;

    /**
     * 参数
     */
    @Column(name = "`parameters`")
    private String parameters;

    /**
     * ContentType: application/json, ContentType: form-data
     */
    @Column(name = "`header`")
    private String header;

    /**
     * GET,POST
     */
    @Column(name = "`method`")
    private String method;

    @Column(name = "`created_time`")
    private Timestamp createdTime;

}