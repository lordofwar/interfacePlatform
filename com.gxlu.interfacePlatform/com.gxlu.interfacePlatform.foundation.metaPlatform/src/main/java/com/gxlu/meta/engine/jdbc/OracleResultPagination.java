/**************************************************************************
 * $RCSfile: OracleResultPagination.java,v $  $Revision: 1.5 $  $Date: 2009/12/30 03:18:55 $
 *
 * $Log: OracleResultPagination.java,v $
 * Revision 1.5  2009/12/30 03:18:55  richie
 * *** empty log message ***
 *
 * Revision 1.4  2009/10/17 17:47:00  liuding
 * *** empty log message ***
 *
 * Revision 1.3  2009/09/08 03:21:39  liuding
 * support DBMS_RANDOM.VALUE
 *
 * Revision 1.2  2009/08/03 01:59:15  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/12 07:05:04  richie
 * no message
 *
 **************************************************************************/
package com.gxlu.meta.engine.jdbc;

/**
 * Oracle Pagination realization.
 * 
 * @author K
 */
public class OracleResultPagination extends AbstractResultPagination {

  /**
   * realized by using keywords "minus".
   * 
   * @see com.gxlu.meta.engine.jdbc.ResultPagination#addPaginationSql(String, int, int)
   */
  public String addPaginationSql(String sql, int start, int maxCount) {
    StringBuffer buffer = new StringBuffer();

    if(start > 1) {
      buffer.append("select leftresult_.* from (");
      buffer.append(sql);
      buffer.append(") leftresult_ where  rownum < ");
      buffer.append(start + maxCount + 1);
      buffer.append(" minus ");
      buffer.append("select rightresult_.* from (");
      buffer.append(sql);
      buffer.append(") rightresult_ where  rownum < ");
      buffer.append(start + 1);
    }
    else {
      //          buffer.append(sql);
      //          if (sql.toLowerCase().indexOf("where") > 0) {
      //            buffer.append(" and rownum < ");
      //          }
      //          else {
      //            buffer.append(" where rownum < ");
      //          }
      //          buffer.append(start + maxCount + 1);
      buffer.append("SELECT * FROM (");
      buffer.append(sql);
      buffer.append(" )");
      buffer.append(" WHERE ROWNUM < ");
      buffer.append(start + maxCount + 1);
    }

    return buffer.toString();
  }

  /**
   * realized by using keywords "minus".
   * 
   * @see com.gxlu.meta.engine.jdbc.ResultPagination#addPaginationSql(String, String, int, int)
   */
  public String addPaginationSql(String sql, String orderBy, int start, int maxCount) {
    StringBuffer buffer = new StringBuffer();

    if(orderBy != null && orderBy.indexOf("DBMS_RANDOM.VALUE") != -1) {
      // ÌØÊâµÄorderby
      buffer.append("select * from (");
      buffer.append(sql);
      buffer.append(" " + orderBy);
      buffer.append(" ) totalresult ");
      buffer.append(" where rownum < ");
      buffer.append(start + maxCount + 1);
    }
    else {
      buffer.append("select * from (");
      buffer.append(addPaginationSql(sql + " " + orderBy, start, maxCount));
      buffer.append(" ) totalresult ");
      buffer.append(" " + orderBy);
    }
    return buffer.toString();
  }
}
