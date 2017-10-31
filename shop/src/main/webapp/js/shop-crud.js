$(function(){
	crud={
	       // targetUrl:'',
			 add :function(targetForm,focusField,url) {
				$(targetForm).dialog('open').dialog('setTitle', '新增');
				$(targetForm).form('reset');
				$(focusField).focus();
				targetUrl = url;
				
			},
			edit:function(targetForm,targetTable,url) {
				var rows = $(targetTable).datagrid('getSelections');
				if (rows.length > 1) {
					$.messager.alert("警告", "编辑时只能选定一条记录", 'warning');
				} else if (rows.length == 0) {
					$.messager.alert("警告", "请至少选定一条记录来编辑", 'warning');
				} else {
					$(targetForm).dialog('open').dialog('setTitle',
							'编辑');
					alert(JSON.stringify(rows[0]));
					$(targetForm).form('load', rows[0]);
					targetUrl = url;
				}
			},
			save:function(targetForm,targetTable) {
				
				$(targetForm).form('submit', {
					url : targetUrl,
					obSubmit : function() {
						return $(this).form('validate');
					},
					success : function(data) {
						//var data = eval('(' + data + ')');//将json字符串转化成javascript对象,
						//var data=(new Function("return "+data))()
						//var data=JSON.parse(data); 
						//alert(data.status);
						//var data=JSON.stringify(data);
						//alert(data);
						var data = eval('(' + data + ')');
						if (data.status == 1) {
							$.messager.show({
								title : '提示',
								msg : data.msg,
							});
							$(targetForm).dialog('close').form('reset');
							$(targetTable).datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg, 'warning');
						}
					}
				});
			},
			reload:function(targetTable) {
				$(targetTable).datagrid('load',{
					
				});
				$("input").val("");
			},
			
			
			remove:function(targetTable,url,idField) {
				var rows = $(targetTable).datagrid('getSelections');
				if (rows.length > 0) {
					$.messager.confirm('确定', "是否要删除所选的<strong>" + rows.length
							+ "</strong>条记录吗？", function(flag) {
						if (flag) {
							var ids = [];
							var id="rows[i]."+idField;
							alert("'"+eval("("+id+")")+"'");
							for (var i = 0; i < rows.length; i++) {
								ids.push("'"+eval("("+id+")")+"'");
								
							}
							console.log(ids);
							$.ajax({
								type : 'POST',
								url : url,
								data : {
									ids : ids.join(','),
								},
								beforeSend : function() {
									$(targetTable).datagrid('loading');
								},
								success : function(data) {
									if (parseInt(data.content)>0) {
										$(targetTable).datagrid('loaded');
										$(targetTable).datagrid('reload');
										$(targetTable).datagrid('unselectAll');
										$.messager.show({
											title : '提示',
											msg :  data.msg,
										});
									}else{
										$.messager.alert(' 警 告  ',
												data.msg, 'warning');
										$(targetTable).datagrid('reload');
									}
								},
							});
						} else {
							$.messager.alert(' 警 告 操 作 ',
									'删 除 记 录 至 少 选 定 一 条 数 据 ！ ', 'warning');
						}
					})
				}
			}
			,
			search:function(params,targetTable){
				//var queryStr=$.trim($('input[name="search_manager"]').val());
				//$.messager.alert('提示', dateFrom, 'warning');
				//var params={
				//		'cname':queryStr,
				//	};
				//var targetTable
				//alert(targetTable);
				//alert(JSON.stringify(params));
				$(targetTable).datagrid('load',params);
			
			},
			extendDateBox:function(target){
				var buttons = $.extend([], $.fn.datebox.defaults.buttons);
				buttons.splice(1, 0, {
					text : '清空',//按钮文本
					handler : function(target) {
						$(target).datebox('setValue', "");//根据ID清空
						$(target).datebox('hidePanel');
					}
				});
				$(target).datebox({
					buttons : buttons,
				});
			},
			formatTimeStamp:function(datestamp) {
				//  var number=parseInt(time);
				var year = datestamp.getFullYear();
				
				var month = this.addZero(datestamp.getMonth() + 1);
				var date = this.addZero(datestamp.getDate());
				//alert(month);
				var hour = this.addZero(datestamp.getHours());
				var minute =this.addZero(datestamp.getMinutes());
				var second = this.addZero(datestamp.getUTCSeconds());
				var formatDate = year + "-" + month + "-" + date + " " + hour + ":"
						+ minute + ":" + second;
				return formatDate;
			},
			formatDate:function(date){
				var year = date.getFullYear();
				var month = this.addZero(date.getMonth() + 1);
				var date = this.addZero(date.getDate());
				var formatDate = year + "-" + month + "-" + date;
				return formatDate;
			},
			addZero:function(number) {
				if (number < 10) {
					return 0 + number.toString();
				} else {
					return number;
				}
			},
			comboboxHidePanel:function(target){
				 var _options = $(target).combobox('options');  
				    var _data = $(target).combobox('getData');/* 下拉框所有选项 */  
				    var _value = $(target).combobox('getValue');/* 用户输入的值 */  
				    var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */  
				    for (var i = 0; i < _data.length; i++) {  
				        if (_data[i][_options.valueField] == _value) {  
				            _b=true;  
				            break;  
				        }  
				    }  
				    if(!_b){  
				        $(target).combobox('setValue', '');  
				    }  
			}
		}
	
})