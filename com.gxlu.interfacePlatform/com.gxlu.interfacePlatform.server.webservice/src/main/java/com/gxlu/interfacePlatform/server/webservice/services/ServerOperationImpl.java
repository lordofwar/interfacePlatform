package com.gxlu.interfacePlatform.server.webservice.services;

import com.gxlu.interfacePlatform.server.webservice.ServerConfig;
import com.gxlu.interfacePlatform.server.webservice.ServerConfig.ServerConfigEntry;


public class ServerOperationImpl implements ServerOperation{

	public void shutdown() throws Exception {
		// TODO Auto-generated method stub
		new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(1);
			}
			
		}.start();
	}

	public ServerConfigEntry findConfig(String id) throws Exception {
		// TODO Auto-generated method stub
		ServerConfigEntry entry=ServerConfig.getServerConfig().loadConfig();
		return entry.findSubServer(id);
	}

}
