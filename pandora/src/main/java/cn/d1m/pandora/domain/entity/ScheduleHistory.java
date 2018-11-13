package cn.d1m.pandora.domain.entity;

import cn.d1m.pandora.domain.mybatis.UUIdGenId;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "`schedule_history`")
@Data
public class ScheduleHistory {
    /**
     * UUID 32位
     */
    @Id
    @Column(name = "`id`")
    @KeySql(genId = UUIdGenId.class)
    private String id;

    /**
     * 是否成功
     */
    @Column(name = "`successful`")
    private String successful;

    /**
     * 是否失败
     */
    @Column(name = "`fail`")
    private String fail;

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
     * Api_error_log表id uuid 32位
     */
    @Column(name = "`api_error_log_id`")
    private String apiErrorLogId;

    @Column(name = "`created_time`")
    private Timestamp createdTime;

}