package com.gxlu.interfacePlatform.schedule;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SimpleTrigger;

import com.gxlu.interfacePlatform.database.hibernate.ScheduleDao;
import com.gxlu.interfacePlatform.database.hibernate.ScheduleDaoImpl;
import com.gxlu.interfacePlatform.schedule.listener.AlarmListener;
import com.gxlu.interfacePlatform.schedule.listener.IMSProgressListener;
import com.gxlu.interfacePlatform.schedule.listener.ScheduleLogListener;
import com.gxlu.interfacePlatform.schedule.listener.ScheduleStatusListener;

public class IMSScheduleManager extends DefaultScheduleManager {

	private static IMSScheduleManager imsScheduleManager;
	private Thread mainThread ;
	private Log log = LogFactory.getLog(getClass());
	
	public static IMSScheduleManager getIMSScheduleManager(){
		if(imsScheduleManager==null){
			imsScheduleManager = new IMSScheduleManager();
		}
		return imsScheduleManager;
	}
	
	private IMSScheduleManager() {
		// TODO Auto-generated constructor stub
		super();
		this.reset();
		this.setLoader(new IMSScheduleLoader());
		this.addListener(new ScheduleLogListener());
		this.addListener(new ScheduleStatusListener());
		this.addListener(new AlarmListener());
		this.addListener(new IMSProgressListener());
		this.addInternalSchedule(new LogCleanInternalSchedule());
	}

	/**
	 * Reset the status and NextTime while first starting.
	 */
	private void reset(){
		ScheduleDao dao = new ScheduleDaoImpl();
		final List<Schedule> result = dao.queryAll();
		if (result != null && result.size() > 0) {
			for (Schedule schedule : result) {
				if(schedule.getStatus() == Schedule.STATUS_PENDING||schedule.getStatus()==Schedule.STATUS_INPROGRESS){
					reset(schedule);
				}
			}
		}
	}
	
	/**
	 * If it's not stopped update it. Despite if it is trigger or not. 
	 * When it's not stopped , then you can update the next time always.
	 * @param schedule
	 */
	private void reset(Schedule schedule){
		ScheduleDao dao = new ScheduleDaoImpl();
		
		if(schedule.getStatus()==Schedule.STATUS_INPROGRESS)
			schedule.setStatus(Schedule.STATUS_STOPPED);
		
		schedule.setNextRunTime(schedule.calculateStartTime());
		dao.modify(schedule);
	}
	
	@Override
	public void startCron() {
		// TODO Auto-generated method stub
		log.info("Start Schedule Manager.");
		mainThread = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				IMSScheduleManager.super.startCron();
				
			}
		});
		mainThread.start();
	}
	
	public void addInternalSchedule(InternalSchedule internalSchedule){
		this.addInternalJob(internalSchedule.getTrigger(), internalSchedule.getClazz());
	}

}
