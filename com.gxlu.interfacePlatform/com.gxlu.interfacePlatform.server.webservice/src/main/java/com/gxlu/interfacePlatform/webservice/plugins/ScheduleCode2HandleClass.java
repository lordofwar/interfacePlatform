package com.gxlu.interfacePlatform.webservice.plugins;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * convert ScheduleCode to ScheduleHandlerClassName.
 * 
 * @author Jame
 * 
 */
public class ScheduleCode2HandleClass {
	private Log log = LogFactory.getLog(getClass());

	private String fileName = "scheduleCode2HandleClass.properties";
	private Properties properties = new Properties();

	public ScheduleCode2HandleClass() {
		// TODO Auto-generated constructor stub
		if(properties.isEmpty())
			load();
	}

	private void load() {
		ClassLoader cl = getClass().getClassLoader();
		InputStream input = cl.getResourceAsStream(fileName);
		try {
			properties.load(input);
		} catch (IOException e) {
			log.error("Error occure while loading scheduleCode2HandleClass.properties.",e);
		} finally {
			if (input != null) {
				close(input);
			}
		}
	}

	private void close(InputStream input) {
		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}
	}

	public String getHandlerClassName(String code) {
		return this.properties.getProperty(code, null);
	}
}
