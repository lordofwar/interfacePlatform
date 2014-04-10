package com.gxlu.international.common.service;

public interface ScheduleDetailLogService {

	public void info(String str);

	public void error(String str, Exception e);
	
	public void error(String str);
	

}
