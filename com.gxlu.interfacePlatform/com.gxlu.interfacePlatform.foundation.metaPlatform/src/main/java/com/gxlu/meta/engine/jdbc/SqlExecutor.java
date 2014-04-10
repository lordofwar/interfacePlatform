/**************************************************************************
 * $RCSfile: SqlExecutor.java,v $  $Revision: 1.11 $  $Date: 2010/05/15 08:51:53 $
 *
 * $Log: SqlExecutor.java,v $
 * Revision 1.11  2010/05/15 08:51:53  liuding
 * *** empty log message ***
 *
 * Revision 1.10  2009/11/20 06:51:11  richie
 * *** empty log message ***
 *
 * Revision 1.9  2009/10/21 05:12:40  shenwq
 * 调用存储过程，添加两个方法： 不需要出参的，和有多个出参，原来的方法默认只有一个出参
 *
 * Revision 1.8  2009/07/31 04:57:29  shenwq
 * call storedProc
 *
 * Revision 1.7  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.6  2009/05/08 02:40:26  richie
 * *** empty log message ***
 *
 * Revision 1.5  2009/01/08 05:06:39  richie
 * *** empty log message ***
 *
 * Revision 1.4  2009/01/05 03:33:00  richie
 * change QueryResult name to SqlQueryResult
 *
 * Revision 1.3  2008/12/19 09:40:07  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/16 05:51:48  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/12 07:05:04  richie
 * no message
 *
 **************************************************************************/
package com.gxlu.meta.engine.jdbc;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

/**
 * Execute sql statment and resolve the results.
 *  
 * @author K
 */
public interface SqlExecutor
{

    /**
     * Execute a sql statment. and add pagination support to the results.
     * besides sql statement is executed, and every queried record is encapsulated into
     * into a map. and records from requested start position to end position will be
     * assembled into a list. Also the total records that fit the where conditions will
     * be returned in QueryResult.total.
     * <pre>
     *      String sql = "select * from GIS97BSITE T where T.CODE=?";
     *      Object params = {new Long(1)};
     *      QueryResult result = query(sql, params, 0, 20);
     * </pre>
     * 
     * @param sql sql statement that will be executed.
     * @param params contains parameters that will be inject to sql when executing.
     * @param start for pagination, the returning first record' position.
     * @param maxCount max count for returning records.
     * @return QueryResult that contains a list of records and total count.
     */
    public SqlQueryResult query(String sql, Object[] params, int start, int maxCount)
    throws SqlExecutorException;

    /**
     * Execute a sql statment. and add pagination support to the results.
     * besides sql statement is executed, and every queried record is encapsulated into
     * into a map. and records from requested start position to end position will be
     * assembled into a list. Also the total records that fit the where conditions will
     * be returned in QueryResult.total.
     * <pre>
     *      String sql = "select * from GIS97BSITE T where T.CODE=?";
     *      Object params = {new Long(1)};
     *      QueryResult result = query(sql, params, 0, 20, new String[]{CODE}, new Byte[]{(byte) 1});
     * </pre>
     * 
     * @param sql sql statement that will be executed.
     * @param params contains parameters that will be inject to sql when executing.
     * @param start for pagination, the returning first record' position.
     * @param maxCount max count for returning records.
     * @param columnNames columns name for order by
     * @param isAsc order type for each colunm name, ASC means ASC, DESC means DESC
     * @return QueryResult that contains a list of records and total count.
     */
    public SqlQueryResult query(String sql, Object[] params, int start, int maxCount, String[] columnNames, String[] isAsc)
    throws SqlExecutorException;
    
    public SqlQueryResult query(String sql, Object[] params, int start, int maxCount, String[] columnNames, String[] isAsc,boolean isCount)
    throws SqlExecutorException;

    /**
     * Execute a sql statment. and add pagination support to the results.
     * besides sql statement is executed, and every queried record is encapsulated
     * into a map. and records from requested start position to end position will be
     * assembled into a list.
     * <pre>
     *      String sql = "select * from GIS97BSITE T where T.CODE=1";
     *      Object params = {new Long(1)};
     *      List list = query(sql, params, 0, 20);
     * </pre>
     * 
     * @param sql sql statement that will be executed.
     * @param params contains parameters that will be inject to sql when executing.
     * @param start for pagination, the returning first record' position.
     * @param maxCount max count for returning records.
     * @return List that contains a list of records.
     */
    public List<Map<String, Object>> queryList(String sql, Object[] params, int start, int maxCount) throws SqlExecutorException;
    

    /**
     * Execute a select sql statment. and return all records in a list.
     * besides sql statement is executed, and every queried record is encapsulated
     * into a map. 
     *
     * <pre>
     *      String sql = "select * from GIS97BSITE T where T.CODE=?";
     *      Object params = {new Long(1)};
     *      List result = query(sql, params);
     * </pre>
     * 
     * @param sql sql statement that will be executed.
     * @param params contains parameters that will be inject to sql when executing.
     * @return List that contains a list of all records.
     */
    public List<Map<String, Object>> query(String sql, Object[] params) throws SqlExecutorException;
    
    /**
     * Execute a sql statment and return SqlRowSet.
     * 
     * @param sql sql statement that will be executed.
     * @param params  contains parameters that will be inject to sql when executing.
     * @throws SqlExecutorException 
     */
    public int update(String sql, Object[] params) throws SqlExecutorException;

    /**
     * Execute a sql statment return a map that reprensents one record.
     * <pre>
     *     String sql = "select * from gis97bsite where id = ?";
     *     Long id = new Long(utilDao.createSequence("GIS97BSITE"));
     *     Object[] params = {id};
     *
     *     Map map = sqlExecutor.queryForOne(sql, params);
     * </pre>
     * 
     * @param sql sql statement that will be executed.
     * @param params contains parameters that will be inject to sql when executing.
     */
    public Map<String, Object> queryForMap(String sql, Object[] params) throws SqlExecutorException;

    /**
     * Query given SQL to create a prepared statement from SQL and a
     * list of arguments to bind to the query, resulting in a long value.
     * <p>The query is expected to be a single row/single column query that
     * results in a long value.
     * @param sql SQL query to execute
     * @param params contains parameters that will be inject to sql when executing.
     */
    long queryForLong(String sql, Object[] params) throws SqlExecutorException;

    /**
     * Query given SQL to create a prepared statement from SQL and a
     * list of arguments to bind to the query, resulting in a int value.
     * <p>The query is expected to be a single row/single column query that
     * results in a long value.
     * @param sql SQL query to execute
     * @param params contains parameters that will be inject to sql when executing.
     */
    int queryForInt(String sql, Object[] params) throws SqlExecutorException;

    /**
     * Query given SQL to create a prepared statement from SQL and a
     * list of arguments to bind to the query, resulting in a string value.
     * <p>The query is expected to be a single row/single column query that
     * results in a string value.
     * @param sql SQL query to execute
     * @param params contains parameters that will be inject to sql when executing.
     */
    String queryForString(String sql, Object[] params) throws SqlExecutorException;

    /**
     * Query given SQL to create a prepared statement from SQL and a
     * list of arguments to bind to the query, resulting in an object value.
     * <p>The query is expected to be a single row/single column query that
     * results in an object value.
     * @param sql SQL query to execute
     * @param params contains parameters that will be inject to sql when executing.
     * @param glass return object's Class
     */
    Object queryForObject(String sql, Object[] params, Class glass) throws SqlExecutorException;

    /**
     * Set the JDBC DataSource to be used by this DAO.
     */
    public void setDataSource(DataSource dataSource);

    /**
     * Set the JDBC Pagination support sql reassembly helper
     */
    public void setResultPagination(ResultPagination resultPagination);

    /**
     * Set if print sql.
     *
     * @param isPrintSql
     */
    public void setPrintSql(boolean isPrintSql);

    /**
     * Get QueryBuilder
     */
    public QueryBuilder getQueryBuilder();
    
    /**改方法的存储过程有一个Types.VARCHAR类型的出参，
     * call storedProcedur
     * @param storedProcName
     * @param paramValueList ,the value orderBy the paramIndex
     * @return 一般返回0或1 表示成功或失败
     * @throws SqlExecutorException
     */
    public  String executeSP(final String storedProcName,final Object [] inParamValueList) throws SqlExecutorException;
   
    /**
     *  有1至多个出参的存储过程
     * @param storedProcName
     * @param inParamValueList: the value orderBy the paramIndex
     * @param outParamTypeList : the type list of out param
     * @return 返回多个出参值 ，
     * @throws SqlExecutorException
     */
    public  Object [] executeSP(final String storedProcName,final Object [] inParamValueList,final int [] outParamTypeList) throws SqlExecutorException;

    /**
     * 调用没有出参的存储过程
     * @param storedProcName
     * @param paramValueList
     * @throws SqlExecutorException
     */
    public void executeSPNeedNotOutParam(final String storedProcName,final Object [] paramValueList) throws SqlExecutorException;

    /**
     * assemble sql with given parameters, and this sql will be executed by executor.
     * @param sql
     * @param params
     * @return
     */
    public String getExecutedSql(String sql, Object [] params);
}
