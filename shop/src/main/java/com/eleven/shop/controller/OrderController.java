package com.eleven.shop.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eleven.shop.bean.Cart;
import com.eleven.shop.bean.CartItem;
import com.eleven.shop.bean.Operators;
import com.eleven.shop.bean.Order;
import com.eleven.shop.bean.OrderItem;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.RespData;
import com.eleven.shop.bean.User;
import com.eleven.shop.service.OrderService;
import com.eleven.shop.service.UserService;
import com.eleven.shop.util.LogUtil;
import com.eleven.shop.util.PaymentUtil;
import com.eleven.shop.util.StringUtil;
import com.eleven.shop.util.UUIDUtil;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Resource
	private OrderService orderService;
	@Resource
	private UserService userService;
	@RequestMapping("/balanceCart")
    public String balanceCart(){
    	return "order";	
    }
	//@RequestMapping("/orderList")
	//public String orderList(){
	//	return "orderList";
	//}
	@RequestMapping("/submitOrder")
	public String submitOrder(HttpSession session,String pd_FrpId ,Order order){
		User sessionUser =(User) session.getAttribute("user");
		Cart cart = (Cart) session.getAttribute("cart");
		User user=userService.findOne(sessionUser.getId());
		order.setUser(user);
		order.setState((byte) 1);
		order.setTotalPrice(cart.getTotal());
		Set<OrderItem> items = new HashSet<OrderItem>();
		for(CartItem cartItem :cart.getCartItems()){
			LogUtil.info("cartitem"+cartItem);
			if(cartItem.getIsSelected()){
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setOrder(order);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setSubTotal(cartItem.getSubTotal());
			items.add(orderItem);
			cart.deleteItem(cartItem.getProduct().getPid());
			}
		}
	   order.setOrderNumber(UUIDUtil.getUUID());
		order.setOrderItems(items);
		orderService.save(order);
		String redirectUrl=pay(order.getOrderNumber(), pd_FrpId);
	    return  "redirect:"+redirectUrl;
	}
	
	@RequestMapping("/findOrdersByUserId/{pageNow}")
	public String findOrdersByUserId(@PathVariable int pageNow,HttpSession session,Map<String, Object> map){
		PagingList<Order> pages=new PagingList<Order>();
		int pageSize=5;
		User user=(User) session.getAttribute("user");
		LogUtil.info(user);
		if(user!=null){
			pages=orderService.findOrdersByUserId(pageNow,pageSize,user.getId());
		}
		map.put("pages", pages);
		return "orderList";
	}
	@RequestMapping("/toPay/{orderNumber}")
	public String toPay(@PathVariable String orderNumber,Map<String, Object> map){
		map.put("orderNumber", orderNumber);
		LogUtil.info("orderNumber"+orderNumber);
		if(orderService.findByOrderNumber(orderNumber)!=null)
		return "pay";
		else{ 
			map.put("payMsg","该订单不存在");
			return "msg";
		}
	}
	@RequestMapping("/payOrder")
	public String payOrder(String orderNumber ,String pd_FrpId){
		String redirectUrl=pay(orderNumber, pd_FrpId);
	    return  "redirect:"+redirectUrl;
	}
	@RequestMapping("/callBack")
	public String callBack(HttpServletRequest request,Map<String,Object> map) throws IOException{
		// 验证请求来源和数据有效性
				// 阅读支付结果参数说明
				// System.out.println("==============================================");
				String p1_MerId = request.getParameter("p1_MerId");
				String r0_Cmd = request.getParameter("r0_Cmd");
				String r1_Code = request.getParameter("r1_Code");
				String r2_TrxId = request.getParameter("r2_TrxId");
				String r3_Amt = request.getParameter("r3_Amt");
				String r4_Cur = request.getParameter("r4_Cur");
				String r5_Pid = request.getParameter("r5_Pid");
				String r6_Order = request.getParameter("r6_Order");
				String r7_Uid = request.getParameter("r7_Uid");
				String r8_MP = request.getParameter("r8_MP");
				String r9_BType = request.getParameter("r9_BType");
//				String rb_BankId = request.getParameter("rb_BankId");
//				String ro_BankOrderId = request.getParameter("ro_BankOrderId");
//				String rp_PayDate = request.getParameter("rp_PayDate");
//				String rq_CardNo = request.getParameter("rq_CardNo");
//				String ru_Trxtime = request.getParameter("ru_Trxtime");

				// hmac
				String hmac = request.getParameter("hmac");
				// 利用本地密钥和加密算法 加密数据
				String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
				boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
						r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
						r8_MP, r9_BType, keyValue);
				Order currOrder = orderService.findByOrderNumber(r6_Order);
				
				if (isValid) {
					// 有效
					if (r9_BType.equals("1")) {
						// 浏览器重定向
						currOrder.setState((byte)2);
						orderService.update(currOrder);
						map.put("paySuccessfully", "success");                                                                         
						map.put("orderId", r6_Order);
						map.put("amount", r3_Amt);
						
					} else if (r9_BType.equals("2")) {
						
						// 服务器点对点，来自于易宝的通知
						// 回复给易宝success，如果不回复，易宝会一直通知
						currOrder.setState((byte)2);
						orderService.save(currOrder);
						map.put("paySuccessfully", "success");
						
					}
				} else {
					throw new RuntimeException("数据被篡改！");
				}
				return "msg";
	}
	private String pay(String orderNumber,String pd_FrpId){
		String p0_Cmd = "Buy"; // 业务类型
		String p1_MerId = "10001126856";// 商户编号
		String p2_Order =orderNumber;// 订单编号
		String p3_Amt = "0.01"; // 支付金额
		String p4_Cur = "CNY"; // 交易币种
		String p5_Pid = ""; //商品名称
		String p6_Pcat = ""; //商品种类
		String p7_Pdesc = ""; // 商品描述
		String p8_Url = "http://localhost:8080/shop/order/callBack"; // 商品接收支付成功数据的地址
		String p9_SAF = ""; //送货地址
		String pa_MP = ""; // 商户扩展信息
		String pr_NeedResponse = "1"; // 应答机制
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl"; // 秘钥
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue); // 生成hmac
		
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);
		LogUtil.info("url+"+sb.toString());
		return sb.toString();
	}
	
	@RequestMapping("/cancelOrder")
	public RespData cancelOrder(){
		RespData respData=new RespData();
		return respData;
	}
	
	@RequestMapping("/deleteOrder")
	@ResponseBody
	public RespData deleteOrder( Integer oid){
		RespData respData=new RespData();
		orderService.delete(oid);
		respData.status=1;
		return respData;
	}
	  @RequestMapping("/admin/getOrders")
	    @ResponseBody
	    public PagingList<Order> getOrders(int page,int rows,Date dateFrom,Date dateTo,HttpServletRequest request){
	    	String queryStr=request.getParameter("queryStr");
	    	String field=request.getParameter("field");
	    	String userName=request.getParameter("userName");
	    	String sstate=request.getParameter("state");
	    	byte state=0;
	    	if(!StringUtil.isNullOrBlank(sstate))
	    	state=Byte.parseByte(sstate);
	    	Map<String, Object[]> params=new HashMap<String, Object[]>();
	    	LogUtil.info("byte"+state);
	    	if(dateFrom!=null&&dateTo!=null){
				 Object[] operatorAndValue={Operators.GE.operator,dateFrom};
				 Object[] operatorAndValue2={Operators.LE.operator,dateTo};
				 params.put("createdTime", operatorAndValue);
				 params.put("createdTime2", operatorAndValue2);
			}
	    	if(!StringUtil.isNullOrBlank(userName)){
	    		 Object[] operatorAndValue={Operators.LIKE.operator,userName};
	    		 params.put("userName", operatorAndValue);
	    	}
	    	if(state!=0){
	    		 Object[] operatorAndValue={Operators.EQ.operator,state};
	    		 params.put("state", operatorAndValue);
	    	}
	  
	    	return orderService.getOrders(queryStr, field, page, rows, params);
	    	
	    }
	    @RequestMapping("/admin/deleteOrders")
	    @ResponseBody
	    public RespData deleteOrders(String ids){
	    	RespData respData=new RespData();
	    	int affected=orderService.deleteOrders(ids);
	    	if(affected>=0){
	    		respData.content=affected;
	    		respData.msg=affected+"条记录已被删除";
	    		respData.status=1;
	    	}else{
	    		respData.status=2;
	    		respData.msg="删除失败";
	    	}
	    	return respData;
	    }
		@InitBinder
		public void initBinder(WebDataBinder binder) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.setLenient(false);
			binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		}
}
