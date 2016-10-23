package com.stock.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPool {

	private static ExecutorService service = Executors.newFixedThreadPool(20);
	
	public static void execute(Runnable task){
		service.execute(task);
	}
	
	public static Future<Object> execute(Callable<Object> task){
		return service.submit(task);
	}
}
