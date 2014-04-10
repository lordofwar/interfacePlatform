package com.gxlu.interfacePlatform.schedule;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.gxlu.interfacePlatform.schedule.rmi.RemoteSchedule;
import com.gxlu.interfacePlatform.schedule.rmi.RemoteScheduleImpl;

public class TestRMI {

  /**
   * @param args
   */
  public static void main(String [] args) {
    try {
//      RemoteSchedule remote = new RemoteScheduleImpl();
//      Naming.rebind("schedule", remote);
//      System.out.println("RemoteSchedule bound");
      Schedule s = new Schedule();
      s.setId(1);
      long startTime = System.currentTimeMillis() + 10000L;
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date start = new Date(startTime);
      s.setNextRunTime(start);
      s.setHandlerClassName("com.gxlu.interfacePlatform.schedule.plugins.SAPAssetSchedule");
      test(s);
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  public static void start() throws InterruptedException {
    ScheduleManager m = new DefaultScheduleManager(new ScheduleLoader() {

      public List<Schedule> load() {
        // TODO Auto-generated method stub
        long startTime = System.currentTimeMillis() + 10000L;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = new Date(startTime);
        System.out.println(format.format(start));

        List<Schedule> list = new LinkedList<Schedule>();
        Schedule s = new Schedule();
        s.setId(1);
        s.setNextRunTime(start);
        s.setHandlerClassName("com.gxlu.interfacePlatform.schedule.TestScheduleHandler");
        list.add(s);
        s = new Schedule();
        s.setId(2);
        s.setRunPeriod(3l);
        try {
          s.setNextRunTime(format.parse("2012-11-1 00:00:00"));
        }
        catch(ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        s.setHandlerClassName("com.gxlu.interfacePlatform.schedule.plugins.SAPAssetSchedule");
        list.add(s);
        s = new Schedule();
        s.setId(3);
        try {
          s.setNextRunTime(format.parse("2012-11-12 13:39:00"));
        }
        catch(ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        s.setHandlerClassName("com.gxlu.interfacePlatform.schedule.plugins.AirComSchedule");
        list.add(s);
        return list;
      }
    });

    try {
      m.startCron();
      Thread.sleep(12 * 1000);
    }
    finally {
      m.stopCron();
    }
  }
  
  private static boolean test(final Schedule schedule ){
    boolean result = false;
    try {
      String rmiAddress = "rmi://192.168.42.129:1099/schedule";//get it from schedule objcet
      RemoteSchedule remoteSchedule = (RemoteSchedule) Naming.lookup(rmiAddress);
      result = remoteSchedule.doAction(new AbstractScheduleContext() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Schedule getSchedule() {
			// TODO Auto-generated method stub
			return schedule;
		}
	});
    }
    catch(RemoteException e) {
      e.printStackTrace();
    }
    catch(MalformedURLException e) {
      e.printStackTrace();
    }
    catch(NotBoundException e) {
      e.printStackTrace();
    }catch(Exception e){
      e.printStackTrace();
    }
    return result;
  }

}
