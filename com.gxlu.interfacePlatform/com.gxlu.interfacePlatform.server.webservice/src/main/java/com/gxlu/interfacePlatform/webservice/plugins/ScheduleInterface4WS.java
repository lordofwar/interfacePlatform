package com.gxlu.interfacePlatform.webservice.plugins;

import java.util.List;

import javax.jws.WebService;

import com.gxlu.interfacePlatform.schedule.Schedule;

@WebService
public interface ScheduleInterface4WS {

	public boolean start();
	
	public boolean stop();
	
	public boolean update();
	
	public List<Schedule> getScheduleInfo();
	
	/*
	 * 即时运行一个schedule,code为schedule对应的code
	 */
	public boolean run(String code);
	
	
	public boolean runByUser(String code,String username);
}
