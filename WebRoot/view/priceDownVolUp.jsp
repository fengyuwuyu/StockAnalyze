<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>业务类型定义</title>
    
    <%@include file="commons/resource.jsp"%>  
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/commons/YDataGrid.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath }/js/ux/priceDownVolUp.js"></script>
  </head>
  <body>
	<div class="easyui-panel warp"  data-options="border:false">
	    <form id="listForm" method="post">
	    	<table id="data-list"></table>
	    </form> 
	    
	</div>	
  </body>
</html>
