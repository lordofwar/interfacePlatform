package com.gxlu.interfacePlatform.schedule;

import java.io.Serializable;
import java.util.Map;

/**
 * ScheduleContext.
 * 
 * @author pudding
 *
 */
public interface ScheduleContext extends Serializable{

	public static final String INTERNAL_ATTRIBUTE_TYPE="schedule_type";
	public static final String INTERNAL_ATTRIBUTE_USERNAME="schedule_username";
	public static final String INTERNAL_ATTRIBUTE_SCHEDULE="schedule";
	/**
	 * Get the Schedule for the ScheduleHander.
	 * @return
	 */
	public Schedule getSchedule();
	
	/**
	 * AttributeMap used to transfer attributes.
	 * @return
	 */
	public Map<String,Serializable> getAttributeMap();
	
}
