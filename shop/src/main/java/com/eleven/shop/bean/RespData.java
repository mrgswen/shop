package com.eleven.shop.bean;

public class RespData {
	/**
	 * 1为成功，其它为失败
	 */
  public byte status; 
  /**
   * 若不为空，则为错误信息
   */
  public String msg;
  //返回的业务数据
  public Object content;
@Override
public String toString() {
	return "RespData [status=" + status + ", msg=" + msg + ", content=" + content + "]";
}
  
}
