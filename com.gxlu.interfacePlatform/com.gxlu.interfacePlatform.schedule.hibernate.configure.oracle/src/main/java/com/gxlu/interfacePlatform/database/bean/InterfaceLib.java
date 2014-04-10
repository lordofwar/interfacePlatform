package com.gxlu.interfacePlatform.database.bean;

public class InterfaceLib {
  private Integer id;
  private String metacategory;
  private String code;
  private Integer type;
  private String description;
  private Integer isActive;
  private Integer isloadingDetaillog;
  private Integer version;
  
  public Integer getVersion() {
    return version;
  }
  public void setVersion(Integer version) {
    this.version = version;
  }
  public Integer getId() {
    return id;
  }
  public void setId(Integer Id) {
    id = Id;
  }
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public Integer getType() {
    return type;
  }
  public void setType(Integer type) {
    this.type = type;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Integer getIsActive() {
    return isActive;
  }
  public void setIsActive(Integer isActive) {
    this.isActive = isActive;
  }
  public Integer getIsloadingDetaillog() {
    return isloadingDetaillog;
  }
  public void setIsloadingDetaillog(Integer isloadingDetaillog) {
    this.isloadingDetaillog = isloadingDetaillog;
  }
  public String getMetacategory() {
    return metacategory;
  }
  public void setMetacategory(String metacategory) {
    this.metacategory = metacategory;
  }
  
}
