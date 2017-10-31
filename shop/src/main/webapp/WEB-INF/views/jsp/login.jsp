<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>会员登录</title>

<link href="${pageContext.request.contextPath}/css/common.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/login.css"
	rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript">
	$(function() {
		
		$("#userName").blur(function() {
			//var cid=${sessionScope.category};
		//	alert(cid);
			var userName = $("#userName").val().trim();
			///	var userCheck=false;
			if (userName == null || userName == '') {
				$("#checkName").html("<font color='red'>用户名不能为空！</font>");
					$("#submit").attr("disabled", true);

			} else {
				$("#checkName").html('');
				//	userCheck=true;
				$("#submit").attr("disabled", false);
			}
		});
		//	alert(userCheck);
		//$("#userName").focus(function() {
		//   alert($("#checkName").text());
		///	if ($("#checkName").text()!='') {
		///	$("#checkName").html('');

		//}
		//});
		var passwordCheck = false;
		$("#password").blur(function() {
			var password = $("#password").val().trim();
			if (password == null || password == '') {
				$("#checkPassword").html("<font color='red'>密码不能为空！</font>");
					$("#submit").attr("disabled", true);
			} else {
				$("#checkPassword").html('');
				//	var passwordCheck=true;
				$("#submit").attr("disabled", false);
			}
		});
		$("#checkCode").hover(function() {
			var checkCode = $("#checkCode").val().trim();
			if (checkCode == null || checkCode == '') {
				$("#checkCodeValidate").html("<font color='red'>请输入验证码！</font>");
					$("#submit").attr("disabled", true);}}
			,function() {
				$("#checkCodeValidate").html('');
				//	var passwordCheck=true;
				$("#submit").attr("disabled", false);
			}
		);
		//var params = {
		//	"userName" : $("#userName").val(),
		//	"password" : password
		//};

		//if(userCheck==true&&passwordCheck==true){
		//	alert(userCheck)
		//	$("#submit").attr("disabled", false);
		//	}
		$("#captchaImage").click(
				function() {
					$("#captchaImage").attr(
							"src",
							"${pageContext.request.contextPath}/getCheckCodeImage?date="
									+ new Date().getTime());
				});
		$("#submit")
				.click(
						function() {
							var userName = $("#userName").val().trim();
							var password = $("#password").val().trim();
							var checkCode = $("#checkCode").val().trim();
							if(checkCode==""){
								$("#checkCodeValidate").html("<font color='red'>请输入验证码！</font>");
								return false;
							}
							
							//alert(checkCode);
							$
									.ajax({
										type : "POST",
										url : "${pageContext.request.contextPath }/userLogin",
										data : {
											"userName" : userName,
											"password" : password,
										    "checkCode" : checkCode
										},
										//contentType : "application/json;charset=UTF-8",
										success : function(data) {
											//alert(data.loginStatus);
											if (data.loginStatus == "noUser") {
												$("#checkName")
														.html(
																"<font color='red'>用户不存在</font>");
											} else if (data.loginStatus == "notActive") {
												$("#checkName")
														.html(
																"<font color='red'>请先激活用户</font>");
											} else if (data.loginStatus == "incorrectPassword") {
												$("#checkPassword")
														.html(
																"<font color='red'>密码不正确</font>");
											} else if (data.loginStatus == "incorrectCheckCode") {
												$("#checkCodeValidate")
												.html(
														"<font color='red'>验证码出错</font>");}
											else if (data.loginStatus == "success") {
												window.location.href = "${pageContext.request.contextPath}/index";
											}else if(data.loginStatus=="successAndRedirect"){
												window.location.href = data.previousUrl;
											}
										},
										error : function(XMLHttpRequest,
												textStatus, errorThrown) {
											alert("error happended: "
													+ XMLHttpRequest.responseText
													+ " textStatus: "
													+ textStatus
													+ " errorThron: "
													+ errorThrown);
										}
									});
							return false;
						});

	});
</script>
<body>
	<%@include file="menu.jsp"%>
	<div class="container login">
		<div class="span12">
			<div class="ad">
				<img src="./image/login.jpg" width="500" height="330" alt="会员登录"
					title="会员登录">
			</div>
		</div>
		<div class="span12 last">
			<div class="wrap">
				<div class="main">
					<div class="title">
						<strong>会员登录</strong>USER LOGIN
					</div>
					<form id="loginForm" method="post">
						<table>
							<tbody>
								<tr>
									<th>用户名:</th>
									<td><input type="text" name="userName" id="userName"
										class="text" maxlength="20" /><span id="checkName"
										style="padding-left: 10px;"></span></td>
								</tr>
								<tr>
									<th>密&nbsp;&nbsp;码:</th>
									<td><input type=password id="password" class="text"
										maxlength="20" /><span id="checkPassword"
										style="padding-left: 10px;"></span></td>
								</tr>
								<tr>
									<th>验证码:</th>
									<td><input type="text" id="checkCode" name="checkCode"
										class="text captcha" maxlength="4" style="width:120px"/> <img id="captchaImage"
										class="captchaImage" src="${pageContext.request.contextPath}/getCheckCodeImage" title="点击更换验证码" /> <span
										id="checkCodeValidate" style="padding-left: 10px;"></span></td>
								</tr>
								<tr>
									<th>&nbsp;</th>
									<td><label> <input type="checkbox"
											id="isRememberUsername" name="isRememberUsername"
											value="true" />记住用户名
									</label> <label> &nbsp;&nbsp;<a>找回密码</a>
									</label></td>
								</tr>
								<tr>
									<th>&nbsp;</th>
								<td><input type="submit" class="submit" id="submit" disabled="disabled"
										value="登 录" /></td>
								</tr>
								<tr class="register">
									<th>&nbsp;</th>
									<td>
										<dl>
											<dt>还没有注册账号？</dt>
											<dd>
												立即注册即可体验在线购物！ <a
													href="${pageContext.request.contextPath }/userRegister">立即注册</a>
											</dd>
										</dl>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="container footer">
		<div class="span24">
			<div class="footerAd">
				<img src="./image/footer.jpg" width="950" height="52" alt="我们的优势"
					title="我们的优势" />
			</div>
		</div>
		<div class="span24">
			<ul class="bottomNav">
				<li><a>关于我们</a> |</li>
				<li><a>联系我们</a> |</li>
				<li><a>招贤纳士</a> |</li>
				<li><a>法律声明</a> |</li>
				<li><a>友情链接</a> |</li>
				<li><a target="_blank">支付方式</a> |</li>
				<li><a target="_blank">配送方式</a> |</li>
				<li><a>服务声明</a> |</li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div class="span24">
			<div class="copyright">Copyright © 2005-2015 网上商城 版权所有</div>
		</div>
	</div>
</body>
</html>