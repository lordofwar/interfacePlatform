package com.gxlu.interfacePlatform.schedule;

import org.quartz.Job;
import org.quartz.Trigger;

public class InternalSchedule {

	private Trigger trigger;
	private Class<? extends Job> clazz;

	public InternalSchedule(Trigger trigger, Class<? extends Job> clazz) {
		super();
		this.trigger = trigger;
		this.clazz = clazz;
	}

	public InternalSchedule() {
		// TODO Auto-generated constructor stub
	}

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}

	public Class<? extends Job> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends Job> clazz) {
		this.clazz = clazz;
	}

}
