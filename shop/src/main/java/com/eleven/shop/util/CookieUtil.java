package com.eleven.shop.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	private static final int MAX_AGE=7*24*60*60;
   public static void addCookie(HttpServletResponse response ,Cookie cookie){
	   if(cookie!=null){
		   response.addCookie(cookie);
	   }
   }
   public static void addCookie(HttpServletResponse response ,String cookieName,String cookieValue,int maxAge ){
	   if(!StringUtil.isNullOrEmpty(cookieName)){
		   if(cookieValue==null){
			   cookieValue="";
		   }
	   }
	   Cookie newCookie = new Cookie(cookieName, cookieValue);
	   if(maxAge>0){
		   newCookie.setMaxAge(maxAge);
	   }
   }
   public static Cookie getCookie(HttpServletRequest request ,String cookieName){
	   Cookie[] cookies = request.getCookies();
	   if(cookies==null||StringUtil.isNullOrEmpty(cookieName)){
		   return null;
	   }
	   for(Cookie cookie :cookies){
		   if(cookie.getName().equals(cookieName)){
			   return cookie;
		   }
	   }
	   return null;
    }
   
   public static String getCookieValue(HttpServletRequest request ,String cookieName){
	   Cookie cookie =getCookie(request, cookieName);
	   if(cookie==null){
		   return null;
	   }else{
		   return cookie.getValue();
	   }
   }
   
   public static void delCookie(HttpServletResponse response,Cookie cookie){
	   if(cookie!=null){
		   cookie.setMaxAge(0);
		   response.addCookie(cookie);
	   }
   }
   
   public void delCookie(HttpServletRequest request ,HttpServletResponse response,String cookieName){
	   Cookie cookie = getCookie(request, cookieName);
	   if(cookie!=null&&cookie.getName().equals(cookieName)){
		   delCookie(response, cookie);
	   }
   }
   
   public static void editCookie(HttpServletRequest request ,HttpServletResponse response,String cookieName,String cookieValue){
	  Cookie cookie = getCookie(request, cookieName);
	  if(cookie!=null&&StringUtil.isNullOrEmpty(cookieName)){
		  if(cookie.getName().equals(cookieName)){
			  addCookie(response, cookieName,cookieValue,MAX_AGE);
		  }
	  }
   }
}
