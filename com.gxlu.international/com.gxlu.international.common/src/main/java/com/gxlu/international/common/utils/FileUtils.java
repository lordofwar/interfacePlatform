package com.gxlu.international.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {
	
	public static String getFileSuffix(String fileName) {
		String suffix = "";
		int index = fileName.lastIndexOf(".");
		suffix = fileName.substring(index + 1);
		return suffix;
	}
	
	/**
	 * Get table suffix of file width name pattern tablename@.csv
	 * @param fileName
	 * @return
	 */
	public static String getTableName(String fileName){
		Pattern pattern = Pattern.compile("[_]([a-zA-Z0-9]+)[_]");
		Matcher matcher =pattern.matcher(fileName);
		String name=null;
		if(matcher.find()){
			name= matcher.group(1);
		}
		
		if(name != null && "SectionUnderlayerlink".equalsIgnoreCase(name)){
			name = "SECTIONDETAIL";
		}
		
		return name;
	}
}
