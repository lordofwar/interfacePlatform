package com.gxlu.interfacePlatform.schedule.listener;

import java.util.Date;

import com.gxlu.interfacePlatform.database.hibernate.ScheduleDao;
import com.gxlu.interfacePlatform.database.hibernate.ScheduleDaoImpl;
import com.gxlu.interfacePlatform.schedule.Schedule;
import com.gxlu.interfacePlatform.schedule.ScheduleContext;
import com.gxlu.interfacePlatform.schedule.ScheduleListener;

public class ScheduleStatusListener implements ScheduleListener {

	public ScheduleStatusListener() {
		// TODO Auto-generated constructor stub
	}

	public void onBefore(ScheduleContext context) {
		// TODO Auto-generated method stub
		Integer status = context.getSchedule().getStatus();
		context.getSchedule().setLastRunTime(new Date());
		setStarted(context.getSchedule());
		context.getAttributeMap().put("status", status);
	}
	
	private void setStarted(Schedule schedule){
		setStatus(schedule,Schedule.STATUS_INPROGRESS);
	}
	
	public void onSuccess(ScheduleContext context) {
		// TODO Auto-generated method stub
		onComplete(context);
	}

	public void onFailure(ScheduleContext context) {
		// TODO Auto-generated method stub
		onComplete(context);
	}

	/**
	 * If it's not stopped,then update it. Despite if it is trigger or not. 
	 * When it's not stopped , then you can update the next time always.
	 * 
	 * @param context
	 */
	public void onComplete(ScheduleContext context){
		Integer status = (Integer)context.getAttributeMap().get("status");
		context.getSchedule().setLastFinishTime(new Date());
		
		if(context.getSchedule().getRunPeriod()==null || context.getSchedule().getRunPeriod()==0){//No Interval
			//If it's triggered by "schedule" function, and already for the last time, it should by stopped.
			if(context.getSchedule().getNextRunTime() ==null || context.getSchedule().getNextRunTime().before(new Date()))
				setStatus(context.getSchedule(),Schedule.STATUS_STOPPED);
			// otherwise, it's triggered by 'run' function. In this case, just resume the status.
			else setStatus(context.getSchedule(),status);
		}else{
			
			if(status!=null && status!=Schedule.STATUS_STOPPED){
				Schedule schedule = context.getSchedule();
				schedule.setNextRunTime(schedule.calculateStartTime());
			}
			
			setStatus(context.getSchedule(),status);
		}
	}
	
	private void setStatus(Schedule schedule,Integer status){
		ScheduleDao dao = new ScheduleDaoImpl();
		schedule.setStatus(status);
		dao.modify(schedule);
	}
}
