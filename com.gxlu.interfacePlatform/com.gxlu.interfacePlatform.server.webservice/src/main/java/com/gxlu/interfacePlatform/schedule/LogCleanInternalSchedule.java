package com.gxlu.interfacePlatform.schedule;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

public class LogCleanInternalSchedule extends InternalSchedule{
	
	public LogCleanInternalSchedule() {
		// TODO Auto-generated constructor stub
		this.setTrigger(getLogTrigger());
		this.setClazz(LogJob.class);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Interval:Log cleanning plug-in.";
	}

	private Trigger getLogTrigger(){
		SimpleTrigger trigger = new SimpleTrigger();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		trigger.setStartTime(calendar.getTime());
		return trigger;
	}
	
	public static class LogJob implements Job{
		private final String[] logFileNames = {"console.log","interfacePlatform.log"};
		private final int keepDays = 3;
		
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			// TODO Auto-generated method stub
			File current = new File(".");
			File[] targetList =current.listFiles(new FilenameFilter() {
				
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					Date current = new Date();
					for(String logFileName : logFileNames){
						if(name.startsWith(logFileName)){
							long second = (current.getTime() - dir.lastModified())/1000;
							if(second > keepDays *24*60*60){
								return true;
							}
						}
						
					}
					return false;
				}
			});
			
			if(targetList!=null && targetList.length>0)
				for(File target : targetList){
					target.delete();
				}
		}
	}
	
}
