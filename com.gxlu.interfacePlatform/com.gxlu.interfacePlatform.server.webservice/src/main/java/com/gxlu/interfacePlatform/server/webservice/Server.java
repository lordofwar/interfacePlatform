package com.gxlu.interfacePlatform.server.webservice;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.interfacePlatform.schedule.IMSScheduleManager;
import com.gxlu.interfacePlatform.server.webservice.ServerConfig.ServerConfigEntry;
/**
 * Server is core server to hold the service of rmi or webservice and schedule.
 * There are two kinds of server in interface-platform. 
 * One is MAIN-Server which play the main role of schedule and it's the entrance of the schedule management.
 * The other one is Sub-Server, which is the extension of the MAIN-Server. Sub-Server can hold remote schedule implementation and also can has it's own
 * services like RMI or Webservice.
 * The mechanism  between two of them is MAIN Server will transfer the request to Sub-Server by RMI interface.
 * All of the server will have its own id, which will be used as the identify of Operation RMI Service and Remote Schedule Service.
 * 
 * @author Jame
 *
 */
public class Server extends AbstractServicePublishServer{

	private static Log log = LogFactory.getLog(Server.class);
	private String id;
	private String type;
	
	public Server(String id,String type,int rmiPort, String wsContextPath) throws IOException {
		// TODO Auto-generated constructor stub
		super(rmiPort, wsContextPath);
		this.id=id;
		this.type=type;
	}

	public void startIMSScheduleManager() throws IOException {
		IMSScheduleManager scheduleManager = IMSScheduleManager.getIMSScheduleManager();	
		scheduleManager.startCron();
	}
	
	public void publishOperationService(String rmiAddress){
		this.publishServicePlugin(rmiAddress, "com.gxlu.interfacePlatform.server.webservice.services.ServerOperationImpl");
	}
	
	public void publishRemoteScheduleService(String rmiAddress){
		this.publishServicePlugin(rmiAddress, "com.gxlu.interfacePlatform.schedule.rmi.RemoteScheduleImpl");	
	}
	
	@Override
	public void publish() throws IOException {
		// TODO Auto-generated method stub
		super.publish();
		
		if(ServerConfig.TYPE_SUB.equals(type)){
			this.publishRemoteScheduleService(String.format("%s",id));
			this.publishOperationService(String.format("%s_Operation",id));
		}
		
		if(ServerConfig.TYPE_MAIN.equals(type)){
			this.publishOperationService(String.format("Platform_Operation"));
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		
		ServerConfigEntry entry=ServerConfig.getServerConfig().loadConfig();
		String contextPath = entry.getWsContextPath();
		int rmiPort = entry.getRmiPort();
		String id=entry.getId();
		String type= entry.getType();
		String description=entry.getDescription();
		
		if (args != null && args.length > 0){
			contextPath = args[0];
		}
		
		log.info("********************************************");
		log.info(String.format("Start Server."));
		log.info(String.format("ID: %s", id));
		log.info(String.format("TYPE: %s", type));
		log.info(String.format("RMI Port: %d", rmiPort));
		log.info(String.format("WebService Address:%s", contextPath));
		log.info(String.format("Description: %s", description));
		if(entry.getSubServerList().size()>0){
			log.info(String.format("Sub Server:"));
			for(ServerConfigEntry subServerEntry :entry.getSubServerList()){
				log.info(String.format("\tID: %s", subServerEntry.getId()));
				log.info(String.format("\tRMI Port: %d", subServerEntry.getRmiPort()));
				log.info(String.format("\tWebService Address:%s", subServerEntry.getWsContextPath()));
			}
		}
		log.info("********************************************");
		
		Server server =new Server(id,type,rmiPort, contextPath);
		
		if(ServerConfig.TYPE_MAIN.equals(type)){
			log.info("Start IMS Schedule Manager.");
			server.startIMSScheduleManager();
		}
		
		server.publish();
	}
	
}
