<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商城后台管理员登录</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/adminlogin.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function() {
		$("#login").dialog({
			title : '登录后台',
			width : 400,
			height : 240,
			modal : true,
			iconCls : 'icon-login',
			buttons : '#loginbtn',
			closable:false,
		});

		$('#admin').validatebox({
			required : true,
			missingMessage : '请输入管理员账号',
			invalidMessage : '账号不能为空',
		});

		$("#adminpassword").validatebox({
			required : true,
			validType:'length[6,10]',
			missingMessage : '请输入密码',
			invalidMessage : '密码长度在6-10位之间',
		});
        if(!$('#admin').validatebox('isValid')){
        	$('#admin').focus();
        }else if(!$('adminpassword').validatebox('isValid')){
        	$('adminpassword').focus();
        }
        $('#loginbtn a').click(function(){
        	 if(!$('#admin').validatebox('isValid')){
             	$('#admin').focus();
             	
             }else if(!$('#adminpassword').validatebox('isValid')){
             	$('adminpassword').focus();
             }else{
            	 $.ajax({
            		 url:"admin/checkAdmin",
            		 type:'post',
            		 data:{
            			 adminName:$('#admin').val(),
            			 password:$('#adminpassword').val()
            		 },
            		 beforeSend:function(){
            			 $.messager.progress({
            				 text:'正在尝试登录...',
            			 });
            		 },
            		 success:function(data){
            			 $.messager.progress('close');
            			 if(data.status==1){
            				 location.href='adminIndex';
            			 }else{
            				 $.messager.alert('登录失败！',data.msg,'warning',function(){
            				   $("#adminpassword").select();	 
            				 });
            			 }
            		 }
            	 });
             }
        })
	})
</script>
</head>
<body>
	<div id="login">
		<p>
			管理员账号:<input type="text" id="admin" class="textbox">
		</p>
		<p>
			管理员密码:<input type="password" id="adminpassword" class="textbox">
		</p>
	</div>
	<div id="loginbtn">
		<a href="#" class="easyui-linkbutton">登录</a>
	</div>
</body>
</html>