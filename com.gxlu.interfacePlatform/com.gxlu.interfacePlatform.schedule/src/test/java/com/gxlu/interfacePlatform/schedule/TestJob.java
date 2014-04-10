package com.gxlu.interfacePlatform.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job {

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		String className=(String)context.getJobDetail().getJobDataMap().get("clazz");
		System.out.println(className);
		Class clazz=null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ScheduleHandler handler = (ScheduleHandler) clazz.newInstance();
			handler.doAction(null);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
