package com.gxlu.interfacePlatform.schedule;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProgressThread extends Thread{
	private Log log = LogFactory.getLog(getClass());
	private boolean stop =false;
	private long lastSpentTime;
	
	private Date startTime = new Date();
	
	public ProgressThread(Schedule schedule){
		super();
		log.info(String.format("start progress for [%s]",schedule.getHandlerClassName()));
		
		if(schedule.getLastRunTime()!=null&&schedule.getLastFinishTime()!=null){
			lastSpentTime =schedule.getLastFinishTime().getTime()-schedule.getLastRunTime().getTime();
		}
	}
	
	public synchronized void forceStop(){
		this.stop=true;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(lastSpentTime<=0){
			log.info(String.format("lastSpentTime is zero. progress stop."));
			return;
		}
		
		while(!stop){
			Date currentTime = new Date();
			long spentTime = currentTime.getTime()-startTime.getTime();
			
			long percent=spentTime*100/lastSpentTime;
			
			if(percent>100){
				percent=99;
			}
			
			changeProgress(percent);
			
			wait(1);
		}
		
	}
	
	public void changeProgress(long percent){
		
	}
	
	private void wait(int second){
		try {
			Thread.sleep(1000*second);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
