package com.gxlu.interfacePlatform.schedule;

public class TestScheduleHandler implements ScheduleHandler{

	public void doAction(ScheduleContext context) {
		// TODO Auto-generated method stub
		System.out.println(context.getSchedule().getId());
	}
	
}
