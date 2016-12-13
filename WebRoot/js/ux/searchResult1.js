$package("YiYa.searcher");

YiYa.searcher = function(){
	_box = null;
	_this = {
		config : {
			dataGrid : {
				url : urls['msUrl']+'searcher/find.do',
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
//					var config = YiYa.searcher.getQueryTime();
					var begin = $('#begin').datebox('getValue'),
					end = $('#end').datebox('getValue');
					window.open(urls['msUrl']+'view/stockChart.jsp?symbol='+row.symbol+'&begin='+begin+'&end='+end);
//					$('#stock-win').window({
//						title : '个股详情',
//						width : 1200,
//						height : 500,
//						href : urls['msUrl']+'view/stockChart.jsp?symbol='+row.symbol+'&begin='+begin+'&end='+end,
//						draggable : false,
//						minimizable : false,
//						maximizable : false,
//						closable : true,
//						modal : true
//					});
//					$('#stock-win').window('show');
				}
			}
		},
		initDateBox : function(){
			$('#begin').datebox('setValue','2000-01-05');
			$('#end').datebox('setValue','2000-02-26');
		},
		getQueryTime : function(){
			return {"begin" : $('#begin').datebox('getValue'),"end":$('#end').datebox('getValue')};
		},
		checkTime : function(){
			var time = this.getQueryTime();
			if(!time.begin||!begin.end){
				return false;
			}
			return true;
		},
		init : function(){
			_box = new YDataGrid(this.config);
			_box.init();
			$('#begin').datebox('setValue','2000-01-28');
		}
	};
	return _this;
}();

$(function(){
	YiYa.searcher.init();
});