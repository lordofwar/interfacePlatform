package com.gxlu.international.jdbc.transaction;

import java.sql.Connection;

import com.gxlu.international.jdbc.ConnectionManager;
import com.gxlu.international.jdbc.DBAdapter;

public class DefaultConnectionManager extends ConnectionManager {
	public DefaultConnectionManager() {
		
		this.setConnectionFactory(new ConnectionFactory() {
			
			@Override
			public Connection createConnection() {
				// TODO Auto-generated method stub
				return  DBAdapter.getInstance().getConnection();
			}
		});
	}
}
