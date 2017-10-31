package com.eleven.shop.util;

public class StringUtil {
    public static boolean isNullOrEmpty(String str){
    	return str==null||str.isEmpty();
    }
    public static boolean isNullOrBlank(String str){
    	return str==null||(str.trim()).isEmpty();
    }
}
