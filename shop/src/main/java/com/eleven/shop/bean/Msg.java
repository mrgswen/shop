package com.eleven.shop.bean;

import java.util.HashMap;
import java.util.Map;

public class Msg {
	/**
	 * 响应状态码 200成功 100失败
	 */
	private String code;
	private String message;
	private Map<String, Object> content = new HashMap<String, Object>();

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

	public Map<String, Object> getContent() {
		return content;
	}

	public void setContent(Map<String, Object> content) {
		this.content = content;
	}
    
	public static Msg success(){
		Msg msg=new Msg("200","处理成功");
		return msg;
	}
	
	public static Msg success(String messageg){
		Msg msg=new Msg("200",messageg);
		return msg;
	}
	
	public static Msg  fail(){
		Msg msg = new Msg("100", "处理失败");
		return msg;
	}
	
	public static Msg fail(String message){
		Msg msg= new Msg("100", message);
		return msg;
	}
    
	public Msg add(String key,Object value){
		this.getContent().put(key, value);
		return this;
	}
	
	public Msg(String code, String message) {
		super();
		this.code = code;
		this.message = message;
		
	}

	public Msg() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
