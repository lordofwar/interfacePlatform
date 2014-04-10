package com.gxlu.interfacePlatform.schedule.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.gxlu.interfacePlatform.schedule.Schedule;
import com.gxlu.interfacePlatform.schedule.ScheduleContext;

public interface RemoteSchedule extends Remote {
  
  public boolean doAction(ScheduleContext context)throws RemoteException;

  
}
