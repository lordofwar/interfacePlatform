/**************************************************************************
 * $RCSfile: DefaultMetaClassRelation.java,v $  $Revision: 1.7 $  $Date: 2009/12/25 03:40:18 $
 *
 * $Log: DefaultMetaClassRelation.java,v $
 * Revision 1.7  2009/12/25 03:40:18  liuding
 * *** empty log message ***
 *
 * Revision 1.6  2009/07/28 03:05:38  richie
 * *** empty log message ***
 *
 * Revision 1.5  2009/07/20 06:37:19  liuding
 * *** empty log message ***
 *
 * Revision 1.4  2009/02/27 02:02:00  richie
 * *** empty log message ***
 *
 * Revision 1.3  2009/01/19 02:14:44  liuding
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/24 02:57:35  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/19 09:40:06  richie
 * *** empty log message ***
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : DefaultMetaComponentRelation.java
 * Created on : Dec 19, 2008 3:28:31 PM
 * Creator : RichieJin
 */
package com.gxlu.meta.cfg;

import com.gxlu.meta.MetaClass;
import com.gxlu.meta.MetaClassRelation;

/**
 * <pre>
 * Description : TODO
 * @author RichieJin
 * </pre>
 */
public class DefaultMetaClassRelation implements MetaClassRelation {

  public MetaClass getRelationMetaClass() {
    return relationMetaClass;
  }

  public MetaClass getOwnerMetaClass() {
    return ownerMetaClass;
  }

  public void setRelationMetaClass(MetaClass relationMetaClass) {
    this.relationMetaClass = relationMetaClass;
  }

  public int getRelationType() {
    return relationType;
  }

  public void setRelationType(int relationType) {
    this.relationType = relationType;
  }

  public int getCascade() {
    return cascade;
  }

  public void setCascade(int cascade) {
    this.cascade = cascade;
  }

  public String getAlias() {
    return alias;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getVersionInstanceId() {
    return versionInstanceId;
  }

  public void setVersionInstanceId(long versionInstanceId) {
    this.versionInstanceId = versionInstanceId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setOwnerMetaClass(MetaClass ownerMetaClass) {
    this.ownerMetaClass = ownerMetaClass;
  }

  public boolean isSameRelationSpec(MetaClassRelation meteClassRelation) {
	  if(meteClassRelation == null){
		  return false;
	  }
    boolean isSame = false;
    String anotherCode = ((DefaultMetaClassRelation) meteClassRelation).getCode();
    if (anotherCode != null && code != null && anotherCode.equals(code)) {
      isSame = true;
    }

    return isSame;
  }
  public String getDisplayName() {
		return name;
  }
  
  	@Override
	public String toString() {
		return "RelationCode : "+code+" | "+ name;
	}

  private MetaClass relationMetaClass;
  private MetaClass ownerMetaClass;
  private int relationType;
  private int cascade;
  private String alias;
  private String name;
  private String code;
  private long id;
  private long versionInstanceId;
}
