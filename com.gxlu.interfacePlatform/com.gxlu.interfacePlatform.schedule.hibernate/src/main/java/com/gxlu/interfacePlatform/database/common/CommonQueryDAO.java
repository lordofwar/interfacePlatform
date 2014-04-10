package com.gxlu.interfacePlatform.database.common;

import java.util.List;

public interface CommonQueryDAO {
  public abstract Object queryObject(String hql) throws Exception;

  public abstract List<Object> queryObjects(String hql)  throws Exception;
  
  public abstract List<Object> queryAll(Class<?> className)  throws Exception;

}
