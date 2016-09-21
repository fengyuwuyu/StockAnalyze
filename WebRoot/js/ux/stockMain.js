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
					config.symbol = row.symbol;
					$('#edit-win').window('open');
					$('#stockPanel').panel({'url':urls['msUrl']+''});
					
					
				}
			}
		},
		initDateBox : function(){
			$('#begin').datebox('setValue','2000-01-05');
			$('#end').datebox('setValue','2000-01-20');
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