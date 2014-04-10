package com.gxlu.interfacePlatform.schedule;

import java.rmi.Naming;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gxlu.interfacePlatform.schedule.rmi.RemoteSchedule;

/**
 * This class is for Adapting ScheduleHandler.class to Quartz Job class.
 * 
 * @author pudding
 * 
 */
public class ScheduleHandler2QuartzAdaptor implements Job {

	private Log log = LogFactory.getLog(getClass());

	private DefaultScheduleListenerManager scheduleListenerManager = DefaultScheduleListenerManager
			.getDefaultScheduleListenerManager();

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		final Schedule schedule = (Schedule) context.getJobDetail().getJobDataMap().get("schedule");
		
		if(!validate(schedule)){
			return;
		}
		
		String className = schedule.getHandlerClassName();
		
		log.info("Start to process schedule[" + className + "]");
		
		final ProgressThread progressThread = new ProgressThread(schedule){
			
			@Override
			public void changeProgress(long percent) {
				// TODO Auto-generated method stub
				scheduleListenerManager.onChangingProgress(schedule,(int)percent);
			}
			
		};
		
		AbstractScheduleContext scheduleContext = new AbstractScheduleContext(context) {
			
			public Schedule getSchedule() {
				// TODO Auto-generated method stub
				return schedule;
			}

			
		};
		
		
		long startTime = System.currentTimeMillis();
		
		try {
			
			scheduleListenerManager.onChangingProgress(schedule,0);
			scheduleListenerManager.onBefore(scheduleContext);

			invokeSchedule(scheduleContext);
			progressThread.start();
			
			scheduleListenerManager.onSuccess(scheduleContext);
			
		} catch (Exception e) {
			log.error(String.format("Schedule[%s] fails.",className),e);
			scheduleContext.addAttribute("ERROR", e.getMessage());
			
			scheduleListenerManager.onFailure(scheduleContext);
			
		} finally {
			long endTime = System.currentTimeMillis();
			log.info(String.format("Task is done[%s]. Time spent : %d",className, endTime - startTime));
			
			progressThread.forceStop();
			scheduleListenerManager.onChangingProgress(schedule,100);
		}
		
	}

	private boolean validate(Schedule schedule){
		String className = schedule.getHandlerClassName();

		if (className == null || className.equals("")) {
			log.error("HandlerClassName is null.");
			return false;
		}

		if(schedule.getStatus() == Schedule.STATUS_INPROGRESS){
			log.warn(String.format("Trying to run the schedule[%s], while it is being processed.",className));
			return false;
		}
		
		if(schedule.getStatus()==null || schedule.getStatus() == Schedule.STATUS_STOPPED){
			log.warn(String.format("Trying to run the schedule[%s], while it is already stopped.",className));
		}
		
		return true;
	}
	
	private void invokeSchedule(AbstractScheduleContext scheduleContext) throws Exception {
		
		Schedule schedule = scheduleContext.getSchedule();
		
		if (checkIfProxySchedule(schedule)) {
			log.info("It's remote task.");
			invokeRemoteProxy(scheduleContext);
		} else {
			log.info("It isn't remote task.");
			invokeLocally(scheduleContext);
		}
	}
	
	/**
	 * Check whether it is a remote schedule.
	 * @param schedule
	 * @return
	 */
	private boolean checkIfProxySchedule(Schedule schedule){
		
		log.info("Check whether it's remote task.");

		String rmiAddr = schedule.getRmiaddr();
		if (rmiAddr != null && !rmiAddr.equals("")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 如果是远程，调用代理.
	 * 
	 * @param scheduleContext
	 * @return
	 * @throws Exception
	 */
	private boolean invokeRemoteProxy(ScheduleContext scheduleContext)throws Exception {
		Schedule schedule = scheduleContext.getSchedule();
		RemoteSchedule remoteSchedule = (RemoteSchedule) Naming.lookup(schedule.getRmiaddr());
		
		boolean result = remoteSchedule.doAction(new SimpleScheduleContext(schedule));
		
		log.info("RMI SUCCESS, AND THE RESULT IS:" + result);
		
		return result;
	}
	
	/**
	 * If is a local schedule, it's deployed in local server.
	 * 
	 * @param scheduleContext
	 * @throws Exception
	 */
	private void invokeLocally(AbstractScheduleContext scheduleContext) throws Exception {
		// TODO Auto-generated method stub
		Schedule schedule = scheduleContext.getSchedule();
		String className = schedule.getHandlerClassName();
		ScheduleHandler handler = getScheduleHandler(className);
		handler.doAction(scheduleContext);
	}
	
	private ScheduleHandler getScheduleHandler(String className){
		try{
			Class<?> clazz = Class.forName(className);
			ScheduleHandler handler = (ScheduleHandler) clazz.newInstance();
			return handler;
		} catch (ClassNotFoundException e) {
			log.error(className + "Cannot find the class.", e);
		} catch (Exception e) {
			log.error(String.format("Schedule[%s] execution fails.",className),e);
		}
		return null;
	}


}
