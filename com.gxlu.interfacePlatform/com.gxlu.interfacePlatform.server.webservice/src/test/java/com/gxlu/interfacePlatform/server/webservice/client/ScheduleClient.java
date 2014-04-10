package com.gxlu.interfacePlatform.server.webservice.client;

import java.net.URL;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.gxlu.interfacePlatform.webservice.plugins.ScheduleInterface4WS;

public class ScheduleClient {
	private static final QName SERVICE_NAME = new QName(
			"http://plugins.webservice.interfacePlatform.gxlu.com/",
			"scheduleService");
	private static final QName PORT_NAME = new QName(
			"http://plugins.webservice.interfacePlatform.gxlu.com/",
			"HelloWorldPort"); 

	/**
	 * @param args
	 */
	public static void main(String[] args)  throws Exception{
	//‘› ±∆¡±Œ  ±‡“Î±®¥Ì¿≤°£°£°£  
		// TODO Auto-generated method stub
		Service service = Service.create(new URL("http://172.30.134.135:7001/scheduleService?wsdl"),SERVICE_NAME);
		Iterator it= service.getPorts();
	     while(it.hasNext()){
	    	 QName name =(QName) it.next();
	    	 System.out.println(name.getLocalPart());
	     } 
		ScheduleInterface4WS scheduleInterface4WS = service.getPort(ScheduleInterface4WS.class);
//		boolean result =scheduleInterface4WS.update(null);
//		System.out.println(result);
		boolean result =scheduleInterface4WS.run("DRM_IP_SNMP");
		System.out.println(result); 
//		
	}

}
