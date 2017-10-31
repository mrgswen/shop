<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的订单</title>
<link href="${pageContext.request.contextPath}/css/common.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/cart.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/jquery-ui.css"
	rel="stylesheet" type="text/css">
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script type="text/javascript">

$(function(){
	$("#delete_dialog").dialog({
	      autoOpen:false,
	     // modal: true,
	      height: "150",
		  width:"250",
		  modal:true,
	      buttons: {
	        "确定": function() {
	          $( this ).dialog( "close" );
	          deleteOrder();
	        },
	        "取消": function() {
	          $( this ).dialog( "close" );
	        }
	      }
	    });
	 var oid;
	 var $table;
	$(".deleteOrder").click(function(){
		oid=$(this).parent().find("input[class='oid']").val();
	    $table=$(this).parent().parent().parent().parent();
		$("#delete_dialog").dialog("open");
		
	});
$(".cancelOrder").click(function(){
	var oid=$(this).parent().find("input[class='orderId']").val();
	//var $table=$(this).parent().parent().parent().parent();
	$(this).prev().remove();
	$(this).replaceWith("订单已取消");
	})
	function deleteOrder(){
	 
     $.ajax({
			url:"${pageContext.request.contextPath}/order/deleteOrder?oid="+oid,
		    success:function(data){
		    	if(data.status==1){
		    		var $span24=$table.parent();
		    		$table.remove();
		    		if($span24.find("table[class='order']").length==0){
		    			var pageNow=$("#pageNow").val();
		    			if(pageNow>1){
		    				pageNow-=1;
		    			}
		    			else{
		    				pageNow=1;
			    			$(".pagination").remove();
			    			window.location.href="${pageContext.request.contextPath}/order/findOrdersByUserId/"+pageNow;
		    			}
		    			
		    		}
		    	}
		    }	
		});
}
})

</script>
<body>
  
	<%@include file="menu.jsp"%>
    
	<div class="container cart">

		<div class="span24">

          <input type="hidden" id="pageNow" value="${pages.pageNow}">
           <c:forEach var="order" items="${pages.rows }">
			<table class="order">
				<tbody>
				
			
				<tr><th colspan="3"><input class="oid" type="hidden" value="${order.oid}"><span class="orderNumber">订单号：${order.orderNumber }</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<c:if test="${order.state==1}"><a href="${pageContext.request.contextPath }/order/toPay/${order.orderNumber}"><font color="red">去付款</font></a>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input class="cancelOrder" type="button" value="取消订单"></c:if>
				<c:if test="${order.state==2}">已付款&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input class="cancelOrder" type="button" value="取消订单"></c:if>
				<c:if test="${order.state==3}"><a href="#"><font color="red">确认收货</font></a>
				</c:if>
				<c:if test="${order.state==4}"><a href="#">交易完成</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="deleteOrder"><a href="javascript:void(0);">删除订单</a></span></c:if>
				<c:if test="${order.state==5}">订单已取消&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="deleteOrder"><a href="javascript:void(0);">删除订单</a></span></c:if>
				</th>
				<td colspan="2">${order.createdTime }</td></tr>
				<tr>
						<th>图片</th>
						<th>商品</th>
						<th>价格</th>
						<th>数量</th>
						<th>小计</th>
				</tr>
                    <c:set value="${order.orderItems }" var="orderItems"/>
					<c:forEach var="orderItem" items="${orderItems }">
						
							<tr>
								<td width="60"><img
									src="${ pageContext.request.contextPath }/${ orderItem.product.image}" />
								</td>
								<td><c:out value="${orderItem.product.pName}" /></td>
								<td><c:out value="${orderItem.product.shopPrice}" /></td>
								<td class="quantity"><c:out value="${orderItem.count}" /></td>
								<td width="140"><span class="subtotal">￥ <c:out
											value="${orderItem.subTotal}" /></span></td>
							</tr>
						
						</c:forEach>
					
					
         </table>
         </c:forEach>
         <div class="pagination">
         <c:if test="${pages.total!=0 }">
					<span>第 <c:out value="${pages.pageNow}" />/<c:out
							value="${pages.totalPages}" />页
					</span>
						<!-- 第一页时不能点击首页和上一页 -->
						<c:if test="${pages.pageNow == 1}">
							<span class="firstPage"></span>
							<span class="previousPage"></span>
						</c:if>

						<c:if test="${pages.pageNow != 1}">
							<span> <a class="firstPage"
								href="${pageContext.request.contextPath}/order/findOrdersByUserId/1"></a></span>
							<span><a class="previousPage"
								href="${pageContext.request.contextPath}/order/findOrdersByUserId/<c:out value="${pages.pageNow-1}"/>"></a></span>
						</c:if>

						<c:forEach var="i" begin="${pages.startPage }"
							end="${pages.endPage }">
							<span> <!-- 如果是当前页则不能够点击 --> <c:if
									test="${i == pages.pageNow }">
									<span class="currentPage">${pages.pageNow }</span>
								</c:if> <c:if test="${i != pages.pageNow}">
									<a
										href="${pageContext.request.contextPath}/order/findOrdersByUserId/<c:out value="${i}"/>">
										<c:out value="${i}" />
									</a>
								</c:if>
							</span>
						</c:forEach>
						<!-- 下一页 -->
						<c:if test="${pages.pageNow <pages.totalPages }">
							<span><a class="nextPage"
								href="${pageContext.request.contextPath}/order/findOrdersByUserId/<c:out value="${pages.pageNow+1}"/>"></a></span>
							<span><a class="lastPage"
								href="${pageContext.request.contextPath}/order/findOrdersByUserId/<c:out value="${pages.totalPages}"/>"></a></span>
						</c:if>
						<c:if test="${pages.pageNow ==pages.totalPages }">
							<span class="nextPage"></span>
							<span class="lastPage"></span>
						</c:if>
					
					</c:if>
					
					
				</div>
         </div>
	</div>
	 <div id="delete_dialog" title="确认">
      <p style="padding:0 0 0 20px">确认删除该订单吗?</p>
     </div>
	<div class="container footer">
		<div class="span24">
			<div class="footerAd">
				<img src="${ pageContext.request.contextPath }/image\r___________renleipic_01/footer.jpg" alt="我们的优势"
					title="我们的优势" height="52" width="950">
			</div>
		</div>
		<div class="span24">
			<ul class="bottomNav">
				<li><a href="#">关于我们</a> |</li>
				<li><a href="#">联系我们</a> |</li>
				<li><a href="#">诚聘英才</a> |</li>
				<li><a href="#">法律声明</a> |</li>
				<li><a>友情链接</a> |</li>
				<li><a target="_blank">支付方式</a> |</li>
				<li><a target="_blank">配送方式</a> |</li>
				<li><a>SHOP++官网</a> |</li>
				<li><a>SHOP++论坛</a></li>
			</ul>
		</div>
		<div class="span24">
			<div class="copyright">Copyright © 2005-2015 网上商城 版权所有</div>
		</div>
	</div>
</body>

</html>