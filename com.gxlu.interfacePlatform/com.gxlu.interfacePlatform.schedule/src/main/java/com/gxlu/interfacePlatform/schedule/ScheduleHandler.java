package com.gxlu.interfacePlatform.schedule;

public interface ScheduleHandler {

	/**
	 * Start the action.
	 */
	public void doAction(ScheduleContext context) throws Exception;
}
