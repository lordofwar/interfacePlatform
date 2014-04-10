package com.gxlu.interfacePlatform.schedule;

public interface ScheduleManager {

	public void startCron();
	
	public void stopCron();
	
	public void triger(String handlerClassName);
	
	public void triger(String handlerClassName,String username);
	
	public void update();
}
