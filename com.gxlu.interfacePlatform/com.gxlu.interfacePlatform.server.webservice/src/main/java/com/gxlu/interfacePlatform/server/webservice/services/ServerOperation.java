package com.gxlu.interfacePlatform.server.webservice.services;

import java.rmi.Remote;

import com.gxlu.interfacePlatform.server.webservice.ServerConfig.ServerConfigEntry;

public interface ServerOperation extends Remote {

	public void shutdown() throws Exception;
	
	public ServerConfigEntry findConfig(String id) throws Exception;
	
}
