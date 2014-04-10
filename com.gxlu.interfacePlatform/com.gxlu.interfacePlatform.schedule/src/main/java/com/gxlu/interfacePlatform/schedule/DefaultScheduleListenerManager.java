package com.gxlu.interfacePlatform.schedule;

import java.util.LinkedList;
import java.util.List;

public class DefaultScheduleListenerManager implements ScheduleListenerManager,ScheduleListener,ProgressListener {
	
	private static volatile DefaultScheduleListenerManager defaultScheduleListenerManager;
	
	public static DefaultScheduleListenerManager getDefaultScheduleListenerManager(){
		
		if(defaultScheduleListenerManager==null){
			defaultScheduleListenerManager=new DefaultScheduleListenerManager();
		}
		return defaultScheduleListenerManager;
	}
	private DefaultScheduleListenerManager(){}
	
	private List<ScheduleListener> listeners = new LinkedList<ScheduleListener>();
	private List<ProgressListener> progressListeners = new LinkedList<ProgressListener>();
	
	public  void addListener(ScheduleListener listener){
		listeners.add(listener);
	}
	
	
	public void removeListener(ScheduleListener listener){
		listeners.remove(listener);
	}
	
	public void removeAllListener(){
		listeners.removeAll(listeners);
	}
	
	public void onBefore(ScheduleContext context) {
		// TODO Auto-generated method stub
		for(ScheduleListener listener :listeners){
			listener.onBefore(context);
		}
	}
	
	public void onSuccess(ScheduleContext context) {
		// TODO Auto-generated method stub
		for(ScheduleListener listener :listeners){
			listener.onSuccess(context);
		}
	}
	
	public void onFailure(ScheduleContext context) {
		// TODO Auto-generated method stub
		for(ScheduleListener listener :listeners){
			listener.onFailure(context);
		}
	}
	public void onChangingProgress(Schedule schedule , int rate) {
		// TODO Auto-generated method stub
		for(ProgressListener listener :progressListeners){
			listener.onChangingProgress(schedule,rate);
		}
	}
	public void addListener(ProgressListener listener) {
		// TODO Auto-generated method stub
		this.progressListeners.add(listener);
	}
}
