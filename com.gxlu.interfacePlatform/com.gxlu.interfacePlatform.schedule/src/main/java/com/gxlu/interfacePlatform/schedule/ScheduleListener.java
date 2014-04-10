package com.gxlu.interfacePlatform.schedule;
/**
 * Schedule procedure listener.
 * @author Jame
 *
 */
public interface ScheduleListener {

	/**
	 * Before executing the schedule.
	 * @param context
	 */
	public void onBefore(ScheduleContext context);
	
	/**
	 * Success
	 * @param context
	 */
	public void onSuccess(ScheduleContext context);
	
	/**
	 * Fail
	 * @param context
	 */
	public void onFailure(ScheduleContext context);
	
}
