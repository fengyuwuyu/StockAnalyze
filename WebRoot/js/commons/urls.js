/*封装变量（利用JSONP）*/
var urls= {
	'msUrl':'http://localhost:8999/StockAnalyze/'
};

$.ajaxSetup({   
    contentType:"application/x-www-form-urlencoded;charset=utf-8",   
    complete:function(XMLHttpRequest,textStatus){ 
      var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus，  
          if(sessionstatus=="timeout"){ 
              YiYa.alert("登录超时,请重新登录！");
          //如果超时就处理 ，指定要跳转的页面  
            window.location.replace(urls['msUrl'] + "login.jsp");   
          }
//          else if()   
       }   
  });