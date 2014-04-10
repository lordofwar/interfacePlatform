package com.gxlu.interfacePlatform.schedule;

import java.util.List;

/**
 * Load schedule information.
 * @author pudding
 *
 */
public interface ScheduleLoader {
 
	/**
	 * Loading
	 * @return
	 */
	public List<Schedule> load();
}
