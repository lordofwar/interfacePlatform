/**************************************************************************
 * $RCSfile: AbstractResultPagination.java,v $  $Revision: 1.6 $  $Date: 2010/01/27 05:45:31 $
 *
 * $Log: AbstractResultPagination.java,v $
 * Revision 1.6  2010/01/27 05:45:31  richie
 * *** empty log message ***
 *
 * Revision 1.5  2010/01/21 05:24:30  richie
 * no message
 *
 * Revision 1.4  2010/01/21 05:23:17  richie
 * no message
 *
 * Revision 1.3  2009/08/03 02:03:52  richie
 * *** empty log message ***
 *
 * Revision 1.2  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/12 07:05:04  richie
 * no message
 *
 **************************************************************************/
package com.gxlu.meta.engine.jdbc;

/**
 * AbstractResult Pagination realization. implements getCountSql method.
 * 
 * @author K
 */
public abstract class AbstractResultPagination implements ResultPagination {

  /**
   * repack sql by add "select count(*)" prefix.
   * @throws SqlExecutorException 
   * 
   * @see com.gxlu.meta.engine.jdbc.ResultPagination#getCountSql(String)
   */
  public String getCountSql(String sql) throws SqlExecutorException {
    if(!sql.toUpperCase().trim().startsWith("SELECT")) {
      throw new SqlExecutorException("Not a select sql: " + sql);
    }

    int startPoint = 0;
    int endPoint = sql.length();

    if(sql.toUpperCase().indexOf(" ORDER BY ") > 0) {
      endPoint = sql.toUpperCase().indexOf(" ORDER BY ");
    }

    if(sql.toUpperCase().indexOf("GROUP BY") < 0) {
      int fromPosition = sql.toUpperCase().indexOf(" FROM ");
      return " SELECT COUNT(1) " + sql.substring(fromPosition + 1, sql.length());
    }
    else {
      return "SELECT COUNT (1) FROM (" + sql.substring(startPoint, endPoint) + ")";  
    }
  }
}

