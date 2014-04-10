package com.gxlu.interfacePlatform.webservice.plugins;

import java.util.List;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.interfacePlatform.schedule.AbstractScheduleContext;
import com.gxlu.interfacePlatform.schedule.IMSScheduleManager;
import com.gxlu.interfacePlatform.schedule.Schedule;
import com.gxlu.interfacePlatform.schedule.ScheduleHandler;

@WebService(endpointInterface = "com.gxlu.interfacePlatform.webservice.plugins.ScheduleInterface4WS", serviceName = "scheduleService")
public class ScheduleInterface4WSImpl implements ScheduleInterface4WS {
	private Log log = LogFactory.getLog(getClass());
	private IMSScheduleManager scheduleManager;

	public ScheduleInterface4WSImpl(){
		scheduleManager = IMSScheduleManager.getIMSScheduleManager();
	}
	
	public boolean start() {
		stop();
		
		scheduleManager.startCron();
		
		return true;
	}

	
	public  boolean stop() {
		// TODO Auto-generated method
		synchronized (scheduleManager) {
			if (scheduleManager != null) {
				scheduleManager.stopCron();
			}
		}
		
		return true;
	}

	public boolean update() {
		scheduleManager.update();
		return true;
	}

	

	public List<Schedule> getScheduleInfo() {
		return null;
	}

	public boolean run(String code) {
		boolean result = false;
		
		ScheduleCode2HandleClass scheduleCode2HandleClass = new ScheduleCode2HandleClass();
		String handlerClassName = scheduleCode2HandleClass.getHandlerClassName(code);
		if (handlerClassName == null || handlerClassName.equals("")) {
			log.info(String.format("Cannot find the handleClass for code %s",code));
			return result;
		}
		scheduleManager.triger(handlerClassName);
		
		return true;
	}
	
	private void startRunning(final ScheduleHandler handler){
		new Thread() {
			@Override
			public void run() {
				try {
					handler.doAction(new AbstractScheduleContext() {
						@Override
						public Schedule getSchedule() {
							return null;
						}
					});
				} catch (Exception e) {
					log.error("运行schedule是出现异常", e);
				}
			}
		}.start();
	}
	
	private ScheduleHandler getHandler(String handlerClassName){
		try {
			Class<?> clazz = Class.forName(handlerClassName);
			final ScheduleHandler handler = (ScheduleHandler)clazz.newInstance();
			return handler;
		} catch (Exception e) {
			log.error("Error occures while instantial new handler.", e);
		}
		return null;
	}

	public boolean runByUser(String code, String username) {
		// TODO Auto-generated method stub
		boolean result = false;
		
		ScheduleCode2HandleClass scheduleCode2HandleClass = new ScheduleCode2HandleClass();
		String handlerClassName = scheduleCode2HandleClass.getHandlerClassName(code);
		if (handlerClassName == null || handlerClassName.equals("")) {
			log.info(String.format("Cannot find the handleClass for code %s",code));
			return result;
		}
		scheduleManager.triger(handlerClassName,username);
		
		return true;
	}

}
