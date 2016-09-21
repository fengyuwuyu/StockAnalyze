$package("YiYa.stockMain");

YiYa.stockMain = function(){
	_box = null;
	_this = {
		config : {
			dataGrid : {
				url : urls['msUrl']+'stockMain/dataList.do',
				idField : 'symbol',
				columns:[[
							{field:'ck',checkbox:true},
							{field : 'symbol',title:'股票编号',align:'center',width:200},
							{field : 'increase',title:'增长比',align:'center',width:200}
						]],
				onDblClickRow : function(index,row){
					var symbol = row.symbol;
					YiYa.ajaxJson(urls['msUrl']+"stockMain/showChart.do", {'symbol':symbol}, function(data){
						if(data.data){
							
						}
					});
				}
			}
		},
		initDateBox : function(){
			$('#begin').datebox('setValue','2000-01-05');
			$('#end').datebox('setValue','2000-01-20');
		},
		init : function(){
			this.initDateBox();
			console.log($('#begin').datebox('getValue'))
			_box = new YDataGrid(this.config);
			_box.init();
		}
	};
	return _this;
}();

$(function(){
	YiYa.stockMain.init();
});