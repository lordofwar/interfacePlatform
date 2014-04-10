/**************************************************************************
 * $RCSfile: DefaultMetaComponent.java,v $  $Revision: 1.5 $  $Date: 2009/06/17 05:56:10 $
 *
 * $Log: DefaultMetaComponent.java,v $
 * Revision 1.5  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.4  2009/05/08 05:09:28  liuding
 * *** empty log message ***
 *
 * Revision 1.3  2009/02/27 02:02:00  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/26 05:55:23  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/19 09:40:06  richie
 * *** empty log message ***
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : DefaultMetaComponent.java
 * Created on : Dec 19, 2008 3:33:35 PM
 * Creator : RichieJin
 */
package com.gxlu.meta.cfg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gxlu.meta.MetaAttribute;
import com.gxlu.meta.MetaComponent;

/**
 * <pre>
 * Description : TODO
 * @author RichieJin
 * </pre>
 */
public class DefaultMetaComponent implements MetaComponent {

  public void setMetaAttributes(List<MetaAttribute> metaAttributes) {
	  
	  if(this.getCode().equals("SIMULATEUSERCARD")){
	  }
    this.metaAttributes = metaAttributes;
  }

  public MetaAttribute findMetaAttribute(String key) {
    if (metaAttributesMap == null) {
      metaAttributesMap = new HashMap<String, MetaAttribute>();
      for (MetaAttribute attribute : metaAttributes) {
        metaAttributesMap.put(attribute.getAttributeName(), attribute);
      }
    }

    return metaAttributesMap.get(key);
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public List<MetaAttribute> getMetaAttributes() {

    return metaAttributes;
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

  public String getCode() {

    return code;
  }

  public String getDisplayName() {

    return displayName;
  }

  private List<MetaAttribute> metaAttributes;
  private String code;
  private String displayName;
  private Map<String, MetaAttribute> metaAttributesMap;
  private long id;
  private long versionInstanceId;
}

