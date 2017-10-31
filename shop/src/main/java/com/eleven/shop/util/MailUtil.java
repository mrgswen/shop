package com.eleven.shop.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	private final static String FROM_ADDRESS="13660188167@163.com";
	private final static String  FROM_PASSWORD="mrgswen10";
    public static void SendMail(String toAddress,String code) throws UnsupportedEncodingException, MessagingException{
    	Properties properties = new Properties();
    	properties.setProperty("mail.transport.protocol", "smtp");
    	properties.setProperty("mail.host", "smtp.163.com");
    	properties.setProperty("mail.smtp.auth", "true");
    	
    	Session session = Session.getDefaultInstance(properties);
    	session.setDebug(true);
    	
    	MimeMessage message = new MimeMessage(session);
    	
    	message.setFrom(new InternetAddress(FROM_ADDRESS,"网上商城","utf-8"));
    	InternetAddress toInternetAddress =new InternetAddress(toAddress);
    	message.setRecipient(MimeMessage.RecipientType.TO, toInternetAddress);
    	
    	message.setSubject("用户激活");
    	//message.setContent("�װ����û�,��ӭע�������̳ǣ���������<h3><a href=��http://localhost:8080/shop/userActive/"+code+"'>http://192.168.204.235/shop/userActive/"+code+"</a>���м���</h3>","text/html;charset=utf-8");
    	message.setContent("<h1>商城用户激活!</h1><h3><a href='http://localhost:8080/shop/userActive/"+code+"'>http://172.16.0.96:8080/shop/active/"+code+"</a></h3>", "text/html;charset=UTF-8");
    	message.setSentDate(new Date());
    	message.saveChanges();
    	
    	Transport transport = session.getTransport();
    	transport.connect(FROM_ADDRESS,FROM_PASSWORD);
    	transport.sendMessage(message,message.getAllRecipients());
    	
    }
    public static void main(String[] args) throws UnsupportedEncodingException, MessagingException {
		SendMail("1157387265@qq.com", "12345");
	}
}
