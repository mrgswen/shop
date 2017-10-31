package com.eleven.shop.util;

import java.util.UUID;

public class TokenUtil {
   public static String getToken(){
	   return UUID.randomUUID().toString().replaceAll("-", "");
   }
   public static void main(String[] args){
	   System.out.println(UUID.randomUUID().toString());
   }
}
