package com.eleven.shop.bean;

public enum Operators {
	 EQ("="), LE("<="),LIKE("like"),GE(">=");
	private Operators(String operator){
		this.operator=operator;
	}
	public String operator;
}
