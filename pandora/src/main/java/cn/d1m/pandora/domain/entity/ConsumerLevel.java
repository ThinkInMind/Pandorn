package cn.d1m.pandora.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Table(name = "`consumer_level`")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsumerLevel {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 会员等级名称
     */
    @Column(name = "`name`")
    private String name;

    @Column(name = "`description`")
    private String description;

    /**
     * scrm的会员等级
     */
    @Column(name = "`loyalty_tier`")
    private String loyaltyTier;

    @Column(name = "`created_at`")
    private Date createdAt;

    @Column(name = "`deleted`")
    private Boolean deleted;

}