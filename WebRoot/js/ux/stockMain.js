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
					var config = YiYa.stockMain.getQueryTime();
					$('#stock-win').window({
						title : '个股详情',
						width : 1200,
						height : 500,
						href : urls['msUrl']+'view/stockChart.jsp?symbol='+row.symbol+'&begin='+config.begin+'&end='+config.end,
						draggable : false,
						minimizable : false,
						maximizable : false,
						closable : true,
						modal : true
					});
				}
			}
		},
		initDateBox : function(){
//			$('#begin').datebox('setValue','2000-01-05');
//			$('#end').datebox('setValue','2000-02-26');
			$('#type').combobox('setValue',1);
		},
		getQueryTime : function(){
			var begin = $('#begin').datebox('getValue');
			var end = $('#end').datebox('getValue');
			return {"begin" : begin,"end":end};
		},
		checkTime : function(){
			var time = this.getQueryTime();
			if(!time.begin||!begin.end){
				return false;
			}
			return true;
		},
		init : function(){
//			$('#stock-win').window('close');
			this.initDateBox();
			_box = new YDataGrid(this.config);
			_box.init();
		}
	};
	return _this;
}();

$(function(){
	YiYa.stockMain.init();
});