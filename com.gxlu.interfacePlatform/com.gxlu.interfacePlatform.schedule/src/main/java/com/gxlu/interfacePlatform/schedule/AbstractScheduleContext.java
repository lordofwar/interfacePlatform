package com.gxlu.interfacePlatform.schedule;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

public abstract class AbstractScheduleContext implements ScheduleContext {

	private static final long serialVersionUID = 1L;
	
	private Map<String,Serializable> attributesMap = new HashMap<String, Serializable>();
	private JobExecutionContext context;
	
	public AbstractScheduleContext(JobExecutionContext context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		
		initial();
	}
	
	private void initial(){
		String USERNAME_KEY=ScheduleContext.INTERNAL_ATTRIBUTE_USERNAME;
		
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		if(jobDataMap.containsKey(USERNAME_KEY)){
			this.attributesMap.put(ScheduleContext.INTERNAL_ATTRIBUTE_TYPE,Schedule.TYPE_MANUAL);
			this.attributesMap.put(USERNAME_KEY,(String)jobDataMap.get(USERNAME_KEY));
			this.attributesMap.put(ScheduleContext.INTERNAL_ATTRIBUTE_SCHEDULE, (Schedule)jobDataMap.get(ScheduleContext.INTERNAL_ATTRIBUTE_SCHEDULE));
			jobDataMap.remove(USERNAME_KEY);// Manual schedule just for one time.
		}else{
			this.attributesMap.put(ScheduleContext.INTERNAL_ATTRIBUTE_TYPE, Schedule.TYPE_AUTO);
		}
	}
	
	public AbstractScheduleContext() {
		// TODO Auto-generated constructor stub
	}

	abstract public Schedule getSchedule();
	
	public Map<String, Serializable> getAttributeMap() {
		// TODO Auto-generated method stub
		return attributesMap;
	}

	public void addAttribute(String key,Serializable value){
		attributesMap.put(key, value);
	}
	
	public Object getAttribute(String key) {
		return attributesMap.get(key);
	}
	
	
	public JobExecutionContext getJobExecutionContext(){
		return this.context;
	}
}
