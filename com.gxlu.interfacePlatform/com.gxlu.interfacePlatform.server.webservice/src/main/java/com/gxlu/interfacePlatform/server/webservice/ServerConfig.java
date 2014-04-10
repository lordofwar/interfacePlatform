package com.gxlu.interfacePlatform.server.webservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gxlu.interfacePlatform.server.webservice.services.ServerOperation;

public class ServerConfig {
	
	public static final String FILE_NAME="server.xml"; 
	public static final String DIR="conf";
	
	public static final String TYPE_MAIN="MAIN";
	public static final String TYPE_SUB="SUB";
	
	private static ServerConfig config= new ServerConfig();
	private ServerConfig() {
	}

	public static ServerConfig getServerConfig(){
		return config;
	}
	
	public ServerConfigEntry loadConfig() throws Exception{
		try{
			ServerConfigEntry entry=loadConfigInClassPath();
			
			ServerConfigEntry entryInFileSys=loadConfigInFileSystem();
			if(entryInFileSys!=null)
				copy(entryInFileSys,entry);
			
			if(TYPE_SUB.equals(entry.getType())){
				ServerConfigEntry remoteConfig = loadConfigFromParent(entry);
				if(remoteConfig!=null)
					copy(remoteConfig,entry);
			}
			return entry;
		}catch(Exception e){
			throw new Exception("Errors occur while reading configuration from server.xml",e);
		}
	}
	
	private ServerConfigEntry loadConfigFromParent(ServerConfigEntry local) throws Exception{
		
		if(local.getParentHost()!=null && local.getParentHostRMIPort()!=null){
			String rmiAddress = "rmi://"+local.getParentHost()+":"+local.getParentHostRMIPort()+"/Platform_Operation";
			ServerOperation serverOperation = (ServerOperation) Naming.lookup(rmiAddress);
			ServerConfigEntry remoteConfigEntry = serverOperation.findConfig(local.getId());
			return remoteConfigEntry;
		}
		return null;
		
	}
	private ServerConfigEntry loadConfigInClassPath() throws Exception{
		Document document= getDocumentInClassPath();
		ServerConfigEntry entry=parse(document);
		return entry;
	}
	
	private ServerConfigEntry loadConfigInFileSystem() throws Exception{
		Document document = getDocumentInFileSys();
		if(document!=null){
			ServerConfigEntry entryInFileSys=parse(document);
			return entryInFileSys;
		}
		return null;
	}
	
	private void copy(ServerConfigEntry source, ServerConfigEntry dest){
		
		if(source.getRmiPort()!=-1)
			dest.setRmiPort(source.getRmiPort());
		
		if(StringUtils.isNotBlank(source.getWsContextPath()))
			dest.setWsContextPath(source.getWsContextPath());
		
		if(StringUtils.isNotBlank(source.getType()))
			dest.setType(source.getType());
		
		if(StringUtils.isNotBlank(source.getDescription()))
			dest.setDescription(source.getDescription());
		
		if(StringUtils.isNotBlank(source.getId()))
			dest.setId(source.getId());
		
		if(StringUtils.isNotBlank(source.getParentHost()))
			dest.setParentHost(source.getParentHost());
		
		if(StringUtils.isNotBlank(source.getParentHostRMIPort()))
			dest.setParentHostRMIPort(source.getParentHostRMIPort());
		
		//While remote configuration.
		if(source.getSubServerList()!=null)
			dest.getSubServerList().addAll(source.getSubServerList());
	}
	
	private ServerConfigEntry parse(Document document) throws Exception{
		
		XPath xPath =  XPathFactory.newInstance().newXPath();
		
		String expression = "/Server";
		Node serverNode=(Node)xPath.evaluate(expression, document, XPathConstants.NODE);
		
		String id=(String)xPath.evaluate("/Server/@id", document, XPathConstants.STRING);
		
		String rmiPortStr=(String)xPath.evaluate("/Server/@rmiPort", document, XPathConstants.STRING);
		int rmiPort= -1;
		if(StringUtils.isNotBlank(rmiPortStr)){
			rmiPort= Integer.parseInt(rmiPortStr);
		}
		
		String wsContextPath =(String)xPath.evaluate("/Server/@wsContextPath", document, XPathConstants.STRING); 
        
        String type= (String)xPath.evaluate("/Server/Type/text()", document, XPathConstants.STRING);
		
		String description= (String)xPath.evaluate("/Server/Description/text()", document, XPathConstants.STRING);
		
		String parentHost=(String)xPath.evaluate("/Server/@parentHost", document, XPathConstants.STRING);
		
		String parentHostRMIPort=(String)xPath.evaluate("/Server/@parentHostRMIPort", document, XPathConstants.STRING);
		
		ServerConfigEntry entry=  new ServerConfigEntry(rmiPort,wsContextPath);
	    entry.setId(id);
	    entry.setDescription(description);
	    entry.setType(type);
	    
	    entry.setParentHost(parentHost);
	    entry.setParentHostRMIPort(parentHostRMIPort);
	    
	    entry.setSubServerList(parseSubserver(document));
	    
		return entry;
	}
	
	private List<ServerConfigEntry> parseSubserver(Document document)
			throws Exception {
		List<ServerConfigEntry> subServerList = new ArrayList<ServerConfigEntry>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList subServerNodeList = (NodeList) xPath.evaluate("/Server/SubServer", document, XPathConstants.NODESET);
		if (subServerNodeList != null) {
			for (int i = 0; i < subServerNodeList.getLength(); i++) {
				Node subserver = subServerNodeList.item(i);
				String id = (String) xPath.evaluate("@id",subserver, XPathConstants.STRING);
				String rmiPortStr = (String) xPath.evaluate("@rmiPort",subserver, XPathConstants.STRING);
				String wsContextPath = (String) xPath.evaluate("@wsContextPath", subserver, XPathConstants.STRING);
				int rmiPort = -1;
				if (StringUtils.isNotBlank(rmiPortStr)) {
					rmiPort = Integer.parseInt(rmiPortStr);
				}
				ServerConfigEntry subServerEntry = new ServerConfigEntry(rmiPort,wsContextPath);
				subServerEntry.setId(id);
				subServerList.add(subServerEntry);
			}
		}
		return subServerList;
	}
	/**
	 * Add Service from class path. Default is classpath:services.xml.
	 * @return
	 * @throws Exception
	 */
	private Document getDocumentInClassPath()throws Exception{
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder =  builderFactory.newDocumentBuilder();
		
		Document doc = builder.parse(ServiceLoader.class.getResourceAsStream("/"+FILE_NAME));
		
		return doc;
	}
	/**
	 * Add Service from external configuration. Default is conf/services.xml.
	 * @return
	 * @throws Exception
	 */
	private Document getDocumentInFileSys()throws Exception{
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder =  builderFactory.newDocumentBuilder();
		File file = new File(DIR,FILE_NAME);
		if(!file.exists())
			return null;
		
		FileInputStream inStream =new FileInputStream(file);
		try{
			Document doc = builder.parse(inStream);
			return doc;
		}finally{
			inStream.close();
		}
	}
	
	public static class ServerConfigEntry implements Serializable{
		private String id;
		private int rmiPort;
		private String wsContextPath;
		private String type;
		private String description;
		
		private String parentHost;
		private String parentHostRMIPort;
		private List<ServerConfigEntry> subServerList ;
		
		public ServerConfigEntry(){}
		public ServerConfigEntry(int rmiPort,
				String wsContextPath) {
			super();
			this.rmiPort = rmiPort;
			this.wsContextPath = wsContextPath;
		}
		
		public int getRmiPort() {
			return rmiPort;
		}
		public void setRmiPort(int rmiPort) {
			this.rmiPort = rmiPort;
		}
		public String getWsContextPath() {
			return wsContextPath;
		}
		public void setWsContextPath(String wsContextPath) {
			this.wsContextPath = wsContextPath;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getParentHost() {
			return parentHost;
		}

		public void setParentHost(String parentHost) {
			this.parentHost = parentHost;
		}

		public String getParentHostRMIPort() {
			return parentHostRMIPort;
		}

		public void setParentHostRMIPort(String parentHostRMIPort) {
			this.parentHostRMIPort = parentHostRMIPort;
		}

		public List<ServerConfigEntry> getSubServerList() {
			return subServerList;
		}

		public void setSubServerList(List<ServerConfigEntry> subServerList) {
			this.subServerList = subServerList;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
		public ServerConfigEntry findSubServer(String id){
			if(subServerList==null) return null;
			
			for(ServerConfigEntry entry : subServerList){
				if(id.equals(entry.getId())){
					return entry;
				}
			}
			return null;
		}
	}
	
}
