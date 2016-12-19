package com.stock.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.stock.dao.StockMainMapper;
import com.stock.util.StockCache;

/**
 * 系统启动后加载配置文件：stock_interface.properties
 * 
 * @author lilei
 * 
 */
@WebListener
public class ContextListener implements ServletContextListener {

	private Logger log = Logger.getLogger(ContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext context = arg0.getServletContext();
		ApplicationContext ac = WebApplicationContextUtils
				.getWebApplicationContext(context);
		//初始化StockCache.setSqlSessionFactory
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ac.getBean("sqlSessionFactory");
		StockCache.setSqlSessionFactory(sqlSessionFactory);
		
		StockMainMapper stockMainMapper = ac.getBean(StockMainMapper.class);
		StockCache.initPrePrices(stockMainMapper);
		log.info("contextListener启动了");
	}

}
