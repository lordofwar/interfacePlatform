/**************************************************************************
 * $RCSfile: ExecutorFactory.java,v $  $Revision: 1.4 $  $Date: 2008/12/19 09:40:07 $
 *
 * $Log: ExecutorFactory.java,v $
 * Revision 1.4  2008/12/19 09:40:07  richie
 * *** empty log message ***
 *
 * Revision 1.3  2008/12/18 09:24:24  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/16 06:53:19  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/16 05:51:48  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.engine.jdbc;

import javax.sql.DataSource;

/**
 * @author K
 */
public class ExecutorFactory {

  public ExecutorFactory(boolean isPrintSql) {
    if (self == null) {
      self = this;
    }
    this.isPrintSql = isPrintSql;
  }

  public static SqlExecutor getSqlExecutor() {
    
    if (sqlExecutor == null) {
      sqlExecutor = new SpringJdbcSqlExecutor();
      sqlExecutor.setDataSource(self.dataSource);
      sqlExecutor.setPrintSql(self.isPrintSql);
      ResultPagination resultPagination = new OracleResultPagination();
      sqlExecutor.setResultPagination(resultPagination);
    }
    
    return sqlExecutor;
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  private DataSource dataSource;
  private static SqlExecutor sqlExecutor;
  private static ExecutorFactory self;
  private boolean isPrintSql;
}
