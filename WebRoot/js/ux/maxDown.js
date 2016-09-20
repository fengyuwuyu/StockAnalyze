/*声明命名空间*/
$package('YiYa.stockVol');

/*封装变量（利用匿名自执行函数）*/
YiYa.stockVol = function(){
	var _box = null;
	var _this = {
		config:{
  			dataGrid:{
	   			url:urls['msUrl'] + 'stock/maxDown.do',
	   			idField:'id',
	   			pageSize : 100,
	   			columns:[[
					{field:'ck',checkbox:true},
					{field:'symbol',title:'股票编号',formatter:function(value){
//						http://quotes.money.163.com/1000002.html
						var url = "http://quotes.money.163.com/"+value+".html";
						return "<a href=\'"+url+"\' >"+value+"</a>";
					}},
					{field:'sname',title:'股票名称',align:'center',width:120,sortable:true},
					{field:'maxPrice',title:'最高价格',align:'center',width:120},	
					{field:'minPrice',title:'最低价格',align:'center',width:120},	
					{field:'down',title:'跌幅',align:'center',width:120},	
				]]
			}
		},
		init:function(){
			_box = new YDataGrid(this.config); 
			_box.init();
		}
	};
	return _this;
}();

/*页面初始化*/
$(function(){
	YiYa.stockVol.init();
});		