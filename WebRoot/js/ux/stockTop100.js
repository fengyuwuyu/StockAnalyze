$package("YiYa.stockMain");

YiYa.stockMain = function(){
	_box = null;
	_this = {
		config : {
			dataGrid : {
				url : urls['msUrl']+'stockMain/analyseQuery.do',
				idField : 'symbol',
				columns:[[
							{field:'ck',checkbox:true},
							{field : 'symbol',title:'股票编号',align:'center',width:50},
							{field : 'incre',title:'增长比',align:'center',width:50},
							{field : 'increases',title:'增长比',align:'center',width:200,formatter:function(value,row,index){
								var increases = row.increases;
								var increase = row.incre;
								var result = '';
								for(var i=0;i<increases.length;i++){
									if(increase==increases[i]){
										result = result+'<span style="color:red;">'+increases[i]+'</span>';
									}else{
										result = result+increases[i];
									}
									result+=', ';
								}
								
								return result.substring(0, result.length-2);
							}}
						]],
						onDblClickRow : function(index,row){
							var begin = $('#begin').datebox('getValue'),
							end = $('#end').datebox('getValue');
							window.open(urls['msUrl']+'view/stockChart.jsp?symbol='+row.symbol+'&begin='+begin+'&end='+end);
						},
						onLoadSuccess : function(data){
							if(data&&data.days){
//								$('#begin').datebox('setValue',data.days.begin);
								$('#end').datebox('setValue',data.days.end);
							}
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
	YiYa.stockMain.init();
});