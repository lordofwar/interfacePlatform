package com.gxlu.international.gd.lte;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.international.jdbc.ConnectionManager;
import com.gxlu.international.jdbc.Session;
import com.gxlu.international.jdbc.transaction.DefaultConnectionManager;
import com.gxlu.international.jdbc.transaction.Transaction;
import com.gxlu.internatoinal.misc.Table;

public class LTEHuaWeiDao {
	private Log log = LogFactory.getLog(getClass());

	public void saveData(Table table) {
		Transaction transaction = null;
		LTEHuaWeiTablePersistor persistor =null;
		try {
			ConnectionManager manager = new DefaultConnectionManager();
			Session session = manager.openSession();
			transaction = session.getTransaction();
			transaction.beginTransaction();
			Connection connection = session.getConnection();
			persistor = new LTEHuaWeiTablePersistor(connection);
			persistor.insert(table);
		} catch (Exception e) {
			transaction.rollback();
			log.error("持久化数据失败：TableName：" + table.getTableName(), e);
		} finally {
			transaction.commit();
			transaction.close();
		}
	}

}
