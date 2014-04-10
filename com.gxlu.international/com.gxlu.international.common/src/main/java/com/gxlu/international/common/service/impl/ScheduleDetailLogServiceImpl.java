package com.gxlu.international.common.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import com.gxlu.interfacePlatform.schedule.DetailLogStorage;
import com.gxlu.international.common.service.ScheduleDetailLogService;


public class ScheduleDetailLogServiceImpl implements ScheduleDetailLogService {

	private static final String INFO = "[INFO]-";

	private static final String ERROR = "[ERROR]-";

	private static final String NEW_LINE = System.getProperty("line.separator");

	private String interfaceLibCode;

	private DetailLogStorage storage;

	public ScheduleDetailLogServiceImpl(String interfaceLibCode) {
		this.interfaceLibCode = interfaceLibCode;
		this.storage = DetailLogStorage.getInstance();
	}

	@Override
	public void info(String str) {
		str = getInfoPre() + str + NEW_LINE;
		storage.append(interfaceLibCode, str);
	}

	@Override
	public void error(String str, Exception e) {
		StringWriter sw = new StringWriter();  
		e.printStackTrace(new PrintWriter(sw));  
		str = getErrorPre() + str + sw.toString() + NEW_LINE;
		storage.append(interfaceLibCode, str);
	}

	@Override
	public void error(String str) {
		str = getErrorPre() + str + NEW_LINE;
		storage.append(interfaceLibCode, str);
	}

	private String getInfoPre() {
		return INFO + "[" + new Date().toLocaleString() + "]";
	}

	private String getErrorPre() {
		return ERROR + "[" + new Date().toLocaleString() + "]";
	}

}
