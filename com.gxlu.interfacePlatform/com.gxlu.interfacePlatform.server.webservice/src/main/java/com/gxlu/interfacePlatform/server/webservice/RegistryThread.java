package com.gxlu.interfacePlatform.server.webservice;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.interfacePlatform.server.webservice.ServerConfig.ServerConfigEntry;

public class RegistryThread {
	private static Log log = LogFactory.getLog(RegistryThread.class);
	private static Registry registry;
	private static Object lock = new Object();
	public static void main(String[] args) throws Exception {
		ServerConfigEntry entry=ServerConfig.getServerConfig().loadConfig();
		final int rmiPort = entry.getRmiPort();
		
		
	
		new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				
				try {
					registry = LocateRegistry.createRegistry(rmiPort);
					
					log.info(String.format("Create rmi registry on port %d", rmiPort));
					
					synchronized (lock) {
						lock.wait();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error("",e);
				}
			}
		}).start();
	}
}
