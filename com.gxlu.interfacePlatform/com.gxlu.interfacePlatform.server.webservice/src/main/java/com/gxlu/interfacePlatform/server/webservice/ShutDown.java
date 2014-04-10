package com.gxlu.interfacePlatform.server.webservice;

import java.net.InetAddress;
import java.rmi.Naming;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.interfacePlatform.server.webservice.ServerConfig.ServerConfigEntry;
import com.gxlu.interfacePlatform.server.webservice.services.ServerOperation;

public class ShutDown {
	private static Log log = LogFactory.getLog(ShutDown.class);
	
	public static void main(String[] args) throws Exception {
		ServerConfigEntry entry=ServerConfig.getServerConfig().loadConfig();
		String host = InetAddress.getLocalHost().getHostAddress();
		String rmiAddress = "rmi://"+host+":"+entry.getRmiPort()+"/"+String.format("%s_Operation",entry.getId());
		ServerOperation serverOperation = (ServerOperation) Naming.lookup(rmiAddress);
		log.info(String.format("Shutdown server [%s:%s] ",host,entry.getRmiPort()));
		serverOperation.shutdown();
	}
}
