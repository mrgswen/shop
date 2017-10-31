<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商城后台管理系统</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/adminIndex.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/shop-crud.js"></script>
<script type="text/javascript">
	$(function(){
		$('#tabs').tabs({
			fit : true,
			border : false,
			});
		$("#nav").tree({
			url:"admin/getNaviTree",
		    lines:true,
		    onLoadSuccess:function(node,data){
		    	if(data){
		    		var _this=this;
		    		$(data).each(function(index,value){
		    			//alert(this.state);
		    			//alert(data);
		    			
		    			if(this.state=='closed'){
		    				$(_this).tree('expandAll');
		    			}
		    		});
		    	}
		    },
		    onClick:function(node){
		    	if(node.url){
		    		if($('#tabs').tabs('exists',node.text)){
		    			$("#tabs").tabs('select',node.text);
		    		}else{
		    			var auth=parseInt($('#auth').val());
		    			if(auth!=1&&node.text=='管理员管理'){
		    				$.messager.alert('提示',"对不起,您的权限级别不够高",'warning');
		    			}else{
		    			$('#tabs').tabs('add',{
		    				title:node.text,
		    				closable:true,
		    				iconCls:node.iconCls,
		    				href:node.url,
		    			});
		    			}
		    		}
		    	}
		    }
		});
	})
	
</script>
</head>
<body class="easyui-layout">
	<div
		data-options="region:'north',title:'header',noheader:true,split:true"
		style="height: 60px; background: #CCFFFF;">
		<div class="logo">后台管理</div>
		<div class="logout">
			您好，${admin.adminName}|<a href="admin/adminLogout"> 退出</a><input id="auth" type="hidden" value=${admin.auth }/>
		</div>
	</div>
	<div
		data-options="region:'south',title:'footer',noheader:true"
		style="height: 35px; lineheight: 30px; text-align: center;padding:5px 0 0 0;">
		@2016-2017网上商城。Powerd By Eleven Gan.</div>

	<div
		data-options="region:'west',title:'导航',split:true,iconCls:'icon-world'"
		style="width: 180px; padding: 10px;">
		<ul id="nav"></ul>
	</div>
	<div data-options="region:'center'" style="overflow: hiddern;">
		<div id="tabs">
			<div title=" 起 始 页 " iconCls="icon-house"
				style="padding: 0 10px; display: block;">
				<p>欢迎来到后台管理系统！</p>
			</div>
		</div>
	</div>
</body>
</html>