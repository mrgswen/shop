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
					<form id="payForm"
				action="${pageContext.request.contextPath }/order/payOrder"
				method="post">
				<input type="hidden" name="oid" value="${oid }" />
				<div class="span24">
					<hr />
					<p>
						选择银行：<br /> <input type="radio" name="pd_FrpId"
							value="ICBC-NET-B2C" checked="checked" />工商银行 <img
							src="${pageContext.request.contextPath }/bank_img/icbc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="BOC-NET-B2C" />中国银行 <img
							src="${pageContext.request.contextPath }/bank_img/bc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="ABC-NET-B2C" />农业银行 <img
							src="${pageContext.request.contextPath }/bank_img/abc.bmp" align="middle" /> <br /> <input
							type="radio" name="pd_FrpId" value="BOCO-NET-B2C" />交通银行 <img
							src="${pageContext.request.contextPath }/bank_img/bcc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="PINGANBANK-NET" />平安银行
						<img src="${pageContext.request.contextPath }/bank_img/pingan.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="CCB-NET-B2C" />建设银行 <img
							src="${pageContext.request.contextPath }/bank_img/ccb.bmp" align="middle" /> <br /> <input
							type="radio" name="pd_FrpId" value="CEB-NET-B2C" />光大银行 <img
							src="${pageContext.request.contextPath }/bank_img/guangda.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="CMBCHINA-NET-B2C" />招商银行
						<img src="${pageContext.request.contextPath }/bank_img/cmb.bmp" align="middle" />
					</p>
					<hr />
					<p style="text-align: right">
						<input type="submit" value="确认支付"
							width="204" height="51" border="0" />
					</p>
				</div>
			</form>
		</div>

	</div>
	<div class="container footer">
		<div class="span24">
			<div class="footerAd">
				<img src="${pageContext.request.contextPath }/image\r___________renleipic_01/footer.jpg" alt="我们的优势"
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