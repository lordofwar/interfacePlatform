package com.gxlu.international.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.international.jdbc.transaction.DefaultConnectionManager;

public class DBUtils {
	
	private static Log logger = LogFactory.getLog(DBUtils.class);
	private static DBUtils instance ;
	
	public  static DBUtils $(){
		
		if(DBUtils.instance==null){
			instance=new DBUtils();
		}
		return instance;
	}
	
	private DBUtils(){}
	
	public void executeSP(Connection connection, String precedure) {
		CallableStatement call = null;
		try {logger.info(String.format("Beginning to proccess precedure [%s] ...",precedure));
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
		Connection connection =manager.openSession().getConnection();
		CallableStatement call = null;
		try {logger.info(String.format("Beginning to proccess precedure [%s] ...",precedure));
			call = connection.prepareCall(String.format("{ call %s() }", precedure));
			call.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.equals(e);
		} finally {
			closeQuietly(call);
		}
	}
	
	public String executeSP(String precedure,Object param) {
		ConnectionManager manager = new DefaultConnectionManager();
		Connection connection =manager.openSession().getConnection();
		CallableStatement call = null;
		try {logger.info(String.format("Beginning to proccess precedure [%s] ...",precedure));
			call = connection.prepareCall(String.format("{?= call %s(?) }", precedure));
			call.registerOutParameter(1, Types.VARCHAR);
			call.setObject(2, param);
			call.execute();
			return call.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(String.format("precedure : %s , parameters : %s",precedure,param));
			logger.equals(e);
		} finally {
			closeQuietly(call);
		}
		return null;
	}
	
	public void closeQuietly(Connection connection){
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
}
