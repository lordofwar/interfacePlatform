package com.gxlu.interfacePlatform.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

public class TestQuartz {
	public static void main(String[] args) throws SchedulerException {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler= sf.getScheduler();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		long startTime = System.currentTimeMillis() + 10000L;
		Date start = new Date(startTime);
		SimpleTrigger trigger = new SimpleTrigger("trigger-",null,new Date(), null, 100, 1L);
		
		
		//trigger.setStartTime(start);
		
		
		JobDetail jobDetail = new JobDetail("Schedule-Job-", "com.gxlu.interfacePlatform.schedule", TestJob.class);
		
		scheduler.scheduleJob(jobDetail, trigger);
		
		scheduler.start();
	}
}
