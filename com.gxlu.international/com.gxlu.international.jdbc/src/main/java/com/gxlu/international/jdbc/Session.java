package com.gxlu.international.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gxlu.international.jdbc.transaction.Transaction;

public interface Session {
	
	public Transaction getTransaction();
	
	public Connection getConnection();
	
	public List<String> queryForList(String sql,String... param);
	
	public Map<String, Object> query(String sql, Object... params) throws SQLException;

	public List<Map<String, Object>> queryList(String sql, Object... params) throws SQLException;

	public void insert(String sql, Object... params) throws SQLException;

	public void update(String sql, Object... params);

	public void execute(String sql, Object... params);

	public void call(String procedure, Object[] inParams);

	public Object[] call(String procedure, Object[] inParams, Class<?>[] outParams) throws SQLException;

	public String getNextval(String sequenceName);
	
	public void close();
}
