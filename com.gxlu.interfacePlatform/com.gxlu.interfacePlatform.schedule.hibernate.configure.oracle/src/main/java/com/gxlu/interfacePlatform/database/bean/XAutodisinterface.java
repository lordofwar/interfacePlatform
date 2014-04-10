package com.gxlu.interfacePlatform.database.bean;

import java.util.Date;

public class XAutodisinterface {
  private Integer id;
  private Integer tasktype;
  private String taskname;
  private Date autodisstartdate;
  private Date autodisfinishdate;
  private String autodisstatus;
  private Long sendflag;
  private String errorreason;
  private String mail;
  private String ftp;
  private Long ruleid;
  private String sms;
  
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public Integer getTasktype() {
    return tasktype;
  }
  public void setTasktype(Integer tasktype) {
    this.tasktype = tasktype;
  }
  public String getTaskname() {
    return taskname;
  }
  public void setTaskname(String taskname) {
    this.taskname = taskname;
  }
  public Date getAutodisstartdate() {
    return autodisstartdate;
  }
  public void setAutodisstartdate(Date autodisstartdate) {
    this.autodisstartdate = autodisstartdate;
  }
  public Date getAutodisfinishdate() {
    return autodisfinishdate;
  }
  public void setAutodisfinishdate(Date autodisfinishdate) {
    this.autodisfinishdate = autodisfinishdate;
  }
  public String getAutodisstatus() {
    return autodisstatus;
  }
  public void setAutodisstatus(String autodisstatus) {
    this.autodisstatus = autodisstatus;
  }
  public Long getSendflag() {
    return sendflag;
  }
  public void setSendflag(Long sendflag) {
    this.sendflag = sendflag;
  }
  public String getErrorreason() {
    return errorreason;
  }
  public void setErrorreason(String errorreason) {
    this.errorreason = errorreason;
  }
  public String getMail() {
    return mail;
  }
  public void setMail(String mail) {
    this.mail = mail;
  }
  public String getFtp() {
    return ftp;
  }
  public void setFtp(String ftp) {
    this.ftp = ftp;
  }
  public Long getRuleid() {
    return ruleid;
  }
  public void setRuleid(Long ruleid) {
    this.ruleid = ruleid;
  }
  public String getSms() {
    return sms;
  }
  public void setSms(String sms) {
    this.sms = sms;
  }
}
