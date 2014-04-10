package com.gxlu.international.jdbc;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mchange.v2.c3p0.ComboPooledDataSource;
 
public class DBAdapter {
	 private static DBAdapter instance = null;
	    private int dbType ;
	    private String dataSource = null;
	    private String driver = null;
	    private String url = null;
	    private Properties connPro = new Properties();
	    private Log log = LogFactory.getLog(getClass());
	    private ComboPooledDataSource cpds;	   
	    
	    private DBAdapter(){
	    	loadConfig();
	    	
	        cpds = new ComboPooledDataSource();
	        try {
				cpds.setDriverClass(driver);
				cpds.setJdbcUrl(url);
				cpds.setProperties(connPro);                          
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				log.error("Initial DataSources fails.",e);
			} //loads the jdbc driver            
	    }

	    /**
	     * load configuration information from XML file.
	     * 
	     */
	    private void loadConfig(){
	    	
			// 获取DB配置参赛
			Element eleDB = XmlConfigurator.getInstance().getDatabaseConfiguratorElement();
			if (eleDB == null)
			{
				log.error("no db configurator found, pls check config file!");
				return;
			}

			String sDBType = "oracle";
			
			NodeList listParam = eleDB.getChildNodes();
			for (int i = 0; i < listParam.getLength(); i++)
			{
				Node param = listParam.item(i);
				if (!(param instanceof Element))
					continue;
			
				Element eleChild = (Element)param;
				String tagName = eleChild.getTagName();
				
				if (tagName.equalsIgnoreCase("DataSource"))
				{
					dataSource = eleChild.getFirstChild().getNodeValue().trim();
				}
				else if (tagName.equalsIgnoreCase("Driver"))
				{
					driver = eleChild.getFirstChild().getNodeValue().trim();
				}
				else if (tagName.equalsIgnoreCase("URL"))
				{
					url = eleChild.getFirstChild().getNodeValue().trim();
				}
				else if (tagName.equalsIgnoreCase("User"))
				{
					String user = eleChild.getFirstChild().getNodeValue().trim();
					connPro.put("user", user);
				}
				else if (tagName.equalsIgnoreCase("Password"))
				{
					String password = eleChild.getFirstChild().getNodeValue().trim();
					connPro.put("password", password);
				}
				else if (tagName.equalsIgnoreCase("dbType"))
				{
					sDBType = eleChild.getFirstChild().getNodeValue().trim();
					if ("sybase".equalsIgnoreCase(sDBType))
						dbType = SysConst.DATABASE_TYPE_SYBASE;
				}
			}
	        
			log.info("\nDataSource : " + dataSource + "\nDriver : " + driver
	            + "\nURL : " + url + "\nUser : " + connPro.get("user")
	            + "\nPassword : " + connPro.get("password")
	            + "\ndbType : " + sDBType);

	    }

	    public static DBAdapter getInstance(){
	        if(instance == null){
	            instance = new DBAdapter();
	        }
	        return instance;
	    }

	    public int getDBType(){
	        return dbType;
	    }

	    public Connection getConnection(){
	    	try {
				return cpds.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.error("getConnection fail:",e);
				return null;
			}
	    }

}
