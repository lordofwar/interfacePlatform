/**************************************************************************
 * $RCSfile: MetaClassOperationSqlCacheLoader.java,v $  $Revision: 1.3 $  $Date: 2009/06/17 05:56:10 $
 *
 * $Log: MetaClassOperationSqlCacheLoader.java,v $
 * Revision 1.3  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/19 09:40:07  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/18 09:23:51  richie
 * *** empty log message ***
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : MetaClassOperationSqlCacheLoader.java
 * Created on : Dec 18, 2008 1:27:06 PM
 * Creator : RichieJin
 */
package com.gxlu.meta.cache;

import java.util.List;
import java.util.Map;

import com.gxlu.meta.Cachable;
import com.gxlu.meta.engine.jdbc.SqlExecutorException;
import com.gxlu.meta.tools.MetaConsts;

/**
 * @author K
 */
public class MetaClassOperationSqlCacheLoader extends AbstractCacheLoader<String, String> {

  @Override
  public Cachable<String, String> reload() throws SqlExecutorException {

    Cachable<String, String> cache = new DefaultCache<String, String>();

    String sql = "SELECT * FROM " + MetaConsts.METACLASSLOAD_SQL_TABLE_NAME;
    List<Map<String, Object>> results = getSqlExecutor().query(sql, null);
    for(Map<String, Object> item : results) {
      cache.put((String)item.get(MetaConsts.METACLASSLOAD_SQL_COLUMN_NAME), (String)item.get(MetaConsts.METACLASSLOAD_SQL_COLUMN_VALUE));
    }

    return cache;
  }

  public Cachable<String, String> reload(List<String> list) throws SqlExecutorException {

    Cachable<String, String> cache = new DefaultCache<String, String>();

    String sql = "SELECT * FROM " + MetaConsts.METACLASSLOAD_SQL_TABLE_NAME + " WHERE " + MetaConsts.METACLASSLOAD_SQL_TABLE_NAME + "=?";
    for (String name : list) {
      String value = getSqlExecutor().queryForString(sql, new String[]{name});
      cache.put(name, value);
    }
    
    return cache;
  }

}
