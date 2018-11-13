package cn.d1m.pandora.entry;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ConsumerCardCount implements Serializable {
    /**
     *
     */
    private String keyStatus;
    private int couponsCount;
    private List<ConsumerCard> list;
}
