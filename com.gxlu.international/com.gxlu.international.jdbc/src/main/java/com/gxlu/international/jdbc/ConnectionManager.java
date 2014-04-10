package com.gxlu.international.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.gxlu.international.jdbc.transaction.ConnectionFactory;

public abstract class ConnectionManager {
	
	private ConnectionFactory connectionFactory ;

	private static volatile ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	private static volatile ThreadLocal<Session> localSession = new ThreadLocal<Session>();
	private Connection connection;

	
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	private Connection getCurrentConnection(){
		connection = threadLocal.get();
		try {
			if(connection==null || connection.isClosed()){
				connection =connectionFactory.createConnection();
				threadLocal.set(connection);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	public Session openSession(){
		Session session = new DefaultSession(getCurrentConnection());
		localSession.set(session);
		return session;
	}
	
	public Session getCurrentSession(){
		Session session =localSession.get();
		if(session==null){
			session = openSession();
		}
		return session;
	}
}
