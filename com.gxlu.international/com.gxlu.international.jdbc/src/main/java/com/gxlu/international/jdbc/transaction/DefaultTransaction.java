package com.gxlu.international.jdbc.transaction;

import java.sql.Connection;

public class DefaultTransaction extends AbstractTransaction{

	public DefaultTransaction(Connection connection){
		super(connection);
	}
}
