package cn.d1m.pandora.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "`card_consumer_level`")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardConsumerLevel {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    @Column(name = "`card_id`")
    private String cardId;

    @Column(name = "`consumer_level_id`")
    private Integer consumerLevelId;
}