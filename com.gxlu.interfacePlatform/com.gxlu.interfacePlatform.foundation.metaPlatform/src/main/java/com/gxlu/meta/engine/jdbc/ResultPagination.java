/**************************************************************************
 * $RCSfile: ResultPagination.java,v $  $Revision: 1.2 $  $Date: 2009/06/17 05:56:10 $
 *
 * $Log: ResultPagination.java,v $
 * Revision 1.2  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/12 07:05:04  richie
 * no message
 *
 **************************************************************************/
package com.gxlu.meta.engine.jdbc;

/**
 * Result pagination support by making up sql. to support pagination two kinds 
 * of sql should be provided: sql for querying records total amount and sql for 
 * querying special rows of records. 
 * 
 * @author K
 */
public interface ResultPagination {

  /**
   * for any select sql statement, make a Relevant sql statement for 
   * only return appointed rows of records.
   *
   * @param sql query sql statement will to be executed.
   * @param start begin rows
   * @param maxCount max rows
   * @return maked up sql.
   */
  public String addPaginationSql(String sql, int start, int maxCount);

  /**
   * for any select sql statement, make a Relevant sql statement for 
   * query total amount for original sql statement executed result.
   *
   * @param sql query sql statement will to be executed.
   * @return sql statement to query for total amount. 
   * @throws SqlExecutorException 
   */
  public String getCountSql(String sql) throws SqlExecutorException;

  /**
   * for any select sql statement, make a Relevant sql statement for 
   * only return appointed rows of records.
   *
   * @param sql query sql statement will to be executed.
   * @param orderBy order sql's subfix
   * @param start begin rows
   * @param maxCount max rows
   * @return maked up sql.
   */
  public String addPaginationSql(String sql, String orderBy, int start, int maxCount);
}
