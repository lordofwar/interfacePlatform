package com.gxlu.interfacePlatform.schedule;

public interface ScheduleListenerManager {

	/**
	 * Add a schedule listener.
	 * 
	 * @param listener
	 */
	public abstract void addListener(ScheduleListener listener);

	public abstract void removeListener(ScheduleListener listener);

	/**
	 * Remove all of the listener.
	 */
	public abstract void removeAllListener();
	
	/**
	 * Add a progress listener.
	 * @param listener
	 */
	public abstract void addListener(ProgressListener listener);

}