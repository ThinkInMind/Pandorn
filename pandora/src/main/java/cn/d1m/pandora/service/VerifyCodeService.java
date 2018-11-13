package cn.d1m.pandora.service;

public interface VerifyCodeService {

	public  boolean checkVerifyCode(String mobilePhone, String code);
	public  boolean sendNewVerifyCode(String mobilePhone);
}
