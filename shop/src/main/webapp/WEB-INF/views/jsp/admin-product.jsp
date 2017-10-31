<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table id="product_table"></table>
<div id='product_toolbar' style="padding: 5px">
	<div style="margin-bottom: 5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="product_add"
			>添加</a> <a href="#" class="easyui-linkbutton" id="product_edit"
			iconCls="icon-edit" plain="true" >编辑</a> <a
			href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="product_remove"
			>删除</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="product_reload"
			>刷新</a>
	</div>
	<div style="padding: 0 0 0 7px; color: #333;">
		上传日期从：<input type="text" id="product_date_from" editable="false"
			name="product_date_from" style="width: 110px"> 到：<input
			type="text" id="product_date_to" name="product_date_to"
			editable="false" style="width: 110px"> &nbsp;&nbsp;销售状态：<input
			type=text id="isHot" name="isHot" style="width: 60px">
			&nbsp;&nbsp;所属一级分类：<input type=text id="category"
			name="secondCategory.category.cid" style="width: 110px">
		&nbsp;&nbsp;所属二级分类：<input type=text id="second_category"
			 style="width: 110px">
	</div>
	<div style="padding: 7px 0 0 7px; color: #333;">
		<input id="product_search"></input>
		<div id="product_search_menu" style="width: 120px">
			<div data-options="name:'pName'">商品名</div>
			<div data-options="name:'productNumber'">商品编号</div>
		</div>
	</div>
	<form id="product_form" method='post'
		style="margin: 0; padding: 5px 0 0 25px;">
		<input type="hidden" name="pid" class="textbox"
			style="width: 200px;">
		<p>
			商&nbsp;品&nbsp;名&nbsp;称：<input type="text" name="pName" class="textbox"
				style="width: 200px;">
		</p>
		<p>
			市&nbsp;&nbsp;场&nbsp;&nbsp;价&nbsp;&nbsp;：<input type="text" name="marketPrice" class="textbox"
				style="width: 200px;">
		</p>
		<p>
			商&nbsp;&nbsp;城&nbsp;&nbsp;价&nbsp;&nbsp;：<input type="text" name="shopPrice" class="textbox"
				style="width: 200px;">
		</p>
		<p>
			商&nbsp;品&nbsp;介&nbsp;绍：<input type="text" name="productDescribe" class="textbox"
				style="width: 200px;">
		</p>
		<p>
			图&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;片&nbsp;&nbsp;&nbsp;：<input type="text" name="image" class="textbox"
				style="width: 200px; " value="products/"/>
		</p>
		<p>
			销&nbsp;&nbsp;售&nbsp;状&nbsp;态&nbsp;&nbsp;：<input type="text" name="isHot" class="textbox"
				style="width: 202px;">
		</p>
		<p>
			所属二级分类：<input type="text" name="categorySecond.csid" class="textbox"
				style="width: 200px;">
		</p>
		<div id="dlg">
		<img id="simg"/>
</div>
	</form>
</div>
<script type="text/javascript">
	$(function() {
		var $date_from = $('#product_date_from');
		var $date_to = $('#product_date_to');
		crud.extendDateBox($date_from);
		crud.extendDateBox($date_to);
		 $.extend($.fn.validatebox.defaults.rules, {
		intOrFloat: {// 验证整数或小数
            validator: function (value) {
            	var i=/^\d+(\.\d+)?$/;
            	//alert(i.test(value));
                return i.test(value);
            },
            message: '请输入数字，并确保格式正确'
        },
		 })
		$('#product_table').datagrid({
			url : 'admin/getProducts',
			fit : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			//fitColumns:true,
			border : false,
			pagination : true,
			pageSize : 20,
			pageList : [ 20, 30, 40, 50, 60 ],
			pageNumer : 1,
			sortName : 'createTime',
			sortOrder : 'desc',
			toolbar : '#product_toolbar',
			columns : [ [ {
				field : 'productDescribe',
				title : '商品描述',
				width : 500,
			}, {
				field : 'image',
				title : '图片',
				width : 100,
				formatter:function(value,row,index){
					var picture="";
					if(value!=''&&value!=null){
						picture="<img  style='width:90px; height:60px;margin-left:3px;' src='${ pageContext.request.contextPath }/"+value + "' title='点击查看图片'/>";
					}
					return picture;
				}
			}, {
				field : 'isHot',
				title : '销售状态',
				width : 40,
				formatter : function(value, row, index) {
					if (value == 1) {
						return "热销";
					} else if (value == 0) {
						return "一般";
					}
				}
			}, {
				field : 'createdTime',
				title : '上传时间',
				width : 100,
				formatter : function(value, row, index) {
					var datestamp = new Date(value);
					//alert(JSON.stringify(datestamp));
					//alert(value);

					return crud.formatTimeStamp(datestamp);
				}
			}, {
				field : 'categorySecond',
				title : '所属二级分类',
				width : 70,
				formatter : function(value, row, index) {
					return value.csName;
				}
			}, ] ],
			frozenColumns : [ [ {
				field : 'pid',
				title : 'pid',
				width : 50,
			    checkbox : true
			},{
				field : 'productNumber',
				title : '商品编号',
				width :100,
			  
			}, {
				field : 'pName',
				title : '商品名',
				width : 120
			}, {
				field : 'marketPrice',
				title : '市场价(元)',
				width : 60
			}, {
				field : 'shopPrice',
				title : '商城价(元)',
				width : 60,
			}, ] ],
			onClickRow:function(rowIndex,rowData){
				//console.log(rowData);
			}

		});
		$('input[name="isHot"]').combobox({
			panelHeight : '50px',
			multiple : false,
			valueField : 'value',
			textField : 'label',
			editable : false,
			data : [ {
				label : "热销",
				value : "1"
			}, {
				label : "一般",
				value : "0"
			} ]
		});
		var $target_form=$('#product_form');
		var $target_table=$('#product_table');
		var $focus_field=$("input[name='pName']");
		var add_url="admin/addProduct";
		$('#product_form').dialog({
			iconCls : 'icon-product',
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
					$('#manager_add').dialog('close').form('reset');
				}
			},

			]
		});
		
		$('input[name="secondCategory.category.cid"]').combobox({
			url : 'admin/getAllCategorys',
			multiple : false,
			valueField : 'cid',
			textField : 'cname',
			panelHeight:'auto',
			onSelect:function(record){
			//	alert(JSON.stringify(category));
				var url2='admin/getSecondCategorysWithCid?cid='+record.cid;
				$('#second_category').combobox('reload',url2);
				console.log(url2);
			},
			filter : function(q, row) {
				var opts = $(this).combobox('options');
				return row[opts.textField].indexOf(q) >= 0;
			},
			onHidePanel : function() {
				crud.comboboxHidePanel($(this));
			},
			
		});
		$('input[name="categorySecond.csid"]').combobox({
			url : 'admin/getAllSecondCategorys',
			required : true,
			multiple : false,
			valueField : 'csid',
			textField : 'csName',
			//panelHeight:'auto',
			filter : function(q, row) {
				var opts = $(this).combobox('options');
				return row[opts.textField].indexOf(q) >= 0;
			},
			onHidePanel : function() {
				crud.comboboxHidePanel($(this));
			},
		});
		$('#second_category').combobox({
			url : 'admin/getAllSecondCategorys',
			multiple : false,
			valueField : 'csid',
			textField : 'csName',
			//panelHeight:'auto',
			filter : function(q, row) {
				var opts = $(this).combobox('options');
				return row[opts.textField].indexOf(q) >= 0;
			},
			onHidePanel : function() {
				crud.comboboxHidePanel($(this));
			},
		});
		$('input[name="pName"]').validatebox({
			required:true,
		})
		$('input[name="shopPrice"]').validatebox({
			required:true,
			validType:'intOrFloat',
		})

		var $targetTable = $('#product_table');
		$('#product_search')
				.searchbox(
						{
							searcher : function(value, name) {
								var queryStr = $.trim(value);
								var dateFrom = $(
										'input[name="product_date_from"]')
										.val();
								var dateTo = $('input[name="product_date_to"]')
										.val();
								var isHot = $('input[name="isHot"]').val();
								var csid = $(
										'input[name="secondCategory.csid"]')
										.val();
								var cid = $(
										'input[name="secondCategory.category.cid"]')
										.val();
								if (queryStr == '' && dateFrom == ''
										&& dateTo == '' && isHot == ''
										&& csid == '' && cid == '') {
									$.messager.alert('提示', "请至少选择一个查询条件",
											'warning');
									return false;
								}
								if ((dateFrom == '' && dateTo != '')
										|| (dateFrom != '' && dateTo == '')) {
									$.messager.alert('提示', "请输入完整的日期区间",
											'warning');
									return false;
								}
								var params = {
									'queryStr' : queryStr,
									'field' : name,
									'dateFrom' : dateFrom,
									'dateTo' : dateTo,
									'isHot' : isHot,
									'csid' : csid,
									'cid' : cid
								};
								alert(JSON.stringify(params));
								$("#product_table").datagrid('load', params);
							},
							menu : '#product_search_menu',
							prompt : '请输入查询词',
							width : '300px'
						});

		$("#product_remove").click(function() {

			var url = 'admin/deleteProduct';
			var idField = 'pid';
			crud.remove($targetTable, url, idField);
		});
		$("#product_reload").click(function() {
			crud.reload($targetTable);
		})
        $('#product_add').click(function(){
        	crud.add($target_form,$target_table,add_url);
        });
		var edit_url="admin/updateProduct";
		$('#product_edit').click(function(){
        	product.edit($target_form,$target_table,edit_url);
        });
		$('#product_save').click(function(){
        	crud.save($target_form,$target_table);
        });
		
		product={
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
							"pid":rows[0].pid,
							"pName":rows[0].pName,
							"marketPrice":rows[0].marketPrice,
							"shopPrice":rows[0].shopPrice,
							"productDescribe":rows[0].productDescribe,
							"image":rows[0].image,
							"isHot":rows[0].isHot,
							"categorySecond.csid":rows[0].categorySecond.csid,
							
						});
						targetUrl = url;
					}
				}
			}
	})
	function download(img){  
	        var simg =  "${ pageContext.request.contextPath }/"+ img;  
	        $('#dlg').dialog({  
	            title: '预览',  
	            width: 600,  
	            height:750,  
	            resizable:true,  
	            closed: false,  
	            cache: false,  
	            modal: true  
	        });  
	        $("#simg").attr("src",simg);
		} 
</script>
