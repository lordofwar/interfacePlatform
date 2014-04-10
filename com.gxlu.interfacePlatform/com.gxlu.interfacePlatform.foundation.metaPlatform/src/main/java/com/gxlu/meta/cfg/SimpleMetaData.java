package com.gxlu.meta.cfg;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class SimpleMetaData {


  public SimpleMetaData(){
    maps = new HashMap<String, Object>();
  }

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
    for (String key : maps.keySet()) {
      if (!(maps.get(key) instanceof java.util.Date) && !(maps.get(key) instanceof java.sql.Date)) {
        this.maps.put(key, maps.get(key));
      }
    }
  }

  
  Map<String, Object> maps;
}
