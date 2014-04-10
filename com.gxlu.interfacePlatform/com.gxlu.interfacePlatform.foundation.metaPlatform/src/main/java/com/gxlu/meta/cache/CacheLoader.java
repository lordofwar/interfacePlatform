/**************************************************************************
 * $RCSfile: CacheLoader.java,v $  $Revision: 1.2 $  $Date: 2009/06/17 05:56:10 $
 *
 * $Log: CacheLoader.java,v $
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
public interface CacheLoader<K, V> {

  public Cachable<K, V> reload() throws SqlExecutorException;

  public void setSqlExecutor(SqlExecutor sqlExecutor);
}

