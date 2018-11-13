package com.d1m.social.wechat.pandora.integration.layer.api.entry.scv;

import java.util.ArrayList;

public class ErrorInfo {
	private String code;// (string): Error code, enumerated ,
	private String message ;//(string): Error message , 
	private ArrayList<Error>  details ;//(Array[Error], optional): List of detailed errors
	public ErrorInfo(String code ,String message) {
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList<Error> getDetails() {
		return details;
	}
	public void setDetails(ArrayList<Error> details) {
		this.details = details;
	}

}
