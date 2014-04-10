package com.gxlu.interfacePlatform.database.hibernate;

import java.util.List;

import com.gxlu.interfacePlatform.schedule.Schedule;

public interface ScheduleDao {
	
	public void insert(Schedule schedule);

	public List<Schedule> queryAll();

	public List<Schedule> queryByCondition(Schedule condition);

	public void delete(Schedule schedule);

	public void modify(Schedule schedule);
	
	public Schedule get(Integer id);
}
