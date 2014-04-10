/**************************************************************************
 * $RCSfile: DefaultMetaData.java,v $  $Revision: 1.3 $  $Date: 2009/03/23 01:31:01 $
 *
 * $Log: DefaultMetaData.java,v $
 * Revision 1.3  2009/03/23 01:31:01  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/31 04:44:41  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/26 05:55:24  richie
 * *** empty log message ***
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : DefaultMetaData.java
 * Created on : Dec 25, 2008 7:45:07 PM
 * Creator : RichieJin
 */
package com.gxlu.meta.cfg;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gxlu.meta.MetaAttribute;
import com.gxlu.meta.MetaData;

/**
 * <pre>
 * Description : TODO
 * @author RichieJin
 * </pre>
 */
public abstract  class DefaultMetaData implements MetaData {

  public DefaultMetaData(){
    maps = new HashMap<String, Object>();
  }

  public abstract List<MetaAttribute> getAttributeList();

  public BigDecimal getBigDecimal(String attributeName) {
    Object object = maps.get(attributeName);

    if(object != null) {
      return (BigDecimal) object;
    }
    else {
      return null;
    }
  }

  public Boolean getBoolean(String attributeName) {
    Object object = maps.get(attributeName);

    if(object != null) {
      return (Boolean) object;
    }
    else {
      return null;
    }
  }

  public Byte getByte(String attributeName) {
    Object object = maps.get(attributeName);

    if(object != null) {
      return (Byte) object;
    }
    else {
      return null;
    }
  }

  public Date getDate(String attributeName) {
    Object object = maps.get(attributeName);

    if(object != null) {
      return (Date) object;
    }
    else {
      return null;
    }
  }

  public Double getDouble(String attributeName) {
    Object object = maps.get(attributeName);

    if(object != null) {
      return (Double) object;
    }
    else {
      return null;
    }
  }

  public Float getFloat(String attributeName) {
    Object object = maps.get(attributeName);

    if(object != null) {
      return (Float) object;
    }
    else {
      return null;
    }
  }

  public Integer getInt(String attributeName) {
    Object object = maps.get(attributeName);

    if(object != null) {
      return (Integer) object;
    }
    else {
      return null;
    }
  }

  public Long getLong(String attributeName) {
    Object object = maps.get(attributeName);

    if(object != null) {
      return (Long) object;
    }
    else {
      return null;
    }
  }

  public Short getShort(String attributeName) {
    Object object = maps.get(attributeName);

    if(object != null) {
      return (Short) object;
    }
    else {
      return null;
    }
  }

  public String getString(String attributeName) {
    Object object = maps.get(attributeName);

    if(object != null) {
      return (String) object;
    }
    else {
      return null;
    }
  }

  public Object getValue(String attribute) {
    return maps.get(attribute);
  }

  public void setValue(String attributeName, Boolean value) {
    maps.put(attributeName, value);
  }

  public void setValue(String attributeName, Byte value) {
    maps.put(attributeName, value);
  }

  public void setValue(String attributeName, Double value) {
    maps.put(attributeName, value);
  }

  public void setValue(String attributeName, Float value) {
    maps.put(attributeName, value);
  }

  public void setValue(String attributeName, Integer value) {
    maps.put(attributeName, value);
  }

  public void setValue(String attributeName, Long value) {
    maps.put(attributeName, value);
  }

  public void setValue(String attributeName, Short value) {
    maps.put(attributeName, value);
  }

  public void setValue(String attributeName, BigDecimal value) {
    maps.put(attributeName, value);
  }

  public void setValue(String attributeName, Date value) {
    maps.put(attributeName, value);
  }

  public void setValue(String attributeName, String value) {
    maps.put(attributeName, value);
  }

  public void setValue(String attributeName, Object value) {
    maps.put(attributeName, value);
  }

  public Map<String, Object> getMaps() {
    return maps;
  }

  public void setMaps(Map<String, Object> maps) {
    this.maps = maps;
  }

  
  Map<String, Object> maps;
}
