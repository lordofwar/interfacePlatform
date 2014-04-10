/**************************************************************************
 * $RCSfile: SpringJdbcSqlExecutor.java,v $  $Revision: 1.32 $  $Date: 2010/06/05 03:50:59 $
 *
 * $Log: SpringJdbcSqlExecutor.java,v $
 * Revision 1.32  2010/06/05 03:50:59  liuding
 * *** empty log message ***
 *
 * Revision 1.31  2010/06/04 08:38:38  liuding
 * *** empty log message ***
 *
 * Revision 1.30  2010/05/15 08:51:53  liuding
 * *** empty log message ***
 *
 * Revision 1.29  2010/01/27 03:11:37  liuding
 * *** empty log message ***
 *
 * Revision 1.28  2009/12/28 03:25:01  liuding
 * *** empty log message ***
 *
 * Revision 1.27  2009/12/09 07:07:14  liuding
 * *** empty log message ***
 *
 * Revision 1.26  2009/11/20 06:51:11  richie
 * *** empty log message ***
 *
 * Revision 1.25  2009/11/07 03:45:36  richie
 * *** empty log message ***
 *
 * Revision 1.24  2009/10/21 05:12:40  shenwq
 * 调用存储过程，添加两个方法： 不需要出参的，和有多个出参，原来的方法默认只有一个出参
 *
 * Revision 1.23  2009/10/15 01:39:29  richie
 * *** empty log message ***
 *
 * Revision 1.22  2009/10/15 01:30:56  richie
 * *** empty log message ***
 *
 * Revision 1.21  2009/10/07 14:40:46  hongbow
 * MR#:Telant0000 system。out
 *
 * Revision 1.20  2009/10/07 12:44:02  hongbow
 * MR#:Telant0000 执行存储过程的setautocommit暂时屏蔽
 *
 * Revision 1.19  2009/09/08 03:48:17  liuding
 * *** empty log message ***
 *
 * Revision 1.18  2009/09/08 03:21:50  liuding
 * support DBMS_RANDOM.VALUE
 *
 * Revision 1.17  2009/09/07 08:00:12  liuding
 * *** empty log message ***
 *
 * Revision 1.16  2009/08/03 02:14:47  richie
 * *** empty log message ***
 *
 * Revision 1.15  2009/08/03 01:59:15  richie
 * *** empty log message ***
 *
 * Revision 1.14  2009/07/31 04:57:19  shenwq
 * call storedProc
 *
 * Revision 1.13  2009/07/15 03:19:14  liuding
 * *** empty log message ***
 *
 * Revision 1.12  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.11  2009/06/05 02:45:29  richie
 * *** empty log message ***
 *
 * Revision 1.10  2009/03/23 09:12:58  richie
 * *** empty log message ***
 *
 * Revision 1.9  2009/01/08 05:06:38  richie
 * *** empty log message ***
 *
 * Revision 1.8  2009/01/05 03:33:00  richie
 * change QueryResult name to SqlQueryResult
 *
 * Revision 1.7  2009/01/05 03:18:34  richie
 * *** empty log message ***
 *
 * Revision 1.6  2008/12/31 04:44:41  richie
 * *** empty log message ***
 *
 * Revision 1.5  2008/12/26 05:55:22  richie
 * *** empty log message ***
 *
 * Revision 1.4  2008/12/24 02:57:44  richie
 * *** empty log message ***
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

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import com.gxlu.meta.i18n.I18N;

/**
 * Using Spring JDBCTemplate to implement SqlExcutor. to use it, a data source
 * and a pagination support should be inject into
 * 
 * @author K
 */
public class SpringJdbcSqlExecutor extends JdbcDaoSupport implements SqlExecutor {

	private static Logger logger = Logger.getLogger(SpringJdbcSqlExecutor.class);

	public int update(String sql, Object[] params) throws SqlExecutorException {
		long time = System.currentTimeMillis();
		trimParams(params);
		long executeTime = 0;
		try {
			int retInt = getJdbcTemplate().update(sql, params);
			executeTime = System.currentTimeMillis() - time;
			return retInt;
		} catch (DataAccessException exception) {
			throw new SqlExecutorException("", exception);
		} finally {
			printSql(sql, params, executeTime);
		}
	}

	public void executeSPNeedNotOutParam(final String storedProcName, final Object[] paramValueList)
			throws SqlExecutorException {
		executeSP(storedProcName, paramValueList, null);
	}

	public String executeSP(final String storedProcName, final Object[] paramValueList) throws SqlExecutorException {

		final int[] outParamTypeList = new int[1];
		outParamTypeList[0] = Types.VARCHAR;
		Object[] outResult = executeSP(storedProcName, paramValueList, outParamTypeList);
		if (outResult != null && outResult.length > 0)
			return (String) outResult[0];
		return null;
	}

	public Object[] executeSP(final String storedProcName, final Object[] inParamValueList, final int[] outParamTypeList)
			throws SqlExecutorException {
		Object[] outResult = null;
		long time = System.currentTimeMillis();
		long executeTime = 0;
		try {
			Object obj = getJdbcTemplate().execute(new ConnectionCallback() {
				public Object doInConnection(Connection conn) throws SQLException, DataAccessException {
					// conn.setAutoCommit(true);
					StringBuffer storedProcStr = new StringBuffer("call ");
					storedProcStr.append(storedProcName + "(");
					if (inParamValueList != null && inParamValueList.length > 0) {
						for (int i = 0; i < inParamValueList.length; i++) {
							if (i > 0) {
								storedProcStr.append(",");
							}
							storedProcStr.append("?");

						}
					}
					if (outParamTypeList != null && outParamTypeList.length > 0) {
						for (int i = 0; i < outParamTypeList.length; i++) {
							if (inParamValueList != null && inParamValueList.length > 0) {
								storedProcStr.append(",");
							}
							storedProcStr.append("?");

						}
					}
					storedProcStr.append(")");
					CallableStatement cstmt = conn.prepareCall(storedProcStr.toString());
					int outParameterIndex = 1;
					if (inParamValueList != null && inParamValueList.length > 0) {
						for (int i = 1; i <= inParamValueList.length; i++) {
							cstmt.setObject(i, inParamValueList[i - 1]);
						}
						outParameterIndex = inParamValueList.length + 1;
					}
					if (outParamTypeList != null && outParamTypeList.length > 0) {
						for (int i = 0; i < outParamTypeList.length; i++) {
							cstmt.registerOutParameter(outParameterIndex + i, outParamTypeList[i]);
						}
					}
					Object[] outResult = null;

					printStoreProc(storedProcStr.toString(), inParamValueList, outParamTypeList, 0);
					cstmt.execute();

					if (outParamTypeList != null && outParamTypeList.length > 0) {
						outResult = new Object[outParamTypeList.length];
						for (int i = 0; i < outParamTypeList.length; i++) {
							outResult[i] = cstmt.getObject(outParameterIndex + i);
						}
					}

					cstmt.close();
					// conn.setAutoCommit(false);
					return outResult;
				}
			});
			if (obj != null) {
				outResult = (Object[]) obj;
			}
			executeTime = System.currentTimeMillis() - time;
			return outResult;
		} catch (DataAccessException exception) {
			throw new SqlExecutorException("", exception);
		} finally {
			printStoreProc(storedProcName, inParamValueList, outParamTypeList, executeTime);
		}
	}

	public SqlQueryResult query(String sql, Object[] params, int start, int maxCount) throws SqlExecutorException {
		return query(sql, params, start, maxCount, true);
	}

	private SqlQueryResult query(String sql, Object[] params, int start, int maxCount, boolean isCount)
			throws SqlExecutorException {
		Object[] newParams = null;
		if (params != null) {
			newParams = new Object[params.length * 2];
			for (int i = 0; i < params.length * 2; i++) {
				newParams[i] = params[i % params.length];
			}
		}
		String localSql = resultPagination.addPaginationSql(sql, start, maxCount);

		SqlRowSet results;
		if (start > 1) {
			results = getQueryResults(newParams, localSql);
		} else {
			results = getQueryResults(params, localSql);
		}

		List<Map<String, Object>> records = assembleQueryResult(results);
		long total = 0;
		if (isCount) {
			total = getTotalCount(sql, params, records, start, maxCount);
		}
		return new SqlQueryResult(total, records);
	}

	public SqlQueryResult query(String sql, Object[] params, int start, int maxCount, String[] columnNames,
			String[] isAsc) throws SqlExecutorException {
		return query(sql, params, start, maxCount, columnNames, isAsc, true);
	}

	public SqlQueryResult query(String sql, Object[] params, int start, int maxCount, String[] columnNames,
			String[] isAsc, boolean isCount) throws SqlExecutorException {
		String retSql = sql;
		String orderBySql = "ORDER BY ";
		if (columnNames != null && columnNames.length != 0) {
			String suffix = "";
			int startPoint = sql.toUpperCase().indexOf(" ORDER BY ");
			if (startPoint > 0) {
				int point = startPoint + " ORDER BY ".length();
				retSql = sql.substring(0, startPoint);
				suffix = "," + sql.substring(point, sql.length());
			}
			// retSql += " ORDER BY ";
			String theSort = "";
			String dbmsRandom = null;
			for (int i = 0; i < columnNames.length; i++) {
				if (columnNames[i].equalsIgnoreCase("dbms_random.value")) {
					dbmsRandom = columnNames[i];
				} else {
					String sortType = isAsc[i];
					theSort += "," + columnNames[i].toUpperCase() + " " + sortType;
				}
			}
			theSort = theSort.substring(theSort.indexOf(",") + 1);
			// retSql += parseSortStr(theSort) + suffix;
			if (dbmsRandom != null) {
				dbmsRandom = dbmsRandom.toUpperCase();
				if (theSort.length() > 0) {
					dbmsRandom = " ," + dbmsRandom;
				}
			} else {
				dbmsRandom = "";
			}
			orderBySql += parseSortStr(theSort) + dbmsRandom + suffix;
			// String orderBySql = "ORDER BY " + parseSortStr(columnNames[0]);
			// orderBySql += " " + isAsc[0] + " ";

			return query(retSql, orderBySql, params, start, maxCount, isCount);
		} else {
			return query(retSql, params, start, maxCount,isCount);
		}
	}

	public List<Map<String, Object>> query(String sql, Object[] params) throws SqlExecutorException {

		SqlRowSet results = getQueryResults(params, sql);
		List<Map<String, Object>> records = assembleQueryResult(results);

		return records;
	}

	public int queryForInt(String sql, Object[] params) throws SqlExecutorException {
		long time = System.currentTimeMillis();
		trimParams(params);
		StringBuffer newSql = new StringBuffer();
		params = filterSql(params, newSql, sql);
		sql = newSql.toString();
		try {
			int retInt = getJdbcTemplate().queryForInt(sql, params);
			long executeTime = System.currentTimeMillis() - time;
			printSql(sql, params, executeTime);
			return retInt;
		} catch (DataAccessException exception) {
			throw new SqlExecutorException("", exception);
		}
	}

	public long queryForLong(String sql, Object[] params) throws SqlExecutorException {
		long time = System.currentTimeMillis();
		trimParams(params);
		StringBuffer newSql = new StringBuffer();
		params = filterSql(params, newSql, sql);
		sql = newSql.toString();
		try {
			long retLong = getJdbcTemplate().queryForLong(sql, params);
			long executeTime = System.currentTimeMillis() - time;
			printSql(sql, params, executeTime);
			return retLong;
		} catch (DataAccessException exception) {
			throw new SqlExecutorException("", exception);
		}
	}

	public Map<String, Object> queryForMap(String sql, Object[] params) throws SqlExecutorException {

		long total = getTotalCount(sql, params, null, 1000, 100);
		if (total > 1) {
			throw new SqlExecutorException("more than one record founded for queryForMap[sql:" + sql + " params:"
					+ params.toString() + "]");
		} else if (total < 1) {
			throw new SqlExecutorException("can not found any record for queryForMap[sql:" + sql + " params:"
					+ params.toString() + "]");
		}

		SqlRowSet results = getQueryResults(params, sql);

		Map<String, Object> map = null;
		while (results.next()) {
			map = assembleMap(results);
		}

		return map;
	}

	public String queryForString(String sql, Object[] params) throws SqlExecutorException {
		long time = System.currentTimeMillis();
		trimParams(params);
		StringBuffer newSql = new StringBuffer();
		params = filterSql(params, newSql, sql);
		sql = newSql.toString();
		try {
			String retString = (String) getJdbcTemplate().queryForObject(sql, params, String.class);
			long executeTime = System.currentTimeMillis() - time;
			printSql(sql, params, executeTime);
			return retString;
		} catch (DataAccessException exception) {
			throw new SqlExecutorException("", exception);
		}
	}

	public Object queryForObject(String sql, Object[] params, Class glass) throws SqlExecutorException {
		long time = System.currentTimeMillis();
		trimParams(params);

		try {
			Object retObject = getJdbcTemplate().queryForObject(sql, params, glass);
			long executeTime = System.currentTimeMillis() - time;
			printSql(sql, params, executeTime);
			return retObject;
		} catch (DataAccessException exception) {
			throw new SqlExecutorException("", exception);
		}
	}

	public List<Map<String, Object>> queryList(String sql, Object[] params, int start, int maxCount)
			throws SqlExecutorException {
		Object[] newParams = null;
		if (params != null) {
			newParams = new Object[params.length * 2];
			for (int i = 0; i < params.length * 2; i++) {
				newParams[i] = params[i % params.length];
			}
		}

		String localSql = resultPagination.addPaginationSql(sql, start, maxCount);
		SqlRowSet results;
		if (start > 1) {
			results = getQueryResults(newParams, localSql);
		} else {
			results = getQueryResults(params, localSql);
		}
		List<Map<String, Object>> records = assembleQueryResult(results);

		return records;
	}

	public void setResultPagination(ResultPagination resultPagination) {
		this.resultPagination = resultPagination;
	}

	public QueryBuilder getQueryBuilder() {

		return queryBuilder;
	}

	private Object[] filterSql(Object[] params, StringBuffer newSql, String sql) {
		// 为了支持in 里面的子查询
		if (params != null) {
			Object[] tempParams = new Object[params.length];
			int k = -1;
			for (int i = 0, j = 0; i < sql.length(); i++) {
				char x = sql.charAt(i);
				if (x == '?') {
					if (params[j] instanceof String && params[j].toString().toUpperCase().indexOf("SELECT") != -1
							&& params[j].toString().toUpperCase().indexOf("FROM") != -1) {
						newSql.append(params[j]);
					} else {
						newSql.append(x);
						k++;
						tempParams[k] = params[j];
					}
					j++;
				} else {
					newSql.append(x);
				}
			}
			if (k >= 0) {
				params = new Object[k + 1];
				for (int i = 0; i < k + 1; i++) {
					params[i] = tempParams[i];
				}
			} else {
				params = null;
			}
		} else {
			newSql.append(sql);
		}
		return params;
		// ---------------------end
	}

	private SqlRowSet getQueryResults(Object[] params, String sql) throws SqlExecutorException {
		long time = System.currentTimeMillis();
		trimParams(params);
		StringBuffer newSql = new StringBuffer();
		params = filterSql(params, newSql, sql);
		sql = newSql.toString();
		SqlRowSet results;
		try {
			if (params == null) {
				// results = getJdbcTemplate().queryForRowSet(sql);
				results = (SqlRowSet) getJdbcTemplate().query(sql, new SqlRowSetOracleResultSetExtractor());
			} else {

				// results = getJdbcTemplate().queryForRowSet(sql, params);
				Object[] newParams = new Object[params.length];
				for (int i = 0; i < params.length; i++) {
					// timeStamp 不能触发Oracle的索引
					if (params[i] instanceof java.util.Date) {
						newParams[i] = new DateSqlTypeValue(params[i]);
					} else {
						newParams[i] = params[i];
					}
				}
				results = (SqlRowSet) getJdbcTemplate().query(sql, newParams, new SqlRowSetOracleResultSetExtractor());
			}
			long executeTime = System.currentTimeMillis() - time;
			printSql(sql, params, executeTime);
		} catch (DataAccessException exception) {
			long executeTime = System.currentTimeMillis() - time;
			printSql(sql, params, executeTime);
			throw new SqlExecutorException("", exception);
		}

		return results;
	}

	private long getTotalCount(String sql, Object[] params, List<Map<String, Object>> records, int start, int maxCount)
			throws SqlExecutorException {
		long total = 0;
		if (records != null && records.size() < maxCount && start < 2) {
			total = records.size();
		} else {
			StringBuffer newSql = new StringBuffer();
			params = filterSql(params, newSql, sql);
			sql = newSql.toString();

			String countSql = resultPagination.getCountSql(sql);
			long time = System.currentTimeMillis();
			trimParams(params);
			
			
			Object[] newParams = null;
			if(params != null){
				newParams = new Object[params.length];
				for (int i = 0; i < params.length; i++) {
					// timeStamp 不能触发Oracle的索引
					if (params[i] instanceof java.util.Date) {
						newParams[i] = new DateSqlTypeValue(params[i]);
					} else {
						newParams[i] = params[i];
					}
				}
			}

			try {
				total = getJdbcTemplate().queryForLong(countSql, newParams);
				long executeTime = System.currentTimeMillis() - time;
				printSql(countSql, params, executeTime);
			} catch (DataAccessException exception) {
				throw new SqlExecutorException("", exception);
			}
		}
		return total;
	}

	private List<Map<String, Object>> assembleQueryResult(SqlRowSet results) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		while (results.next()) {
			Map<String, Object> map = assembleMap(results);
			list.add(map);
		}

		return list;
	}

	private Map<String, Object> assembleMap(SqlRowSet results) {
		Map<String, Object> map = new HashMap<String, Object>();
		SqlRowSetMetaData meta = results.getMetaData();
		String[] columnNames = meta.getColumnNames();
		int i = 0;
		for (String columnName : columnNames) {
			Object object = results.getObject(columnName);
			if (meta.getColumnType(i + 1) == 2) {
				if (object != null) {
					BigDecimal number = (BigDecimal) object;
					map.put(columnName, number);
					// if(meta.getPrecision(i + 1) < 3 && meta.getScale(i + 1)
					// == 0) {
					// map.put(columnName, number.byteValue());
					// }
					// else if(meta.getColumnType(i + 1) == 2 &&
					// meta.getPrecision(i + 1) < 9 && meta.getScale(i + 1) ==
					// 0) {
					// map.put(columnName, number.intValue());
					// }
					// else if(meta.getColumnType(i + 1) == 2 &&
					// meta.getPrecision(i + 1) > 8 && meta.getScale(i + 1) ==
					// 0) {
					// map.put(columnName, number.longValue());
					// }
					// else if(meta.getColumnType(i + 1) == 2 && meta.getScale(i
					// + 1) > 0 && meta.getScale(i + 1) < 5) {
					// map.put(columnName, number.floatValue());
					// }
					// else if(meta.getColumnType(i + 1) == 2 && meta.getScale(i
					// + 1) > 5) {
					// map.put(columnName, number.doubleValue());
					// }
				} else {
					map.put(columnName, object);
				}
			} else {
				map.put(columnName, object);
			}
			++i;
		}

		return map;
	}

	private SqlQueryResult query(String sql, String orderByColumn, Object[] params, int start, int maxCount,
			boolean isCount) throws SqlExecutorException {
		Object[] newParams = null;
		if (params != null) {
			newParams = new Object[params.length * 2];
			for (int i = 0; i < params.length * 2; i++) {
				newParams[i] = params[i % params.length];
			}
		}

		String localSql = resultPagination.addPaginationSql(sql, orderByColumn, start, maxCount);

		SqlRowSet results;
		if (start > 1) {
			results = getQueryResults(newParams, localSql);
		} else {
			results = getQueryResults(params, localSql);
		}

		List<Map<String, Object>> records = assembleQueryResult(results);
		long total = 0;
		if (isCount) {
			total = getTotalCount(sql, params, records, start, maxCount);
		}
		return new SqlQueryResult(total, records);
	}

	public void setPrintSql(boolean printSql) {
		this.printSql = printSql;
	}

	private static String parseSortStr(String columnName) {
		String result = "";
		int index = columnName.indexOf(".");
		if (index < 0) {
			return columnName;
		}
		result = columnName.substring(index + 1);

		return columnName.replace(".", "_");
	}

	private boolean trimParams(Object[] params) {
		boolean isChanged = false;
		int flag = 0;
		if (params != null) {
			for (Object object : params) {
				if (object == null) {
					params[flag] = "";
					isChanged = true;
				}
				flag++;
			}
		}

		return isChanged;
	}

	private String printSql(String sql, Object[] params, long executeTime) {
		String executeSql = getExecutedSql(sql, params);
		
		if (printSql) {
			logger.debug(" Time-consuming:" + executeTime + "ms | " + executeSql);
			// logger.debug(printSql);
			// System.out.println(printSql);
		}

		return executeSql;
	}

	public String getExecutedSql(String sql, Object[] params) {
		StringBuffer newSql = new StringBuffer();
		params = filterSql(params, newSql, sql);
		sql = newSql.toString();

		StringBuffer logSql = new StringBuffer();
		for (int i = 0, j = 0; i < sql.length(); i++) {
			char x = sql.charAt(i);
			if (x == '?') {
				if (params[j] instanceof String) {
					logSql.append("'");
					logSql.append(params[j]);
					logSql.append("'");
				} else {
					logSql.append(params[j]);
				}
				j++;
			} else {
				logSql.append(x);
			}
		}
		return logSql.toString();
	}

	private void printStoreProc(String storedProcName, Object[] paramList, int[] outParamTypeList, long executeTime) {
		if (printSql) {
			StringBuffer logSql = new StringBuffer();
			if (executeTime > 0)
				logSql.append(" Time-consuming:" + executeTime + "ms | ");
			else
				logSql.append(" call storedProcedur started:");

			logSql.append(storedProcName + "(");
			if (paramList != null && paramList.length > 0) {
				for (int i = 0; i < paramList.length; i++) {
					if (i > 0) {
						logSql.append(",");
					}
					logSql.append(paramList[i]);

				}
			}
			if (outParamTypeList != null && outParamTypeList.length > 0) {
				for (int i = 0; i < outParamTypeList.length; i++) {
					if (paramList != null && paramList.length > 0) {
						logSql.append(",");
					}
					logSql.append("?");

				}
			}

			logSql.append(")");
			
			logger.debug(logSql);
		}
	}

	private ResultPagination resultPagination;

	private boolean printSql;

	private QueryBuilder queryBuilder = new QueryBuilder();

	public static String execute(String driverClass, String url, String username, String password, String sql) {
		Connection con = null;
		Statement stmt = null;
		String message = null;
		try {
			Class.forName(driverClass);
			con = DriverManager.getConnection(url, username, password);
			// 创建状态
			stmt = con.createStatement();
			// 执行SQL语句，返回结果集
			ResultSet rs = stmt.executeQuery(sql);
			// 对结果集进行处理
			message = I18N.getString ("successful_execution.");
		} catch (Exception e) {
			message = I18N.getString ("ExecitionFailString") + e.getMessage() + ")";
			e.printStackTrace();
			return message;
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (Throwable e) {
				message = I18N.getString ("ExecitionFailString") + e.getMessage() + ")";
				e.printStackTrace();
				return message;
			}
		}

		return message;
	}

	class DateSqlTypeValue implements SqlTypeValue {

		private Object date = null;

		public DateSqlTypeValue(Object date) {
			this.date = date;
		}

		public void setTypeValue(PreparedStatement ps, int paramIndex, int sqlType, String typeName)
				throws SQLException {
			if (date instanceof java.util.Date) {
				ps.setDate(paramIndex, new java.sql.Date(((java.util.Date) date).getTime()));
			} else {
				ps.setObject(paramIndex, date);
			}
		}

	}

	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@134.96.101.10:1521:nmdb";
		String username = "pst0814";
		String password = "pst0814";
		// String sql="select s_x_Intf_Log.nextval from dual";
		String sql = "select ID  from xb_port where rownum<2";
		// String sql="select S_X_ENTRYLOG.nextval from dual";
		// execute(driver,url,username,password,sql);
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		SqlExecutor sqlExecutor = new SpringJdbcSqlExecutor();
		sqlExecutor.setDataSource(dataSource);

		try {
			// sqlExecutor.queryForLong(sql, null);
			sqlExecutor.query(sql, null);
		} catch (SqlExecutorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
