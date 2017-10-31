<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单页面</title>
<link href="${pageContext.request.contextPath}/css/common.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/cart.css"
	rel="stylesheet" type="text/css" />

</head>
<body>

	<%@include file="menu.jsp"%>

	<div class="container cart">

		<div class="span24">



			<table>
				<tbody>
					<tr>
						<th>图片</th>
						<th>商品</th>
						<th>价格</th>
						<th>数量</th>
						<th>小计</th>
					</tr>

					<c:forEach var="item" items="${cart.cartItems}">
						<c:if test="${item.isSelected }">
							<tr>
								<td width="60"><img
									src="${ pageContext.request.contextPath }/<c:out value="${ item.product.image}"/>" />
								</td>
								<td><c:out value="${item.product.pName}" /></td>
								<td><c:out value="${item.product.shopPrice}" /></td>
								<td class="quantity"><c:out value="${item.count}" /></td>
								<td width="140"><span class="subtotal">￥ <c:out
											value="${item.subTotal}" /></span></td>
							</tr>
						</c:if>
					</c:forEach>
					<%--	<c:forEach items="${cart.cartItems }" var="item">
						<tr>
							<td width="60">
								<img
								src="${pageContext.request.contextPath}/${item.product.image}">
							</td>
							<td><a target="_blank"> ${item.product.pName}</a></td>
							<td>${item.product.shopPrice}</td>
							<td class="quantity" width="60">${item.count}</td>
							<td width="140"><span class="subtotal">￥${item.subTotal}</span></td>
						</tr>
					</c:forEach>--%>
				</tbody>
			</table>
			<dl id="giftItems" class="hidden" style="display: none;">
			</dl>
			<div class="ordertotal">
				商品金额: <span><strong class="em">￥${cart.total}元</strong></span>
			</div>
			<form id="orderForm"
				action="${pageContext.request.contextPath }/order/submitOrder"
				method="post">
				<input type="hidden" name="order.oid" value="" />
				<div class="span24">
					<p>
						收货地址：<input name="address" type="text" value="${user.address }"
							style="width: 350px" /> <br /> 收货人&nbsp;&nbsp;&nbsp;：<input
							name="ownerName" type="text" value="${user.userName }"
							style="width: 150px" /> <br /> 联系方式：<input name="phone"
							type="text" value="${user.phone}" style="width: 150px" />

					</p>
					<hr />
					<p>
						选择银行：<br /> <input type="radio" name="pd_FrpId"
							value="ICBC-NET-B2C" checked="checked" />工商银行 <img
							src="${ pageContext.request.contextPath }/bank_img/icbc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="BOC-NET-B2C" />中国银行 <img
							src="${ pageContext.request.contextPath }/bank_img/bc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="ABC-NET-B2C" />农业银行 <img
							src="${ pageContext.request.contextPath }/bank_img/abc.bmp" align="middle" /> <br /> <input
							type="radio" name="pd_FrpId" value="BOCO-NET-B2C" />交通银行 <img
							src="${ pageContext.request.contextPath }/bank_img/bcc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="PINGANBANK-NET" />平安银行
						<img src="${ pageContext.request.contextPath }/bank_img/pingan.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="CCB-NET-B2C" />建设银行 <img
							src="${ pageContext.request.contextPath }/bank_img/ccb.bmp" align="middle" /> <br /> <input
							type="radio" name="pd_FrpId" value="CEB-NET-B2C" />光大银行 <img
							src="${ pageContext.request.contextPath }/bank_img/guangda.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="CMBCHINA-NET-B2C" />招商银行
						<img src="${ pageContext.request.contextPath }/bank_img/cmb.bmp" align="middle" />
					</p>
					<hr />
					<p style="text-align: right">
						<input type="image" name="submit"
							src="${pageContext.request.contextPath}/images/finalbutton.gif"
							width="204" height="51" border="0" />
					</p>
				</div>
			</form>
		</div>

	</div>
	<div class="container footer">
		<div class="span24">
			<div class="footerAd">
				<img src="${pageContext.request.contextPath}/image\r___________renleipic_01/footer.jpg" alt="我们的优势"
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
			<div class="copyright">Copyright © 2016-2017 网上商城 版权所有</div>
		</div>
	</div>
</body>

</html>