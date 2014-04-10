package com.gxlu.internatoinal.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Simple Table Parser for csv format file.
 * @author pudding
 *
 */
public class SimpleTableCSVParser implements Parser<Table> {
	private Table table ;
	private int intervalRows=10000;
	public SimpleTableCSVParser(String tableName){
		table= new Table(tableName);	
	}
	
	public SimpleTableCSVParser(Table table){
		this.table= table;	
	}
	
	@Override
	public Table parse(InputStream remoteFtpFile) {
		// TODO Auto-generated method stub
		return parse(remoteFtpFile,null);
	}
	
	
	@Override
	public Table parse(InputStream remoteFtpFile, BatchHandler handler) {
		// TODO Auto-generated method stub
		
		readByOpenCSV(remoteFtpFile,handler);
		
		
		
		return table;
	}
	
	private void readByCommonCSV(InputStream remoteFtpFile, BatchHandler handler){
		
	}
	
	
	/**
	 * Reading csv file by openCsv.jar
	 * @param remoteFtpFile
	 * @param handler
	 */
	private void readByOpenCSV(InputStream remoteFtpFile, BatchHandler handler){
		BufferedReader br = new BufferedReader(new InputStreamReader(remoteFtpFile));
		CSVReader reader = new CSVReader(br);
		try {
			String line[];
			//First line is normally header.
			if((line=reader.readNext())!=null){
					table.setHeader(line);	
			}
			
			int count=0;
			while ((line = reader.readNext()) != null) {
					table.getData().add(line);
				count++;
				
				
				if(count>=intervalRows && handler!=null){
					handler.handle(table);
					table.getData().clear();
					count=0;
				}
			}
			if(handler!=null)
				handler.handle(table);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
				try {
					if(br!=null)
						br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public int getIntervalRows() {
		return intervalRows;
	}

	public void setIntervalRows(int intervalRows) {
		this.intervalRows = intervalRows;
	}
}
