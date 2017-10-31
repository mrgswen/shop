<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table id="managerTable"></table>
<div id='manager_toolbar' style="padding: 5px">
	<div style="margin-bottom: 5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="manager.add()">添加</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="manager.edit()">编辑</a> <a
			href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
			onclick="manager.remove()">删除</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-reload" plain="true"
			onclick="manager.reload()">刷新</a>
	</div>
	<div style="padding: 0 0 0 7px; color: #333;">
		查询管理员：<input type="text" class="textbox"  name="search_manager"
			style="width: 110px"> 创建时间从：<input type="text" id="date_from" editable="false"
			name="date_from"  	style="width: 110px"> 到：<input type="text" id="date_to" name="date_to"
			 editable="false"  style="width: 110px">
		<a href="#" class="easyui-linkbutton"  id="manager_search" iconCls="icon-search"
			onclick="manager.search();">查询</a>
	</div>

	<form id="manager_form" method='post'
		style="margin: 0; padding: 5px 0 0 25px;">
		<input type="hidden" name="adminId" class="textbox"
			style="width: 200px;">
		<p>
			管理员账号：<input type="text" name="adminName"  class="textbox" 
				style="width: 200px;">
		</p>
		<p>
			管理员密码：<input type="text" name="password" class="textbox"
				style="width: 200px;">
		</p>
		<p>
			管理员权限：<input type="text" name="auth" class="textbox"
				style="width: 200px;">
		</p>

	</form>

</div>
<script type="text/javascript">
   
	$(function() {
		var status="";
		/*$.extend($.fn.validatebox.defaults.rules,{
			isExist:{
				validator:function(value,param){
					var rules = $.fn.validatebox.defaults.rules;
					//rules.length.message="管理员账号已存在，请重新输入";
					//rules.length.message = "账号长度为2-6位";
					//if(!rules.length.validator(value)){  
		            //   rules.length.message = "账号长度为2-6位";  
		             ///   return false;  
		          // }  
					//alert(value);
					var str=value.toString();
					if(str.length<2||str.length>10){
						rules.isExist.message="账号长度为2-10位";
						return false;
					}
					
					$.ajax({
					    url:"admin/isAdminExist?adminName="+value,
					    async:false,
					    success:function(data){
					    	status=data.status;
					    	//console.log("neibu   "+status);
					    	
					    }
					});
		          // console.log("waibu"+status);
					if(status=="1"){
						rules.isExist.message="管理员账号已存在，请重新输入";
			        	   return false;
			           }else{ 
			        	   return true;
			           }
		           
				},	
			   message:"管理员账号已存在，请重新输入"}
		
		});*/
		var buttons = $.extend([], $.fn.datebox.defaults.buttons);
		buttons.splice(1, 0,
		{
		    text: '清空',//按钮文本
		    handler: function (target) {
		        $(target).datebox('setValue', "");//根据ID清空
		        $(target).datebox('hidePanel');
		    }
		});
		//var dateFrom=$('#date_from').datebox('getValue');
		//alert(dateFrom);
		$('#date_to').datebox({
			buttons: buttons,
			//validType:"equaldDate[dateFrom]",
		});
		$('#date_from').datebox({
			buttons: buttons,
		});
		$('#managerTable').datagrid({
			url : 'admin/getAdmins',
			fit : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			border : false,
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			pageNumer : 1,
			sortName : 'createTime',
			sortOrder : 'desc',
			toolbar : '#manager_toolbar',
			columns : [ [ {
				field : 'adminId',
				title : 'id',
				width : 100,
				checkbox : true
			}, {
				field : 'adminName',
				title : '账号',
				width : 100
			}, {
				field : 'password',
				title : 'password',
				hidden : true,
				width : 100
			}, {
				field : 'auth',
				title : '权限级别',
				width : 100,
				formatter : function(value, row, index) {
					if (value == 1) {
						return "一级管理员";
					} else if (value == 2) {
						return "二级管理员";
					}
				}
			}, {
				field : 'createdTime',
				title : '创建时间',
				width : 100,
				formatter : function(value, row, index) {
					var datestamp = new Date(value);

					return format(datestamp);
				}
			}

			] ]
		});

		$('#manager_form').dialog({
			iconCls : 'icon-manager',
			width : 350,
			modal : true,
			closed : true,
			buttons : [ {
				text : '提交',
				iconCls : 'icon-add-new',
				handler : function() {
					manager.save()
				},

			}, {
				text : '取消',
				iconCls : 'icon-redo',
				handler : function() {
					$('#manager_add').dialog('close').form('reset');
				}
			},

			]
		});

		$('input[name="adminName"]').validatebox({
			required : true,
			validType : 'length[2,6]',
			missingMessage : '请输入管理名称',
			invalidMessage:"管理员账户名长度为2-6位"
			
			
		});
		//管理密码
		$('input[name="password"]').validatebox({
			required : true,
			validType : 'length[6,10]',
			missingMessage : '请输入管理密码',
			invalidMessage : '管理密码在 6-10 位',
		});

		$('input[name="auth"]').combobox({
			valueField : 'value',
			textField : 'label',
			panelHeight : 60,
			editable : false,
			multiple : false,
			value : '2',
			data : [ {
				label : '一级管理员',
				value : '1'
			}, {
				label : '二级管理员',
				value : '2'
			} ],
		});

		manager = {
			add : function() {
				$('#manager_form').dialog('open').dialog('setTitle', '新增管理员');
				$('#manager_form').form('reset');
				$('input[name="adminName"]').focus();
				url = 'admin/addAdmin';
			},
			edit : function() {
				var rows = $('#managerTable').datagrid('getSelections');
				$('input[name="adminName"]').attr('readonly', 'readonly');
				if (rows.length > 1) {
					$.messager.alert("警告", "编辑时只能选定一条记录", 'warning');
				} else if (rows.length == 0) {
					$.messager.alert("警告", "请至少选定一条记录来编辑", 'warning');
				} else {
					$('#manager_form').dialog('open').dialog('setTitle',
							'编辑管理员');
					$('#manager_form').form('load', rows[0]);
					url = "admin/updateAdmin";
				}
			},
			reload : function() {
				$('#managerTable').datagrid('load',{
					
				});
			},
			remove : function() {
				var rows = $("#managerTable").datagrid('getSelections');
				if(rows.length == 0 ){
					$.messager.alert(' 警 告 操 作 ',
							'请至 少 选 定 一 条 记录！ ', 'warning');
				//	console.log(1);
				}else if (rows.length > 0) {
					$.messager.confirm('确定', "是否要删除所选的<strong>" + rows.length
							+ "</strong>条记录吗？", function(flag) {
						if (flag) {
							var ids = [];
							for (var i = 0; i < rows.length; i++) {
								ids.push(rows[i].adminId);
							}
							$.ajax({
								type : 'POST',
								url : 'admin/deleteAdmins',
								data : {
									ids : ids.join(','),
								},
								beforeSend : function() {
									$('#managerTable').datagrid('loading');
								},
								success : function(data) {
									if (parseInt(data.content)>0) {
										$('#managerTable').datagrid('loaded');
										$('#managerTable').datagrid('reload');
										$('#managerTable').datagrid('unselectAll');
										$.messager.show({
											title : '提示',
											msg :  data.msg,
										});
									}else{
										$.messager.alert(' 警 告  ',
												data.msg, 'warning');
									}
								},
							});
						} 
					})
				}
			},
			save : function() {
				$('#manager_form').form('submit', {
					url : url,
					obSubmit : function() {
						return $(this).form('validate');
					},
					success : function(data) {
						//alert(data);
						//var data=data +'';
						var data = eval('(' + data + ')');//将json字符串转化成javascript对象,
						
						if (data.status == 1) {
							$.messager.show({
								title : '提示',
								msg : data.msg,
							});
							$('#manager_form').dialog('close').form('reset');
							$('#managerTable').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg, 'warning');
						}
					}
				});
			},
			search:function(){
				var queryStr=$.trim($('input[name="search_manager"]').val());
				var dateFrom=$('input[name="date_from"]').val();
				var dateTo=$('input[name="date_to"]').val();
				if(queryStr==''&&dateFrom==''&&dateTo==''){
					$.messager.alert('提示', "请至少选择一个查询条件", 'warning');
					return false;
				}
				//$.messager.alert('提示', dateFrom, 'warning');
				var params={
						'adminName':queryStr,
						'dateFrom':dateFrom,
						'dateTo':dateTo
					};
				//$.messager.alert('提示', "dateFrom "+dateFrom, 'warning');
				//$.messager.alert('提示', "dateTo"+dateTo, 'warning');
				/*if(dateFrom==''&&dateTo==''){
					params={'adminName':queryStr};
				}else if(dateFrom==''&&dateTo!=''){
					parmas={
							'adminName':queryStr,
							'dateTo':dateTo
					}
				}
				else if(dateFrom!=''&&dateTo==''){
					 params={
							'adminName':queryStr,
							'dateFrom':dateFrom
					};
				}else {
					params={
							'adminName':queryStr,
							'dateFrom':dateFrom,
							'dateTo':dateTo
						};*/
					//$.messager.alert('提示', JSON.stringify(params), 'warning');
				//}
				
				//$.messager.alert('提示', "parmas " +JSON.stringify(params), 'warning');
				$('#managerTable').datagrid('load',params);
			}

		}
		function format(datestamp) {
			//  var number=parseInt(time);
			var year = datestamp.getFullYear();
			var month = addZero(datestamp.getMonth()+1);
			var date = addZero(datestamp.getDate());
			var hour = addZero(datestamp.getHours());
			var minute = addZero(datestamp.getMinutes());
			var second = addZero(datestamp.getUTCSeconds());
			var formatDate = year + "-" + month + "-" + date + " " + hour + ":"
					+ minute + ":" + second;
			return formatDate;
		}
		function addZero(number) {
			if (number < 10) {
				return 0 + number.toString();
			} else {
				return number;
			}
		}
		
	})
</script>
