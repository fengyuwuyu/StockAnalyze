<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>k线图</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/commons/jquery-1.11.0-min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/commons/highstock.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/commons/urls.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/ux/stockChart.js"></script>
  </head>
  
  <body>
  
  	<div id="container"></div>
  </body>
</html>
