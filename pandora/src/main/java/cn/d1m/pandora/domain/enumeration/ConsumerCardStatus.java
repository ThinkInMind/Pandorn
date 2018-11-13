package cn.d1m.pandora.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * Created by jone.wang on 2018/9/30.
 * Description:
 */
public enum ConsumerCardStatus {
    NORMAL("normal"),
    REDEEMED("redeemed"),
    EXPIRED("expired");

    private String value;


    ConsumerCardStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ConsumerCardStatus fromValue(String value) {
        return Arrays
                .stream(values()).filter(v -> v.getValue().equals(value))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Error value: " + value));
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }
}
