package com.gxlu.interfacePlatform.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TestScheduleManager {
	public static void main(String[] args) throws InterruptedException {
		ScheduleManager m = new DefaultScheduleManager(new ScheduleLoader() {
			
			public List<Schedule> load() {
				// TODO Auto-generated method stub
				long startTime = System.currentTimeMillis() + 10000L;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date start = new Date(startTime);
				System.out.println(format.format(start));
				
				List<Schedule> list = new LinkedList<Schedule>();
				Schedule s = new Schedule();
				s.setId(1);
				s.setNextRunTime(start);
				s.setHandlerClassName("com.gxlu.interfacePlatform.schedule.plugins.AirComSchedule");
				s.setRunPeriod(new Long(3));
//				list.add(s);
//				s = new Schedule();
//				s.setId(2);
//				s.setRunPeriod(new Long(3));
//				try {
//					s.setNextRunTime(format.parse("2012-11-1 00:00:00"));
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				s.setHandlerClassName("com.gxlu.interfacePlatform.schedule.TestScheduleHandler");
//				list.add(s);
//				s = new Schedule();
//				s.setId(3);
//				s.setRunPeriod(new Long(3));
//				try {
//					s.setNextRunTime(format.parse("2012-11-12 13:39:00"));
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				s.setHandlerClassName("com.gxlu.interfacePlatform.schedule.TestScheduleHandler");
				list.add(s);
				return list;
			}
		});
		
		try{
			m.startCron();
			Thread.sleep(12*1000);
		}finally{
			m.stopCron();
		}
	}
}
