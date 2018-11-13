package cn.d1m.pandora.entry;

import lombok.Data;

import java.io.Serializable;

@Data
public class SmsSender implements Serializable {

    private String id;
    private String verificationCode;
    private String mobile;
    private String expireTime;
    private int times;

}
