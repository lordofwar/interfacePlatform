package com.gxlu.international.gd.lte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.gxlu.internatoinal.misc.Table;

public class LTEHuaWeiCSVParser {
	private Log log = LogFactory.getLog(getClass());

	public Map<String,Table> parse(InputStream remoteFtpFile) throws IOException {
		Map<String,Table> tablesMap = new HashMap<String,Table>();
		BufferedReader br = new BufferedReader(new InputStreamReader(remoteFtpFile));
		CSVReader reader = new CSVReader(br);
		try {
			String[] attrLine;
			// 开始的两行是NE信息
			boolean NELine = true;
			while ((attrLine = reader.readNext()) != null) {
				if (attrLine.length < 2){
					continue;
				}
				log.debug(Arrays.toString(attrLine));
				if (attrLine[1] != null && attrLine[1].equalsIgnoreCase("fdn")) {
					String[] valueLine;
					if ((valueLine = reader.readNext()) != null && valueLine.length > 1) {
						if (NELine) {
							Table neTable = new Table(TableNameEnum.NE_TABLE.tableName);
							neTable.setHeader(attrLine);
							neTable.getData().add(valueLine);
							tablesMap.put(neTable.getTableName(),neTable);
							NELine = false;
						} else {
							if (valueLine[1] != null && !valueLine[1].equals("")) {
								Table table = getTableByFDN(valueLine[1]);
								if (table != null) {
									if(tablesMap.containsKey(table.getTableName())){
										tablesMap.get(table.getTableName()).getData().add(valueLine);
									}else{
										table.setHeader(attrLine);
										table.getData().add(valueLine);
										tablesMap.put(table.getTableName(), table);
									}
								}
							}
						}
					}

				}
			}
		} finally {
			reader.close();
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return tablesMap;

	}
	

	public Table getTableByFDN(String fdn) {
		Table table = null;
		TableNameEnum[] nameSet = TableNameEnum.values();
		for (TableNameEnum tableEnum : nameSet) {
			if (fdn.contains(tableEnum.name()+"=")) {
				table = new Table(tableEnum.tableName, TableNameEnum.getAttrCorrectMap(tableEnum.tableName));
				log.debug("fdn:"+fdn+" tableName:"+table.getTableName());
			}
		}
		log.debug("fdn:"+fdn);
		return table;
	}
}
