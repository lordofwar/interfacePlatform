package com.gxlu.interfacePlatform.database.bean;

import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;

public class ScheduleLog implements Serializable {
	private static final long serialVersionUID = 6805527765128790794L;
	private Integer id;
	private String metacategory;
	private Integer interfacelibid;
	private Integer type;
	private String userName;
	private String loginName;
	private Integer category;
	private Date startedTime;
	private Date finishedTime;
	private Integer status;
	private String failedReason;
	private Integer version;
	private Clob detailLog;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMetacategory() {
		return metacategory;
	}

	public void setMetacategory(String metacategory) {
		this.metacategory = metacategory;
	}

	public Integer getInterfacelibid() {
		return interfacelibid;
	}

	public void setInterfacelibid(Integer interfacelibid) {
		this.interfacelibid = interfacelibid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Date getStartedTime() {
		return startedTime;
	}

	public void setStartedTime(Date startedTime) {
		this.startedTime = startedTime;
	}

	public Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Clob getDetailLog() {
		return detailLog;
	}

	public void setDetailLog(Clob detailLog) {
		this.detailLog = detailLog;
	}

}
