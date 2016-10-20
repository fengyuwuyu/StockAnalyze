package com.stock.util;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;






	
public class TaskJob extends ApplicationObjectSupport {

    public void execute() {    	
		ApplicationContext applicationContext = this.getApplicationContext();
    }
    
    
}

