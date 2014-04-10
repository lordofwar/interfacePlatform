package com.gxlu.international.gd.lte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.internatoinal.misc.Table;


public class LTEHuaWeiTablePersistor {
	
	private Connection conn;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public LTEHuaWeiTablePersistor(Connection connection) {
		conn = connection;
	}
	
	public void clear(String tableName){
		
	}
	
	public void insert(Table table){
		PreparedStatement prst=null;
		try {
			conn.setAutoCommit(false);
			String sql = this.getInsertSql(table);
			logger.debug("---生成 SQL:	"+sql);
			prst =conn.prepareStatement(sql);
			List<String[]> datas=table.getData();
			String[] attrHeader = table.getHeader();
			for(String[] row:datas){
				for(int i=1;i<attrHeader.length+1;i++){
					prst.setObject(i,row[i-1]);
				}
				prst.addBatch();
			}
			prst.executeBatch();
			conn.commit();
			prst.clearBatch();
			prst.close();
			logger.info("数据插入成功："+table.getTableName()+" 数量："+table.getData().size());
		}catch(Exception e){
			logger.error("插入数据失败", e);
		}finally{
			if(prst!=null){
				try {
					prst.close();
				} catch (SQLException e) {
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
				e.printStackTrace();
			}
		}
	}
	
	public String getInsertSql(Table table){
		String[] header=table.getHeader();
		//去掉数据文件中读取行末的空数据
		for(int i= (header.length-1); i>0; i--){
			String str = header[i];
			if(str != null && str.equals("")){
				header = Arrays.copyOfRange(header, 0, i);
				table.setHeader(header);
			}else{
				break;
			}
		}
		String[] values= new String[header.length];
		Arrays.fill(values, "?");
		return String.format("insert into %s(ID,%s) values(%s,%s)",table.getTableName(),trim(Arrays.toString(header)),table.getSequence()+".NEXTVAL",trim(Arrays.toString(values)));
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
