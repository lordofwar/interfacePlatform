package com.gxlu.interfacePlatform.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;



public class DefaultScheduleManager implements ScheduleManager ,ScheduleListenerManager{

	private final static String GROUP="com.gxlu.interfacePlatform.schedule";
	private final static String JOB_PREFIX="Schedule-Job-";
	private final static String TRIGER_PREFIX="Trigger-";
	
	private final static String INTERNAL_JOB_GROUP="INTERNAL-GROUP";
	private final static String INTERNAL_JOB_PREFIX="SCHEDULE-INTERNAL-";
	private final static String INTERNAL_TRIGER_PREFIX="TRIGER-INTERNAL-";
	
	private Log log = LogFactory.getLog(getClass());

	private List<Schedule> schedules;
	private ScheduleLoader loader ;
	
	private ScheduleListenerManager scheduleListenerManager=DefaultScheduleListenerManager.getDefaultScheduleListenerManager();
	
	public DefaultScheduleManager(){
	}
	
	public DefaultScheduleManager(ScheduleLoader args){
		this();
		loader = args;
	}
	
	public void startCron() {
		// TODO Auto-generated method stub
		schedules =loader.load();
		
		SchedulerFactory sf = new StdSchedulerFactory();
		try {
			Scheduler scheduler= sf.getScheduler();
			for(final Schedule schedule : schedules){
				if(schedule.isInvalid() || schedule.isStop())
					continue;
				
				addSchedule(scheduler,schedule);
			}
			
			scheduler.start();
		} catch (SchedulerException e) {
			log.error("-------------	Schedule manager fail starting		--------------", e);
		}
	}
	
	private void addSchedule(final Scheduler scheduler,final Schedule schedule) throws SchedulerException{
		SimpleTrigger trigger ;
		trigger = createTriger(schedule);
		
		JobDetail jobDetail = new JobDetail(JOB_PREFIX+schedule.getHandlerClassName(), GROUP, ScheduleHandler2QuartzAdaptor.class);
		jobDetail.getJobDataMap().put(ScheduleContext.INTERNAL_ATTRIBUTE_SCHEDULE, schedule);
		
		scheduler.scheduleJob(jobDetail, trigger);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info(String.format("Add schedule %s into scheduler[%s], NextRuntime is [%s]",JOB_PREFIX+schedule.getHandlerClassName(),scheduler.getSchedulerName(),format.format(trigger.getStartTime())));
	}
	
	private SimpleTrigger createTriger(final Schedule schedule){
		
		SimpleTrigger trigger = new SimpleTrigger(TRIGER_PREFIX+schedule.getHandlerClassName(),GROUP);
		
		trigger.setStartTime(schedule.calculateStartTime());
		
		if(schedule.getRunPeriod() != null && schedule.getRunPeriod()>0 && schedule.getStatus()!=null && schedule.getStatus()!=Schedule.STATUS_STOPPED){
			trigger.setRepeatInterval(schedule.getRunPeriod()*60*1000);
			trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		}
		return trigger;
	}
	
	
	private SimpleTrigger createImmediateTriger(Schedule schedule) {
		// TODO Auto-generated method stub
		SimpleTrigger trigger = new SimpleTrigger(TRIGER_PREFIX+schedule.getHandlerClassName(),GROUP);
		trigger.setStartTime(new Date());
		return trigger;
	}

	public void stopCron() {
		// TODO Auto-generated method stub
		SchedulerFactory sf = new StdSchedulerFactory();
		try {
			Scheduler scheduler= sf.getScheduler();
			if(scheduler!=null)
				scheduler.shutdown();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			log.error("",e);
		}
	}
	
	/**
	 * 如果判断Schedule已经暂停，生成临时的一次性Schedule马上执行。
	 * 执行过程中，如果重复执行，判断Status如果是正在处理，则直接跳过。
	 * 执行结束后，Schedule恢复到原有的状态。
	 */
	public void triger(String handlerClassName) {
		// TODO Auto-generated method stub
		triger(handlerClassName,"sa");
	}
	
	/**
	 * 如果判断Schedule已经暂停，生成临时的一次性Schedule马上执行。
	 * 执行过程中，如果重复执行，判断Status如果是正在处理，则直接跳过。
	 * 执行结束后，Schedule恢复到原有的状态。
	 */
	public void triger(String handlerClassName,String username) {
		// TODO Auto-generated method stub
		SchedulerFactory sf = new StdSchedulerFactory();
		try {
			Scheduler scheduler= sf.getScheduler();
			
			Schedule schedule=findSchedule(handlerClassName);
			if(schedule==null){
				log.warn(String.format("Cannot find the job[%s] in the database.Please check the configuration and the database.",handlerClassName));
				return;
			}
			if(schedule.getStatus() == Schedule.STATUS_INPROGRESS){
				log.warn(String.format("Trigger job[%s] fail. It's being processed already.",handlerClassName));
				return;
			}
			
			if(isNotOnSchedule(handlerClassName)){// Also means it's stopped.
				log.warn(String.format("Cannot find the job[%s] in scheduler %s. Add a new schedule.",handlerClassName,scheduler.getSchedulerName()));
				if(schedule!=null)
					this.addManualSchedule(scheduler, schedule,username);
					
			}else{
				JobDetail jobDetail = findJobDetail(handlerClassName);
				jobDetail.getJobDataMap().put(ScheduleContext.INTERNAL_ATTRIBUTE_USERNAME, username);
				scheduler.triggerJob(JOB_PREFIX+handlerClassName,GROUP);
				log.info(String.format("Triger job[%s] successfully.",handlerClassName));
			}
			
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			log.error("",e);
		}
	}
	
	private boolean isNotOnSchedule(String handlerClassName){
		return !isOnSchedule(handlerClassName);
	}
	
	private boolean isOnSchedule(String handlerClassName){
		JobDetail jobDetail = findJobDetail(handlerClassName);
		if(jobDetail!=null){
			return true;
		}
		return false;
	}
	
	private JobDetail findJobDetail(String handlerClassName){
		SchedulerFactory sf = new StdSchedulerFactory();
		try {
			Scheduler scheduler= sf.getScheduler();
			JobDetail jobDetail = scheduler.getJobDetail(JOB_PREFIX+handlerClassName,GROUP);
			return jobDetail;
		}catch (SchedulerException e) {
			// TODO Auto-generated catch block
			log.error("",e);
		}
		return null;
	}
	
	private void addManualSchedule(final Scheduler scheduler,final Schedule schedule,String username) throws SchedulerException{
		SimpleTrigger trigger ;
		trigger = this.createImmediateTriger(schedule);
		
		JobDetail jobDetail = new JobDetail(JOB_PREFIX+schedule.getHandlerClassName(), GROUP, ScheduleHandler2QuartzAdaptor.class);
		jobDetail.getJobDataMap().put(ScheduleContext.INTERNAL_ATTRIBUTE_SCHEDULE, schedule);
		jobDetail.getJobDataMap().put(ScheduleContext.INTERNAL_ATTRIBUTE_USERNAME, username);
		scheduler.scheduleJob(jobDetail, trigger);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info(String.format("Add manual schedule %s into scheduler[%s], NextRuntime is [%s]",JOB_PREFIX+schedule.getHandlerClassName(),scheduler.getSchedulerName(),format.format(trigger.getStartTime())));
	}
	
	private Schedule findSchedule(String handlerClassName){
		if(this.schedules==null) return null;
		
		for(Schedule schedule: this.schedules){
			if(handlerClassName.equals(schedule.getHandlerClassName())){
				return schedule;
			}
		}
		return null;
	}
	
	/**
	 * Update the schedule.
	 * if Stop -> Stop, did not need to load. by using "modify" function.
	 * if Stop -> Pending, Load. By using "start" function.
	 * if Pending -> Stop, remove it. By using "stop" function.
	 * if In process -> could not be changed.
	 */
	public void update() {
		// TODO Auto-generated method stub
		log.info("Trying to update all schedules.");
		schedules =loader.load();
		SchedulerFactory sf = new StdSchedulerFactory();
		try {
			Scheduler scheduler= sf.getScheduler();
			for(final Schedule schedule : schedules){
				if(schedule.isInvalid() || schedule.getStatus()==Schedule.STATUS_INPROGRESS)
					continue;
				
				if(schedule.isStop()){
					//Some of them are stopped.
					if(this.isOnSchedule(schedule.getHandlerClassName())){
						log.info(String.format("Stop schedule[%s]",schedule.getHandlerClassName()));
						removeSchedule(schedule.getHandlerClassName());
					}
					continue;
				}
				
				if(isNotOnSchedule(schedule.getHandlerClassName())){
					this.addSchedule(scheduler, schedule);// Some of them are started.
				}else{//Pending : cannot be changed. Either be stopped or started. So there is no need to restart.
//					updateSchedule(scheduler,schedule); // Some of them are modify.
				}
				
			}
		} catch (SchedulerException e) {
			log.error("-------------	Schedule manager fail starting		--------------", e);
		}
		
	}

	private void updateSchedule(Scheduler scheduler, Schedule schedule) throws SchedulerException {
		// TODO Auto-generated method stub
		removeSchedule(schedule.getHandlerClassName());
		this.addSchedule(scheduler, schedule);
	}
	
	private void removeSchedule(String handlerClassName){
		SchedulerFactory sf = new StdSchedulerFactory();
		try {
			Scheduler scheduler= sf.getScheduler();
			scheduler.deleteJob(JOB_PREFIX+handlerClassName,GROUP);
			log.info(String.format("Remove schedule %s into scheduler[%s]",JOB_PREFIX+handlerClassName,scheduler.getSchedulerName()));
		}catch (SchedulerException e) {
			// TODO Auto-generated catch block
			log.error("",e);
		}
	}

	public void addInternalJob(Trigger trigger,Class<? extends Job> internalJobClass){
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler;
		try {
			trigger.setGroup(INTERNAL_JOB_GROUP);
			trigger.setName(INTERNAL_TRIGER_PREFIX+internalJobClass.getName());
			scheduler = sf.getScheduler();
			JobDetail jobDetail = new JobDetail(INTERNAL_JOB_PREFIX+internalJobClass.getName(), INTERNAL_JOB_GROUP, internalJobClass);
			scheduler.scheduleJob(jobDetail, trigger);
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ScheduleLoader getLoader() {
		return loader;
	}

	public void setLoader(ScheduleLoader loader) {
		this.loader = loader;
	}

	public void addListener(ScheduleListener listener) {
		// TODO Auto-generated method stub
		scheduleListenerManager.addListener(listener);
	}

	public void removeListener(ScheduleListener listener) {
		// TODO Auto-generated method stub
		scheduleListenerManager.removeListener(listener);
	}

	public void removeAllListener() {
		// TODO Auto-generated method stub
		scheduleListenerManager.removeAllListener();
	}

	public void addListener(ProgressListener listener) {
		// TODO Auto-generated method stub
		scheduleListenerManager.addListener(listener);
	}

}
