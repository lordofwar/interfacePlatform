package com.gxlu.interfacePlatform.server.webservice;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import javax.xml.ws.Endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.interfacePlatform.server.webservice.ServiceLoader.ServiceConfigEntry;

public class AbstractServicePublishServer {
	private static Log log = LogFactory.getLog(AbstractServicePublishServer.class);
	
	private String contextPath;
	private int rmiPort;
	private static Registry registry;
	
	public AbstractServicePublishServer() throws IOException{
		
	}
	
	public AbstractServicePublishServer(int rmiPort,String wsContextPath){
		this.rmiPort=rmiPort;
		this.contextPath=wsContextPath;
	}
	
	public void publish() throws IOException {
		
		initRMI();
		
		publishServicePlugin();
		
	}


	private void initRMI() throws RemoteException {
			registry = LocateRegistry.createRegistry(rmiPort);
			log.info(String.format("Get RMI Server via port[%s].",rmiPort));
	}
	
	private void publishServicePlugin(){
		try {
			 List<ServiceConfigEntry> entries = ServiceLoader.getServiceLoader().loadConfig();
			 for(ServiceConfigEntry entry: entries){
				 String path = entry.getPath();
				 String clazz = entry.getClazz();
				 
				 publishServicePlugin(path,clazz);
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void publishServicePlugin(String path,String clazz){
		try {
			Class<?> classEntry = Class.forName(clazz);
			Object object =classEntry.newInstance();
			
			if(object instanceof Remote){
				publishRMIService(path,(Remote)object);
			}else{
				publishWebService(path,object);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			log.error("Cannot find the class.", e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}
	}
	
	protected void publishRMIService(String path,Remote remote) throws AccessException, RemoteException{
		Remote stub = (Remote) UnicastRemoteObject.exportObject(remote, 0);
		try {
			registry.bind(path, stub);
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			registry.rebind(path, stub);
		}
		log.info(String.format("Add rmi service > [%s|%s]",path,remote.getClass().getName()));
	}
	
	protected void publishWebService(String path, Object object) {
		// TODO Auto-generated method stub
		String address = contextPath + path;
		Endpoint.publish(address, object);
		log.info(String.format("Add webservice > [%s|%s]",path,object.getClass().getName()));
	}
}
