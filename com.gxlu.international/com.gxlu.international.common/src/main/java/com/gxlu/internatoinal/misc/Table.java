package com.gxlu.internatoinal.misc;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author pudding
 *
 */
public class Table implements HeaderCorrectable{
	private String [] header;
	private List<String []> data = new LinkedList<String[]>();
	private HeaderCorrect correct = new DefaultHeaderCorrect();
	private String tableName;
	private Map<String,String> correctMap ;
	private String sequence;
	
	
	public Table(){	}
	
	public Table(String tableName){
		this(tableName,null);
	}
	
	public Table(String tableName,Map<String,String> correctMap){
		this.tableName=tableName;
		this.sequence="S_"+tableName;
		this.correctMap=correctMap;
	}
	
	public String[] getHeader() {
		return header;
	}
	public void setHeader(String[] header) {
		if(correctMap!=null){
			this.header=correct.correct(correctMap, header);
		}else
			this.header = header;
	}
	public List<String[]> getData() {
		return data;
	}
	public void setData(List<String[]> data) {
		this.data = data;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public void setCorrectMap(Map<String, String> correctMap) {
		// TODO Auto-generated method stub
		this.correctMap=correctMap;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
}
