package com.eleven.shop.util;

import org.apache.log4j.Logger;

public class LogUtil {
   private static Logger logger =Logger.getLogger(LogUtil.class);
   
   public static void debug(String message){
	   logger.debug("-----------------------------------------------------"+message);
   }
   
   public static void info(String message){
	   logger.info("-----------------------------------------------------"+message);
   }
   
   public static void info(int val) {
		logger.info("info-----------------------------------------------------" + val);
	}
	
	public static void info(Object object){
		logger.info("------------------------------------------------"+object);
	}
	
	public void main(String[] args){
		info("dd");
	}
}
