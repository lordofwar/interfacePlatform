package com.gxlu.interfacePlatform.database.bean;

import java.util.Date;

public class XbAlertrule {
  private Integer id;
  private Integer UserId;
  private String metacategory;
  private Long version;
  private String name;
  private Integer type;
  private Date createdate;
  private Integer status;
  private String email;
  private String ftp;
  private String persmstime;
  private String sms;
  
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public Integer getUserId() {
    return UserId;
  }
  public void setUserId(Integer userId) {
    UserId = userId;
  }
  public String getMetacategory() {
    return metacategory;
  }
  public void setMetacategory(String metacategory) {
    this.metacategory = metacategory;
  }
  public Long getVersion() {
    return version;
  }
  public void setVersion(Long version) {
    this.version = version;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Integer getType() {
    return type;
  }
  public void setType(Integer type) {
    this.type = type;
  }
  public Date getCreatedate() {
    return createdate;
  }
  public void setCreatedate(Date createdate) {
    this.createdate = createdate;
  }
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getFtp() {
    return ftp;
  }
  public void setFtp(String ftp) {
    this.ftp = ftp;
  }
  public String getPersmstime() {
    return persmstime;
  }
  public void setPersmstime(String persmstime) {
    this.persmstime = persmstime;
  }
  public String getSms() {
    return sms;
  }
  public void setSms(String sms) {
    this.sms = sms;
  } 
  
}
