package com.gxlu.interfacePlatform.database.bean;

public class XbAutoDiscoveryRule {
  private Integer id;
  private XbAlertrule xbAlertrule;
  private String metacategory;
  private Long version;
  private Long code;
  private Integer autodisname;
  private Integer autodisstatus;
  
  public XbAlertrule getXbAlertrule() {
    return xbAlertrule;
  }
  public void setXbAlertrule(XbAlertrule xbAlertrule) {
    this.xbAlertrule = xbAlertrule;
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
  public Long getCode() {
    return code;
  }
  public void setCode(Long code) {
    this.code = code;
  }
  public Integer getAutodisname() {
    return autodisname;
  }
  public void setAutodisname(Integer autodisname) {
    this.autodisname = autodisname;
  }
  public Integer getAutodisstatus() {
    return autodisstatus;
  }
  public void setAutodisstatus(Integer autodisstatus) {
    this.autodisstatus = autodisstatus;
  }
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

}
