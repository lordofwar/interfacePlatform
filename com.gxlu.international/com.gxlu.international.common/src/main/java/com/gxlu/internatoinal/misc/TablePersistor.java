package com.gxlu.internatoinal.misc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TablePersistor {
	
	private Connection conn;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public TablePersistor(Connection connection) {
		conn = connection;
	}
	
	public void clear(String tableName){
		
	}
	
	public void insert(Table table){
		PreparedStatement prst=null;
		try {
			conn.setAutoCommit(false);
			logger.info("---开始处理数据库持久化---");
			String sql = this.getInsertSql(table);
			logger.info("---生成 SQL:	"+sql);
			prst =conn.prepareStatement(sql);
			List<String[]> datas=table.getData();
			for(String[] row:datas){
				for(int i=1;i<row.length+1;i++){
					prst.setObject(i,filterValue(row[i-1]));
				}
				prst.addBatch();
			}
			prst.executeBatch();
			conn.commit();
			prst.clearBatch();
			prst.close();
			logger.info("处理数据库持久化END");
		}catch(Exception e){
			logger.error("插入数据失败", e);
		}finally{
			if(prst!=null){
				
				try {
					prst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public void close(){
		if(this.conn!=null){
			try {
				this.conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getInsertSql(Table table){
		String[] header=table.getHeader();
		String[] values= new String[header.length];
		Arrays.fill(values, "?");
		return String.format("insert into %s(%s,CREATEDATE) values(%s,sysdate)",table.getTableName(),trim(Arrays.toString(header)),trim(Arrays.toString(values)));
	}
	
	private Object filterValue(String value){
		if(StringUtils.isNotBlank(value) && value.equals("false")){
			return 0;
		}else	if(StringUtils.isNotBlank(value) && value.equals("true")){
			return 1;
		}
		return value;
	}
	
	private String trim(String array){
		return array.subSequence(1, array.length()-1).toString();
	}
	
	
}
