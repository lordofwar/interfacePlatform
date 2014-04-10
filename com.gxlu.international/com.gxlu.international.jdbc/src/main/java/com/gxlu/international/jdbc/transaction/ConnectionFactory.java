package com.gxlu.international.jdbc.transaction;

import java.sql.Connection;


public interface ConnectionFactory {

	public Connection createConnection();
}
