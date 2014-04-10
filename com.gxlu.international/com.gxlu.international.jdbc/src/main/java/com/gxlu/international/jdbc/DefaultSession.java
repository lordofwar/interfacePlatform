package com.gxlu.international.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.international.jdbc.transaction.DefaultTransaction;
import com.gxlu.international.jdbc.transaction.Transaction;

public class DefaultSession implements Session{
	
	private Log log = LogFactory.getLog(getClass());
	
	private Transaction transaction;
	private Connection connection;
	
	public DefaultSession(final Connection connection){
		this.connection = connection;
	}
	
	public Transaction getTransaction(){
		if(transaction==null){
			transaction = new DefaultTransaction(connection);
		}
		return transaction;
	}
	
	public Connection getConnection(){
		return connection;
	}

	@Override
	public List<String> queryForList(String sql, String... param) {
		// TODO Auto-generated method stub
		PreparedStatement preparedStatement = null;
		List<String> result = new LinkedList<String>();
		ResultSet rs =null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			for(int i=1;i<=param.length;i++){
				preparedStatement.setObject(i, param[i-1]);
			}
			rs = preparedStatement.executeQuery();
			while(rs.next()){
				result.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.$().closeQuietly(rs);
			DBUtils.$().closeQuietly(preparedStatement);
		}
		
		return result;
	}
	
	public Map<String,Object> query(String sql,Object...params)throws SQLException{
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Connection connection = getConnection();
		PreparedStatement pstmt = connection
				.prepareStatement(sql);
		ResultSet rs=null;
		try{
			for(int i=0;params!=null && i<params.length;i++){
				pstmt.setObject(i+1, params[i]);
			}
			rs = pstmt.executeQuery();
			if(rs.next()){
				ResultSetMetaData rsmd =rs.getMetaData();
				for(int columnIndex=1;columnIndex<=rsmd.getColumnCount();columnIndex++){
					resultMap.put(rsmd.getColumnName(columnIndex),rs.getObject(columnIndex));
					
				}
			}
		}finally{
			if(pstmt!=null)
				pstmt.close();
			
			if(rs!=null)
				rs.close();
		}
		
		return resultMap;
	}
	
	public List<Map<String,Object>> queryList(String sql,Object...params)throws SQLException{
		List<Map<String,Object>> list = new LinkedList<Map<String,Object>>();
		Connection connection = getConnection();
		PreparedStatement pstmt = connection
				.prepareStatement(sql);
		ResultSet rs=null;
		try{
			for(int i=0;params!=null && i<params.length;i++){
				pstmt.setObject(i+1, params[i]);
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				Map<String,Object> resultMap = new HashMap<String, Object>();
				ResultSetMetaData rsmd =rs.getMetaData();
				for(int columnIndex=1;columnIndex<=rsmd.getColumnCount();columnIndex++){
					resultMap.put(rsmd.getColumnName(columnIndex),rs.getObject(columnIndex));
				}
				
				list.add(resultMap);
			}
		}finally{
			if(pstmt!=null)
				pstmt.close();
			
			if(rs!=null)
				rs.close();
		}
		
		return list;
	}
	
	public void insert(String sql, Object... params) throws SQLException {
		Connection connection = getConnection();
		PreparedStatement pstmt = connection
				.prepareStatement(sql);
		try{
			for(int i=0;i<params.length;i++){
				pstmt.setObject(i+1, params[i]);
			}
			pstmt.execute();
		}finally{
			if(pstmt!=null)
				pstmt.close();
		}
	}
	
	public void update(String sql, Object... params) {
		this.execute(sql, params);
	}
	
	public void execute(String sql, Object... params) {
		Connection connection = getConnection();
		PreparedStatement pstmt=null;
		try{
			 pstmt = connection.prepareStatement(sql);
			for(int i=0;i<params.length;i++){
				pstmt.setObject(i+1, params[i]);
			}
			
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("",e);
		}finally{
			DBUtils.$().closeQuietly(pstmt);
		}
	}
	
	public void call(String procedure,Object[] inParams){
		Connection connection = getConnection();
		CallableStatement call = null;
		try {
			log.info(String.format("Beginning to proccess precedure [%s] ...",procedure));
			StringBuilder paramTip =new StringBuilder(); 
			for(int i=0;i<inParams.length;i++){
				if(paramTip.length()>0){
					paramTip.append(",");
				}
				paramTip.append("?");
			}
			call = connection.prepareCall(String.format("{ call %s(%s) }", procedure,paramTip.toString()));
			for(int i=0;i<inParams.length;i++){
				call.setObject(i+1, inParams[i]);
			}
			
			call.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.equals(e);
		} finally {
			DBUtils.$().closeQuietly(call);
		}
	}
	
	public Object[] call(String procedure,Object[]inParams,Class<?>[] outParams) throws SQLException{
		
		List<Object> result = new ArrayList<Object>(outParams.length);
		Connection connection = getConnection();
		CallableStatement call = null;
		try {
			log.info(String.format("Beginning to proccess precedure [%s] ...",procedure));
			StringBuilder paramTip =new StringBuilder(); 
			for(int i=0;i<inParams.length+outParams.length;i++){
				if(paramTip.length()>0){
					paramTip.append(",");
				}
				paramTip.append("?");
			}
			String sql =String.format("{ call %s(%s) }", procedure,paramTip.toString());
			call = connection.prepareCall(sql);
			for(int i=0;i<inParams.length;i++){
				call.setObject(i+1, inParams[i]);
			}
			
			for(int i=0;i<outParams.length;i++){
				if(outParams[i] == String.class){
					call.registerOutParameter(inParams.length+i+1, Types.VARCHAR);
				}else if(outParams[i] == Integer.class){
					call.registerOutParameter(inParams.length+i+1, Types.INTEGER);
				}else if(outParams[i] == Integer.class){
					call.registerOutParameter(inParams.length+i+1, Types.ARRAY);
				}else{
					log.error("Unsupported format."+ outParams[i].getClass().getName());
				}
			}
			
			call.execute();
			
			for(int i=0;i<outParams.length;i++){
				result.add(call.getObject(inParams.length+i+1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("",e);
			throw e;
		} finally {
			DBUtils.$().closeQuietly(call);
		}
		
		return result.toArray();
	}
	
	public String getNextval(String sequenceName){
		List<String> ids = queryForList(String.format("select %s.nextval from dual",sequenceName),new String[]{});
		if(ids.size()>0){
			return ids.get(0);
		}else{
			return null;
		}
	}
	
	public void close(){
		try {
			if(connection!=null){
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
