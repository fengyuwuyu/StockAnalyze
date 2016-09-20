<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>业务类型定义</title>
    
    <%@include file="commons/resource.jsp"%>  
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/commons/YDataGrid.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath }/js/ux/maxDown.js"></script>
  </head>
  <body>
 	 <div class="easyui-panel ui-search-panel" title="查询条件" data-options="striped: true,collapsible:true,iconCls:'icon-search'" style="width:97%;"> 
 	 	<form id="searchForm" method="post">
 	 	<p class="ui-fields">
        	<label class="ui-label">最高天数：</label>
        	<input name="maxDay" type="text" class="ui-text">&nbsp;&nbsp;&nbsp; 
       	</p>
       	<p class="ui-fields">
        	<label class="ui-label">最低天数：</label>
        	<input name="minDay" type="text" class="ui-text">&nbsp;&nbsp;&nbsp; 
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
  </body>
</html>
