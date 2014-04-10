package com.gxlu.interfacePlatform.schedule;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity Bean specify information of a schedule.
 * 
 * @author pudding
 * 
 */
public class Schedule implements Serializable {
	public static final Integer STATUS_STOPPED=1;
	public static final Integer STATUS_PENDING=2;
	public static final Integer STATUS_INPROGRESS=3;
	
	public static final Integer CATEGORY_COLLECTION =1;
	public static final Integer CATEGORY_COMPARISON =2;
	public static final Integer CATEGORY_NBI =3;
	
	public static final Integer TYPE_AUTO =1;
	public static final Integer TYPE_MANUAL=2;
	
	private static final long serialVersionUID = -7587430290549824169L;
	private Integer id;
	private String handlerClassName;
	private String METACATEGORY;
	private Long Interfacelibid;
	private Date lastRunTime;
	private Date lastFinishTime;
	private Date nextRunTime;
	private Long runPeriod;
	private Integer Status;
	private Integer Progress;
	private String Comments;
	private String rmiaddr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHandlerClassName() {
		return handlerClassName;
	}

	public void setHandlerClassName(String handlerClassName) {
		this.handlerClassName = handlerClassName;
	}

	public String getMETACATEGORY() {
		return METACATEGORY;
	}

	public void setMETACATEGORY(String mETACATEGORY) {
		METACATEGORY = mETACATEGORY;
	}

	public Long getInterfacelibid() {
		return Interfacelibid;
	}

	public void setInterfacelibid(Long interfacelibid) {
		Interfacelibid = interfacelibid;
	}

	public Date getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(Date lastRunTime) {
		this.lastRunTime = lastRunTime;
	}

	public Date getLastFinishTime() {
		return lastFinishTime;
	}

	public void setLastFinishTime(Date lastFinishTime) {
		this.lastFinishTime = lastFinishTime;
	}

	public Date getNextRunTime() {
		return nextRunTime;
	}

	public void setNextRunTime(Date nextRunTime) {
		this.nextRunTime = nextRunTime;
	}

	public Long getRunPeriod() {
		return runPeriod;
	}

	public void setRunPeriod(Long runPeriod) {
		this.runPeriod = runPeriod;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	public Integer getProgress() {
		return Progress;
	}

	public void setProgress(Integer progress) {
		Progress = progress;
	}

	public String getComments() {
		return Comments;
	}

	public void setComments(String comments) {
		Comments = comments;
	}

	public String getRmiaddr() {
		return rmiaddr;
	}

	public void setRmiaddr(String rmiaddr) {
		this.rmiaddr = rmiaddr;
	}
	
	public boolean isStop(){
		if(this.Status==STATUS_STOPPED || this.Status==null){
			return true;
		}
		return false;
	}
	
	public boolean isInvalid(){
		if(getHandlerClassName() ==null || getHandlerClassName().equals("")){
			return true;
		}
		return false;
	}
	
	/*
	 * ����schedule��ʼ���е�ʱ��
	 * scheduleĬ�Ͽ�ʼʱ��Ϊ��ʱ����Ϊ��ǰʱ�䣬С�ڵ�ǰʱ��ʱ�ظ������������ֱ�����ڵ�ǰʱ�䣬���ڵ�ǰʱ��ʱֱ�����á�
	 * 
	 */
	public Date calculateStartTime() {
		Date current = new Date();
		Date startTime = this.getNextRunTime();
		Long runPeriod = this.getRunPeriod();
		
		if(startTime==null)
			return current;
		
		if (runPeriod != null) {
			while (startTime.compareTo(current) != 1) {
				startTime = new Date(startTime.getTime() + runPeriod * 60 * 1000);
			}
		} 
		
		return startTime;
	}

}
