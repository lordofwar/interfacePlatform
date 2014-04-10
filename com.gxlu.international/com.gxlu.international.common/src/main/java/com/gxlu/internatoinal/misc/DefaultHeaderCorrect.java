package com.gxlu.internatoinal.misc;

import java.util.Map;



public class DefaultHeaderCorrect implements HeaderCorrect {

	@Override
	public String[] correct(Map<String, String> map, String[] header) {
		// TODO Auto-generated method stub
		
		String[] newHeader = new String[header.length];
		
		for(int i=0;i<header.length;i++){
			
			if(map.containsKey(header[i])){
				newHeader[i]= map.get(header[i]);
			}else{
				newHeader[i]= header[i];
			}
		}
		
		return newHeader;
	}

}
