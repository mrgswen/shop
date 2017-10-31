<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table id="second_category_table"></table>
<div id='second_category_toolbar' style="padding: 5px">
	<div style="margin-bottom: 5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="second_category_add"
			>添加</a> <a href="#" class="easyui-linkbutton" id="second_category_edit"
			iconCls="icon-edit" plain="true" >编辑</a> <a
			href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="second_category_remove"
			>删除</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="second_category_reload"
			>刷新</a>
	</div>
	<div style="padding: 0 0 0 7px; color: #333;">
		查询二级分类：<input type="text" class="textbox"  name="search_second__category"
			style="width:220px">
		<a href="#" class="easyui-linkbutton"  id="second_category_search" iconCls="icon-search"
			>查询</a>
	</div>

	<form id="second_category_form" method='post'
		style="margin: 0; padding: 5px 0 0 25px;">
		
		<input type="hidden" name="csid" class="textbox"
			style="width: 200px;">
		<p>
			分&nbsp;&nbsp;类&nbsp;名&nbsp;称：<input type="text" name="csName" class="textbox"
				style="width: 200px;">
		</p>
		<p>
			所属一级分类：<input type="text" name="category.cid" class="textbox"
				style="width: 209px;">
		</p>
	</form>

</div>
<script type="text/javascript">

	$(function() {
		
		
		$('#second_category_table').datagrid({
			url : 'admin/getSecondCategorys',
			fit : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			border : false,
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			pageNumer : 1,
			toolbar : '#second_category_toolbar',
			columns : [ [ {
				field : 'csid',
				title : 'id',
				width : 100,
				checkbox : true
			}, {
				field : 'csName',
				title : '分类名称',
				width : 100
			}, 
			{
				field : 'category',
				title : '所属一级分类',
				width : 100,
				formatter:function(value, row, index){
					//alert(value);
					return value.cname;
				}
			}, 

			] ]
		});

		$('#second_category_form').dialog({
			iconCls : 'icon-category',
			width : 350,
			modal : true,
			closed : true,
			buttons : [ {
				text : '提交',
				iconCls : 'icon-add-new',
				handler : function() {
					crud.save($target_form,$target_table)
				},

			}, {
				text : '取消',
				iconCls : 'icon-redo',
				handler : function() {
					$('#second_category_form').dialog('close').form('reset');
				}
			},

			]
		});

		$('input[name="csName"]').validatebox({
			required : true,
			validType : 'length[2,10]',
			missingMessage : '请输入分类名',
			invalidMessage : '分类名称长度为 2-10 位',
		});
			
		$('input[name="category.cid"]').combobox({
				url:'admin/getAllCategorys',
				required : true,
				multiple : false,
				valueField:'cid',
				textField:'cname',
				filter: function(q, row){
					var opts = $(this).combobox('options');
					return row[opts.textField].indexOf(q) >= 0;
				},
				onHidePanel : function() {  
				    var _options = $(this).combobox('options');  
				    var _data = $(this).combobox('getData');/* 下拉框所有选项 */  
				    var _value = $(this).combobox('getValue');/* 用户输入的值 */  
				    var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */  
				    for (var i = 0; i < _data.length; i++) {  
				        if (_data[i][_options.valueField] == _value) {  
				            _b=true;  
				            break;  
				        }  
				    }  
				    if(!_b){  
				        $(this).combobox('setValue', '');  
				    }  
				}, 
			});
		
		
		var $target_form=$('#second_category_form')
		var $target_table=$("#second_category_table");
		var $focus_field='input[name="csName"]';
		var add_url='admin/addSecondCategory';
		var edit_url='admin/updateSecondCategory';
		var remove_url='admin/deleteSecondCategory';
		$('#second_category_add').click(function(){
			crud.add($target_form,$focus_field,add_url)
			});
		$("#second_category_edit").click(function(){
			second_category.edit($target_form,$target_table,edit_url)
		});
		$("#second_category_remove").click(function(){
			crud.remove($target_table,remove_url,'csid');
		});
		
		$("#second_category_search").click(function(){
			var queryStr=$.trim($('input[name="search_second__category"]').val());
			var params={
					'csName':queryStr,
				};
			if(queryStr==''){
				$.messager.alert('提示', "请输入查询条件", 'warning');
				return false;
			}
			crud.search(queryStr,params,$target_table);
		});
		$("#second_category_reload").click(function(){
			crud.reload($target_table);
		});
		
		second_category={
			edit:function(targetForm,targetTable,url) {
				var rows = $(targetTable).datagrid('getSelections');
				if (rows.length > 1) {
					$.messager.alert("警告", "编辑时只能选定一条记录", 'warning');
				} else if (rows.length == 0) {
					$.messager.alert("警告", "请至少选定一条记录来编辑", 'warning');
				} else {
					$(targetForm).dialog('open').dialog('setTitle',
							'编辑');
					//alert(JSON.stringify(rows[0]));
					$(targetForm).form('load', {
						"csid":rows[0].csid,
						"csName":rows[0].csName,
						"category.cid":rows[0].category.cid
						
					});
					targetUrl = url;
				}
			},
		}
	})
</script>

