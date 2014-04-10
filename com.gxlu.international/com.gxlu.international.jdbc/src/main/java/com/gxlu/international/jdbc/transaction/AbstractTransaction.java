package com.gxlu.international.jdbc.transaction;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractTransaction implements Transaction{

	
	private static volatile ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	
	private Connection connection;
	
	public AbstractTransaction(final Connection connection){
		this.connection =connection;
	}

	
	public Connection getCurrentConnection(){
		if(connection==null){
			connection = threadLocal.get();
		}
		return connection;
	}
	
	@Override
	public void beginTransaction() {
		// TODO Auto-generated method stub
		try {
			connection =getCurrentConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		try {
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub
		try {
			connection.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close(){
		try {
			if(connection!=null){
				connection.close();
				threadLocal.remove();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		Connection connection = getCurrentConnection();
		try {
			if(connection != null && !connection.isClosed()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
