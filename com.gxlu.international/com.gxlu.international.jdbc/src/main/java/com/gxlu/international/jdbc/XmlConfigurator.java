package com.gxlu.international.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class XmlConfigurator {
	private Log log = LogFactory.getLog(getClass());
	// singleton instance
	private static XmlConfigurator configurator = null;
	
	// �����ļ�
	private String configfile = "/conf/jdbc.xml";
	
	private Document document = null;
	
	
	/**
	 * ���캯�� 
	 */
	private XmlConfigurator()
	{
       // ClassLoader cl = this.getClass().getClassLoader();
		File file = new File("."+configfile);
		InputStream input =null;
		if(!file.exists()){
			 input=this.getClass().getResourceAsStream(configfile);
		}else{
			try {
				input = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				log.error("jdbc�����ļ����ش���", e);
			}
		}

		try
		{
			document = XmlUtil.newXMLDocument(input);
		}
		catch (Exception e)
		{
			log.fatal(e);
			return;
		}		
		
	}


	public static XmlConfigurator getInstance()
	{
		if (configurator == null)
			configurator = new XmlConfigurator();
		
		return configurator;
	}

	
	/**
	 * �������õĽڵ�Ԫ��
	 * 
	 * @return List
	 */
	public List getConfiguratorElements(String path)
	{
		try
		{
			return XmlUtil.getElementsFromXMLDocumentByPath(document, "etlftp/" + path);
		}
		catch (Exception e)
		{
			log.error(e);
			return null;
		}		
	}

	
	/**
	 * �������õĽڵ�Ԫ��
	 * 
	 * @return element
	 */
	public Element getConfiguratorElement(String path)
	{
		List eles = getConfiguratorElements(path);
		
		if (eles == null || eles.isEmpty())
			return null;
		
		return (Element)eles.get(0);
	}

	
	/**
	 * ����Database���õĽڵ�
	 * 
	 * @return db element
	 */
	public Element getDatabaseConfiguratorElement()
	{
		return getConfiguratorElement("database");
	}
	
	/**
	 * ����job���õĽڵ�
	 * 
	 * @return db element
	 */
	public Element getJobConfiguratorElement()
	{
		return getConfiguratorElement("job");
	}
	

	public static void main(String[] args)
	{
//		XmlConfigurator config = XmlConfigurator.getInstance();
//		List filters = config.getAllFilters();
//		Element eleRmi = config.getRmiConfiguratorElement();
//		Element eleDB = config.getDatabaseConfiguratorElement();
//		Element eleIRP = config.getConfiguratorElement("irp/usable");
	}
	

}