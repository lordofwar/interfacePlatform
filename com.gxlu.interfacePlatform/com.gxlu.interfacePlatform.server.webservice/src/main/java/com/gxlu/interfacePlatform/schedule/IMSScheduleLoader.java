package com.gxlu.interfacePlatform.schedule;

import java.util.List;

import com.gxlu.interfacePlatform.database.bean.InterfaceLib;
import com.gxlu.interfacePlatform.database.hibernate.ScheduleDao;
import com.gxlu.interfacePlatform.database.hibernate.ScheduleDaoImpl;
import com.gxlu.interfacePlatform.schedule.schedulelog.ScheduleQueryService;
import com.gxlu.interfacePlatform.webservice.plugins.ScheduleCode2HandleClass;

public class IMSScheduleLoader implements ScheduleLoader {

	public IMSScheduleLoader() {
		// TODO Auto-generated constructor stub
	}

	public List<Schedule> load() {
		// TODO Auto-generated method stub
		List<Schedule> list = getSchedulesFromDatabase();
		return list;
	}
	
	private List<Schedule> getSchedulesFromDatabase() {
		ScheduleDao dao = new ScheduleDaoImpl();
		final List<Schedule> result = dao.queryAll();
		ScheduleCode2HandleClass scheduleCode2HandleClass = new ScheduleCode2HandleClass();
		if (result != null && result.size() > 0) {
			for (Schedule schedule : result) {
				if (schedule.getInterfacelibid() != null ) {
					ScheduleQueryService service = new ScheduleQueryService();
					InterfaceLib interfaceLib = service.findInterfaceLibById(schedule.getInterfacelibid());
					
					String handlerClassName = scheduleCode2HandleClass.getHandlerClassName(interfaceLib.getCode());
					schedule.setHandlerClassName(handlerClassName);
				}
				
			}
		}
		
		return result;
	}
	
}
