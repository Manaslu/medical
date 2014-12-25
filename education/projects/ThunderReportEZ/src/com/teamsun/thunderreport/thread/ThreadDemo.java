package com.teamsun.thunderreport.thread;


public class ThreadDemo {

	public static void main(String[] args) {
		ThreadResourcePool pool = new ThreadResourcePool(5,5);
		/*
		for (int i = 0; i < 20; i++) {
			pool.doProcess(null, new ProcessCallBack(){

				public void doProcess(Object param) throws Exception {
					
					Thread.sleep(500);
					System.out.println("睡眠完成");
					System.out.println(Thread.currentThread().getId());
					
				}
				
			});
			
		}
		*/			
		
		//pool.shutdownNow();
	}

}
