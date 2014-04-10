package com.gxlu.international.common.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.international.jdbc.ConnectionManager;
import com.gxlu.international.jdbc.transaction.DefaultConnectionManager;

public class DBUtils {

	private static Log logger = LogFactory.getLog(DBUtils.class);
	private static DBUtils instance;

	public static DBUtils $() {

		if (DBUtils.instance == null) {
			instance = new DBUtils();
		}
		return instance;
	}

	private DBUtils() {
	}

	public void executeSP(Connection connection, String precedure) {
		CallableStatement call = null;
		try {
			logger.info(String.format("Beginning to proccess precedure [%s] ...", precedure));
			call = connection.prepareCall(String.format("{ call %s() }", precedure));
			call.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.equals(e);
		} finally {
			closeQuietly(call);
		}
	}

	public void executeSP(String precedure) {
		ConnectionManager manager = new DefaultConnectionManager();
		Connection connection = manager.openSession().getConnection();
		CallableStatement call = null;
		try {
			logger.info(String.format("Beginning to proccess precedure [%s] ...", precedure));
			call = connection.prepareCall(String.format("{ call %s() }", precedure));
			call.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.equals(e);
		} finally {
			closeQuietly(call);
		}
	}

	public void closeQuietly(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.equals(e);
		}
	}

	public void closeQuietly(CallableStatement call) {
		try {
			if (call != null) {
				call.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.equals(e);
		}

	}

	public void closeQuietly(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.equals(e);
		}

	}

	public void closeQuietly(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.equals(e);
		}

	}

	public Object queryObject(String sql) {
		Object objcet = null;
		PreparedStatement prest = null;
		ResultSet rs = null;
		ConnectionManager manager = new DefaultConnectionManager();
		Connection connection = manager.openSession().getConnection();
		try {
			prest = connection.prepareStatement(sql);
			rs = prest.executeQuery();
			if (rs.next()) {
				objcet = rs.getObject(1);
			}
		} catch (SQLException e) {
			logger.error("queryObject():", e);
		} finally {
			closeQuietly(connection);
			closeQuietly(prest);
			closeQuietly(rs);
		}
		return objcet;
	}

	public List<Map<String,Object>> queryList(String sql) {
		List<Map<String,Object>> mapList = null;
		PreparedStatement prest = null;
		ResultSet rs = null;
		ConnectionManager manager = new DefaultConnectionManager();
		Connection connection = manager.openSession().getConnection();
		try {
			prest = connection.prepareStatement(sql);
			mapList = resultSetToList(prest.executeQuery());
		} catch (SQLException e) {
			logger.error("queryList() Error:", e);
		} finally {
			closeQuietly(connection);
			closeQuietly(prest);
			closeQuietly(rs);
		}
		return mapList;
	}

	public void updateObject(String sql) {
		Statement stat = null;
		ConnectionManager manager = new DefaultConnectionManager();
		Connection connection = manager.openSession().getConnection();
		try {
			stat = connection.createStatement();
			stat.executeUpdate(sql);
		} catch (SQLException e) {
			logger.error("updateObject():", e);
		} finally {
			closeQuietly(connection);
			closeQuietly(stat);
		}
	}

	public  List<Map<String,Object>> resultSetToList(ResultSet rs) throws java.sql.SQLException {
		if (rs == null)
			return Collections.emptyList();
		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> rowData = new HashMap<String,Object>();
		while (rs.next()) {
			rowData = new HashMap<String,Object>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(rowData);
		}
		return list;
	}
}
