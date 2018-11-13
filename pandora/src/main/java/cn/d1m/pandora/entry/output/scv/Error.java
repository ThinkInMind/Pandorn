package cn.d1m.pandora.entry.output.scv;

import java.util.ArrayList;

public class Error {
	private String code;// (string): Error code, enumerated ,
	private String message ;//(string): Error message , 
	private ArrayList<Error>  details ;//(Array[Error], optional): List of detailed errors
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
