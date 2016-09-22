package com.stock.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 处理jsp请求，防止用户直接输入jsp地址访问jsp页面 处理考勤及食堂中个人查询统计
 * 
 * @author lilei
 * 
 */
public class JspParamFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 将jsp请求中参数包含personal的添加到attribute中
		HttpServletRequest request2 = (HttpServletRequest) request;
		String symbol = request.getParameter("symbol");
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		if (symbol != null) {
			request2.setAttribute("symbol", symbol);
		}
		if (begin != null) {
			request2.setAttribute("begin", begin);
		}
		if (end != null) {
			request2.setAttribute("end", end);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
	}

}
