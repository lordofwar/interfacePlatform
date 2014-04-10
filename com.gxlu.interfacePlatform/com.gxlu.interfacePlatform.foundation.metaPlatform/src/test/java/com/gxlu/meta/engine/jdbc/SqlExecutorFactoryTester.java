/**************************************************************************
 * $RCSfile: SqlExecutorFactoryTester.java,v $  $Revision: 1.3 $  $Date: 2008/12/19 09:41:39 $
 *
 * $Log: SqlExecutorFactoryTester.java,v $
 * Revision 1.3  2008/12/19 09:41:39  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/18 09:25:22  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/16 07:35:03  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.engine.jdbc;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Ignore;

import com.gxlu.meta.tools.JdbcHelper;

/**
 * Test ExecutorFactory can be initialized correctly by giving a DataSource.
 *
 * @author K
 */
@Ignore
public class SqlExecutorFactoryTester {

  public SqlExecutorFactoryTester(String name){

    driver = "oracle.jdbc.driver.OracleDriver";
    url = "jdbc:oracle:thin:@172.16.5.220:1521:sdh";
    username = "qhng";
    password = "qhng";

    createTableSql = "create table meta_tablefortest (id number(18))";
    insertDateSql = "insert into meta_tablefortest values (1)";
    dropTableSql = "drop table meta_tablefortest";
  }

  protected void setUp() throws Exception {
    String message = JdbcHelper.execute(driver, url, username, password, createTableSql);
    System.out.println(message);
    message = JdbcHelper.execute(driver, url, username, password, insertDateSql);
    System.out.println(message);
    dataSource = new BasicDataSource();
    ((BasicDataSource) dataSource).setDriverClassName(driver);
    ((BasicDataSource) dataSource).setUrl(url);
    ((BasicDataSource) dataSource).setUsername(username);
    ((BasicDataSource) dataSource).setPassword(password);
    
    executorFactory = new ExecutorFactory(true);
    executorFactory.setDataSource(dataSource);
  }

  @AfterClass
  protected void tearDown() throws Exception {
    String message = JdbcHelper.execute(driver, url, username, password, dropTableSql);
    System.out.println(message);
  }

  public void testQuery() throws SqlExecutorException {
    int result = ExecutorFactory.getSqlExecutor().queryForInt("select count(*) from meta_tablefortest where 1 = 1", null);
    Assert.assertEquals(1, result);
  }

  private ExecutorFactory executorFactory;
  private DataSource dataSource;
  private String driver;
  private String username;
  private String password;
  private String url;
  private String createTableSql;
  private String insertDateSql;
  private String dropTableSql;
}

