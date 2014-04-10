package com.gxlu.interfacePlatform.schedule;

public class SimpleScheduleContext extends AbstractScheduleContext{

	private Schedule schedule;
	
	public SimpleScheduleContext(Schedule schedule){
		this.schedule=schedule;
	}
	@Override
	public Schedule getSchedule() {
		// TODO Auto-generated method stub
		return schedule;
	}

}
