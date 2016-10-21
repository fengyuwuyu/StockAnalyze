package com.stock.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.stock.task.TaskJob;
import com.stock.util.ThreadPool;

/**
 * 系统启动后加载配置文件：stock_interface.properties
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
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
		final TaskJob job = (TaskJob) ac.getBean("taskJob");
		if(job!=null){
			ThreadPool.execute(new Runnable() {
				
				@Override
				public void run() {
					job.execute();
					log.info("TaskJob启动了！");
				}
			});
		}
		
		
//		Properties properties = new Properties();
//		try {
//			properties.load(this.getClass().getResourceAsStream("/stock_interface.properties"));
//			Configuration.HUS_A = properties.getProperty("hus_a");
//			Configuration.HUS_A_PAGE = Integer.valueOf(properties.getProperty("hus_a_page"));
//			Configuration.HUS_A_TYPE = properties.getProperty("hus_a_type");
//			Configuration.HUS_B = properties.getProperty("hus_b");
//			Configuration.HUS_B_PAGE = Integer.valueOf(properties.getProperty("hus_b_page"));
//			Configuration.HUS_B_TYPE = properties.getProperty("hus_b_type");
//			Configuration.CYB = properties.getProperty("cyb");
//			Configuration.CYB_PAGE = Integer.valueOf(properties.getProperty("cyb_page"));
//			Configuration.CYB_TYPE = properties.getProperty("cyb_type");
//			Configuration.ZXB = properties.getProperty("zxb");
//			Configuration.ZXB_PAGE = Integer.valueOf(properties.getProperty("zxb_page"));
//			Configuration.ZXB_TYPE = properties.getProperty("zxb_type");
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.error(CommonsUtil.join(e.getStackTrace(), ",\n\r"));
//		}
		
	}

}
