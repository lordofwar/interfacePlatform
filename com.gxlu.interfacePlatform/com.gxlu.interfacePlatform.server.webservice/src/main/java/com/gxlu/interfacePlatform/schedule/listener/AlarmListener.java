package com.gxlu.interfacePlatform.schedule.listener;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.interfacePlatform.database.bean.XAutodisinterface;
import com.gxlu.interfacePlatform.database.bean.XbAutoDiscoveryRule;
import com.gxlu.interfacePlatform.database.common.CommonPersistDAOImpl;
import com.gxlu.interfacePlatform.schedule.ScheduleContext;
import com.gxlu.interfacePlatform.schedule.ScheduleListener;
import com.gxlu.interfacePlatform.schedule.schedulelog.ScheduleQueryService;

public class AlarmListener implements ScheduleListener {

	private Log log = LogFactory.getLog(getClass());
	private CommonPersistDAOImpl persistDAO = new CommonPersistDAOImpl();
	// 接口平台只告警两种状态
	private static final Integer FAILED = 3;
	private static final Integer SUCCESSFUL = 4;

	private ScheduleQueryService queryService = new ScheduleQueryService();
	public AlarmListener() {
		// TODO Auto-generated constructor stub
		log.info("Add listener for alarm.");
	}

	public void onBefore(ScheduleContext context) {
		// TODO Auto-generated method stub
		context.getAttributeMap().put("StartTime", new Date());
	}

	public void onSuccess(ScheduleContext context) {
		// TODO Auto-generated method stub
		context.getAttributeMap().put("EndTime", new Date());
		recordAlarmLog(context, SUCCESSFUL);
	}

	public void onFailure(ScheduleContext context) {
		// TODO Auto-generated method stub
		recordAlarmLog(context, FAILED);
	}

	/*
	 * 根据告警规则记录相对应的告警记录
	 */
	public void recordAlarmLog(ScheduleContext context,Integer status) {
		Date startTime =(Date)context.getAttributeMap().get("StartTime");
		
		List list = queryService.QueryAlertRules(context.getSchedule(), status);
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				XbAutoDiscoveryRule rule = (XbAutoDiscoveryRule) obj;
				XAutodisinterface disinterface = new XAutodisinterface();
				disinterface.setTaskname(rule.getXbAlertrule().getName());
				disinterface.setRuleid(Long.valueOf(rule.getXbAlertrule().getId()));
				if (status == SUCCESSFUL) {
					disinterface.setAutodisstatus("SUCCESSFUL");
				} else if (status == FAILED) {
					disinterface.setAutodisstatus("FAILED");
					disinterface.setErrorreason((String) context.getAttributeMap().get("ERROR"));
				}
				disinterface.setAutodisstartdate(startTime);
				disinterface.setAutodisfinishdate(new Date());
				disinterface.setTasktype(rule.getAutodisname());
				persistDAO.createObject(disinterface);
			}
		}
	}
}
