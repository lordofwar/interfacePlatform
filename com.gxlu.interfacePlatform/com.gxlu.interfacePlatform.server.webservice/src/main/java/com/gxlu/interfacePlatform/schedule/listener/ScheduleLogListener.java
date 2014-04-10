package com.gxlu.interfacePlatform.schedule.listener;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.interfacePlatform.database.bean.InterfaceLib;
import com.gxlu.interfacePlatform.database.bean.ScheduleLog;
import com.gxlu.interfacePlatform.database.common.CommonPersistDAOImpl;
import com.gxlu.interfacePlatform.schedule.DetailLogStorage;
import com.gxlu.interfacePlatform.schedule.Schedule;
import com.gxlu.interfacePlatform.schedule.ScheduleContext;
import com.gxlu.interfacePlatform.schedule.ScheduleListener;
import com.gxlu.interfacePlatform.schedule.schedulelog.ScheduleQueryService;

@SuppressWarnings("unused")
public class ScheduleLogListener implements ScheduleListener {
	private Log log = LogFactory.getLog(getClass());
	private CommonPersistDAOImpl persistDAO = new CommonPersistDAOImpl();
	private ScheduleQueryService queryService = new ScheduleQueryService();

	public void onBefore(ScheduleContext context) {
		Schedule schedule = context.getSchedule();
		Integer interfaceLibId = schedule.getInterfacelibid().intValue();
		DetailLogStorage.getInstance().flush(getLibCodeById(interfaceLibId));

		ScheduleLog scheduleLog = new ScheduleLog();
		scheduleLog.setInterfacelibid(interfaceLibId);
		scheduleLog.setStartedTime(new Date());
		scheduleLog.setCategory(Schedule.CATEGORY_COLLECTION);

		Integer type = (Integer) context.getAttributeMap().get(ScheduleContext.INTERNAL_ATTRIBUTE_TYPE);
		if (type == Schedule.TYPE_MANUAL) {
			String username = (String) context.getAttributeMap().get(ScheduleContext.INTERNAL_ATTRIBUTE_USERNAME);
			scheduleLog.setType(Schedule.TYPE_MANUAL);
			scheduleLog.setUserName(username);
			scheduleLog.setLoginName(username);
		} else {
			scheduleLog.setType(Schedule.TYPE_AUTO);
		}

		scheduleLog = (ScheduleLog) persistDAO.createObject(scheduleLog);
		context.getAttributeMap().put("ScheduleLog", scheduleLog);
	}

	public void onSuccess(ScheduleContext context) {
		ScheduleLog scheduleLog = (ScheduleLog) context.getAttributeMap().get("ScheduleLog");
		scheduleLog.setStatus(1);
		scheduleLog.setFinishedTime(new Date());
		scheduleLog.setDetailLog(getDetailLogByLibId(scheduleLog.getInterfacelibid()));
		persistDAO.updateObject(scheduleLog);
	}

	public void onFailure(ScheduleContext context) {
		ScheduleLog scheduleLog = (ScheduleLog) context.getAttributeMap().get("ScheduleLog");
		scheduleLog.setStatus(2);
		scheduleLog.setFinishedTime(new Date());
		scheduleLog.setFailedReason((String) context.getAttributeMap().get("ERROR"));
		scheduleLog.setDetailLog(getDetailLogByLibId(scheduleLog.getInterfacelibid()));
		persistDAO.updateObject(scheduleLog);
	}

	public String getLibCodeById(Integer interfaceLibId) {
		String code = null;
		if (interfaceLibId != null) {
			InterfaceLib interfaceLib = queryService.findInterfaceLibById(Long.valueOf(interfaceLibId));
			code = interfaceLib.getCode();
		}
		return code;
	}

	public Clob getDetailLogByLibId(Integer interfaceLibId) {
		Clob clob = null;
		String detailInfo = DetailLogStorage.getInstance().getDetailLog(getLibCodeById(interfaceLibId));
		try {
			if (detailInfo != null) {
				clob = new SerialClob(detailInfo.toCharArray());
			}
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clob;
	}
}
