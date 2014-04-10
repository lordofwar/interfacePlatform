package com.gxlu.interfacePlatform.schedule.listener;

import com.gxlu.interfacePlatform.database.hibernate.ScheduleDao;
import com.gxlu.interfacePlatform.database.hibernate.ScheduleDaoImpl;
import com.gxlu.interfacePlatform.schedule.ProgressListener;
import com.gxlu.interfacePlatform.schedule.Schedule;

public class IMSProgressListener implements ProgressListener {

	public IMSProgressListener() {
		// TODO Auto-generated constructor stub
	}

	public void onChangingProgress(Schedule schedule, int rate) {
		// TODO Auto-generated method stub
		setProgress(schedule,rate);
	}
	
	private void setProgress(Schedule schedule,int rate){
		ScheduleDao dao = new ScheduleDaoImpl();
		schedule.setProgress(rate);
		dao.modify(schedule);
	}
}
