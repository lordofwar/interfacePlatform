package com.gxlu.interfacePlatform.server.webservice;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ServiceLoader {
	
	public static final String FILE_NAME="services.xml"; 
	public static final String DIR="conf";
	private static ServiceLoader config= new ServiceLoader();
	private ServiceLoader() {
	}

	public static ServiceLoader getServiceLoader(){
		return config;
	}
	
	public List<ServiceConfigEntry> loadConfig() throws Exception{
		Document document= getDocumentInClassPath();
		List<ServiceConfigEntry> entries=parse(document);
		
		document = getDocumentInFileSys();
		if(document!=null){
			List<ServiceConfigEntry> entriesExternal=parse(document);
			entries.addAll(entriesExternal);
		}
		
		return entries;
	}
	
	private List<ServiceConfigEntry> parse(Document document) throws Exception{
		
		List<ServiceConfigEntry> entries=new ArrayList<ServiceConfigEntry>();
		
		XPath xPath =  XPathFactory.newInstance().newXPath();
		String expression = "/Services/Service";
		
		NodeList serviceNodeList=(NodeList)xPath.evaluate(expression, document, XPathConstants.NODESET);
		for (int i = 0; i < serviceNodeList.getLength(); i++) {
            Node serviceNode = serviceNodeList.item(i); 
            String path = serviceNode.getAttributes().getNamedItem("path").getNodeValue();
            String clazz = serviceNode.getAttributes().getNamedItem("class").getNodeValue();
            
            entries.add(new ServiceConfigEntry(path,clazz));
            
        }
		return entries;
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
	
	public class ServiceConfigEntry{
		private String path;
		private String clazz;
		
		public ServiceConfigEntry(String path, String clazz) {
			super();
			this.path = path;
			this.clazz = clazz;
		}
		
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getClazz() {
			return clazz;
		}
		public void setClazz(String clazz) {
			this.clazz = clazz;
		}
		
	}
}
