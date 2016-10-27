package com.stock.daemon;

/**
 * 守护线程，当需要守护的线程挂掉时，会将其重新启动
 * @author lilei
 *
 */
/**
 * @author lilei
 *
 */
public class DaemonThread extends Thread {
	
	private Thread daemon;
	private DaemonJobI daemonJobI;
	private long sleep;
	private boolean cancle = false;
	
	
	public DaemonThread() {}

	/**
	 * 
	 * @param thread 需要守护的线程实例
	 * @param obj	DaemonJobI的实例对象，会根据obj重新启动一个线程
	 * @param sleep 守护线程检查一次的时间
	 * @return
	 */
	public DaemonThread(Thread daemon, DaemonJobI daemonJobI, long sleep) {
		this.daemon = daemon;
		this.daemonJobI = daemonJobI;
		this.sleep = sleep;
	}
	
	@Override
	public void run() {
		while(!cancle){
			if(daemon!=null&&daemon.isAlive()){
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
					new DaemonThread(daemon, daemonJobI, sleep).start();
				}
			}else{
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						daemonJobI.execute();
					}
				});
			}
		}
	}

	public boolean isCancle() {
		return cancle;
	}

	public void setCancle(boolean cancle) {
		this.cancle = cancle;
	}
	
}
