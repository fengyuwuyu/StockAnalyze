$package("YiYa.stockMain");

YiYa.stockMain = function(){
	_box = null;
	_this = {
		config : {
			dataGrid : {
				url : urls['msUrl']+'searcher/query.do',
				idField : 'symbol',
				pagination : false,
				columns:[[
							{field:'ck',checkbox:true},
							{field : 'symbol',title:'股票编号',align:'center',width:200},
							{field : 'nowIncrease',title:'增长比',align:'center',width:200,sortable : true},
							{field : 'type',title:'类型',align:'center',width:200,sortable : true},
							{field : 'lastIncrease',title:'累计涨幅',align:'center',width:200,sortable : true}
						]],
						onDblClickRow : function(index,row){
					var begin = $('#begin').datebox('getValue'),
					end = $('#end').datebox('getValue');
					window.open(urls['msUrl']+'view/stockChart.jsp?symbol='+row.symbol+'&begin='+begin+'&end='+end);
				}
			}
		},
		init : function(){
			_box = new YDataGrid(this.config);
			_box.init();
			$('#remainDays').combobox('setValue',5);
		}
	};
	return _this;
}();

$(function(){
	YiYa.stockMain.init();
});