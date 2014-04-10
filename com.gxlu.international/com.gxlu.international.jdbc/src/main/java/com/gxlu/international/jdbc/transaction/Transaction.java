package com.gxlu.international.jdbc.transaction;

import java.sql.Connection;

public interface Transaction {

	public void beginTransaction();
	
	public void commit();
	
	public void rollback();
	
	public Connection getCurrentConnection();
	
	public boolean isRunning();
	
	public void close();
}
