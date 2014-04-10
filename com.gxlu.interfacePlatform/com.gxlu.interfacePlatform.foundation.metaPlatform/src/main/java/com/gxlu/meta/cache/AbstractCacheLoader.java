/**************************************************************************
 * $RCSfile: AbstractCacheLoader.java,v $  $Revision: 1.2 $  $Date: 2009/06/17 05:56:10 $
 *
 * $Log: AbstractCacheLoader.java,v $
 * Revision 1.2  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/18 09:23:52  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.cache;

import com.gxlu.meta.Cachable;
import com.gxlu.meta.engine.jdbc.SqlExecutor;
import com.gxlu.meta.engine.jdbc.SqlExecutorException;

/**
 * @author K
 */
public abstract class AbstractCacheLoader<K, V> implements CacheLoader<K, V> {

  public abstract Cachable<K, V> reload() throws SqlExecutorException;

  /* (non-Javadoc)
   * @see com.gxlu.meta.loader.CacheLoader#setSqlExecutor(com.gxlu.meta.engine.jdbc.SqlExecutor)
   */
  public void setSqlExecutor(SqlExecutor sqlExecutor) {
    this.sqlExecutor = sqlExecutor;
  }

  public SqlExecutor getSqlExecutor() {
    return sqlExecutor;
  }

  private SqlExecutor sqlExecutor;
}

