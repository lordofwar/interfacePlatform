package com.gxlu.interfacePlatform.schedule.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.interfacePlatform.schedule.Schedule;
import com.gxlu.interfacePlatform.schedule.ScheduleContext;
import com.gxlu.interfacePlatform.schedule.ScheduleHandler;

public class RemoteScheduleImpl implements RemoteSchedule {

	private static final long serialVersionUID = 6206020192261986738L;
	private Log log = LogFactory.getLog(getClass());

	public RemoteScheduleImpl() throws RemoteException {
		super();
	}

	public boolean doAction(final ScheduleContext context) throws RemoteException {
		Schedule schedule= context.getSchedule();
		
		boolean result = false;
		try {
			
			log.info("Enter impl > " + schedule.getHandlerClassName());
			
			Class<?> clazz = Class.forName(schedule.getHandlerClassName());
			ScheduleHandler handler = (ScheduleHandler) clazz.newInstance();
			handler.doAction(context);

		} catch (Exception e) {
			log.error(String.format("Executing schedule[%s] fails.",schedule.getHandlerClassName()),e);
			
			throw new RemoteException(e.getMessage(),e);
		}

		return result;
	}

}
