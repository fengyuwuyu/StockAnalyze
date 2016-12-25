<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>下跌后反弹</title>
    
    <%@include file="commons/resource.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/commons/YDataGrid.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ux/stockMain.js"></script>
  </head>
  
  <body>
    <div class="easyui-panel ui-search-panel" title="查询条件" data-options="striped: true,collapsible:true,iconCls:'icon-search'" style="width:97%;">  
 	 			<form id="searchForm" method="post">
 	 			<p class="ui-fields">	 	    		
 	 	    		<label class="ui-label">开始日期：</label>
 	 	    		<input name="begin" id="begin" type="text" class="easyui-datebox" data-options="editable:false">&nbsp;&nbsp;&nbsp;
 	 	    		<label class="ui-label">结束日期：</label>
 	 	    		<input name="end" id="end" type="text" class="easyui-datebox" data-options="editable:false">&nbsp;&nbsp;&nbsp;
 	 	    		<label class="ui-label">持续天数：</label>
 	 	    		<input type="text" class="easyui-combobox" name="remainDays" id="remainDays" data-options="valueField:'id',textField:'text',data:[{id:5,text:'5天涨幅'},{id:7,text:'7天涨幅'},{id:10,text:'10天涨幅'},{id:14,text:'14天涨幅'}],panelHeight:'auto'">
 	 	    		<label class="ui-label">最低涨幅：</label>
 	 	    		<input name="minIncrease" id="minIncrease" type="text" class="ui-text">&nbsp;&nbsp;&nbsp;
 	 	    		<label class="ui-label">最低成交量：</label>
 	 	    		<input name="minVolume" id="minVolume" type="text" class="ui-text">&nbsp;&nbsp;&nbsp;
 	 	    		<label class="ui-label">排序：</label>
 	 	    		<input name="order" id="order" type="text" class="easyui-combobox" data-options="valueField:'id',textField:'text',data:[{id:1,text:'漲幅'},{id:2,text:'成交量'}],editable:false,panelHeight:'auto'" >&nbsp;&nbsp;&nbsp;
        		</p>
        		<a id="btn-search" href="#" class="easyui-splitbutton" iconCls="icon-search" menu="#menu-reset" plain="false">查询</a>
        		<div id="menu-reset"><div id="btn-reset" iconCls="icon-reset">重置</div></div>
      			</form>  
     		</div> 
    
    <div class="easyui-panel warp"  data-options="border:false">
	    <form id="listForm" method="post">
	    	<table id="data-list"></table>
	    </form> 
	</div>	
	
		<div id="stock-win"> </div> 
  </body>
</html>
