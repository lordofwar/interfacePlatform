/**************************************************************************
 *
 * $RCSfile: FTPContext.java,v $  $Revision: 1.2 $  $Date: 2007/11/28 12:00:14 $
 *
 * $Log: FTPContext.java,v $
 * Revision 1.2  2007/11/28 12:00:14  fuzhw
 * MR#:NM60-0000
 * 放开code的长度约束
 *
 * Revision 1.1  2007/11/22 15:05:52  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/
package com.gxlu.interfacePlatform.foundation.ftp.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gxlu.interfacePlatform.foundation.sftp.SFTPFactory;
import com.gxlu.interfacePlatform.foundation.sftp.SFTPServer;

public class FTPContext
{
	private Log log = LogFactory.getLog(getClass());
	
	private static FTPContext instance ;
	
	private static final String logFileName = "ftpserver.log";
	
	
	private String ftpSiteFile = "/conf/ftpsite.xml";
	
	private Hashtable siteTable = new Hashtable();
	
	
	public static FTPContext getInstance()
	{
		if (null == instance)
		{
			instance = new FTPContext();
			
		}
		return instance;
	}
	
	public FTPContext()
	{
		try
		{
			initSites();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public FTPContext(String ftpSiteFile)
  {
    try
    {
      this.ftpSiteFile = ftpSiteFile; 
      initSites();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
	
	
	
	public void initSites() throws Exception
	{

//		if(log == null)
//			log = initLog(logFileName);
		
//		FTPContext.writeRuntimeLog("INFO", "FTPContext.initSites()");
//		
//		FTPContext.writeRuntimeLog("INFO", "init() parse xml file=" + ftpSiteFile);
		
		Document docFtpconf;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder parser = factory.newDocumentBuilder();
            ClassLoader cl = this.getClass().getClassLoader();
            File file = new File("."+ftpSiteFile);
            InputStream input =null;
            if(!file.exists()){
            	input = doGetURL(ftpSiteFile);
            }else{
            	input = new FileInputStream(file);
            }
            docFtpconf = parser.parse(input);
        }
        catch (Exception ex) {
        	log.error("init() parse xml exception.", ex);
            throw ex;
        }
        
        NodeList ftpList = docFtpconf.getElementsByTagName("ftp");
        Element ftpElement = (Element) ftpList.item(0);
        Element siteElement = null;
        
        for (int i = 0; i < ftpElement.getElementsByTagName("site").getLength(); i++) {
            siteElement = (Element) ftpElement.getElementsByTagName("site").item(i);
            FTPSite ftpSite = new FTPSite();
            initSite(siteElement, ftpSite);            
            siteTable.put(ftpSite.getSiteCode(), ftpSite);
//            FTPContext.writeRuntimeLog("INFO", "ftpSite:" + ftpSite.toString());
        }
        
		
	}
	
  public static InputStream doGetURL(String realpath) throws FileNotFoundException, IOException {
	  URL url = null;  
	    InputStream is  = null;
	    File file  = new File(realpath);
	    if(!file.exists()){
	        url = FTPContext.class.getResource(realpath);
	        if (url == null) {
	          ClassLoader cl = FTPContext.class.getClassLoader();
	          while (cl != null) {
	            url = cl.getResource(realpath);
	            if (url != null) {
	              break;
	            }
	            cl = cl.getParent();
	          }

	          if (url == null) {
	            url = ClassLoader.getSystemResource(realpath);
	          }
	        }

	        if (url == null) {
	          throw new FileNotFoundException(realpath);
	        }
	        is = url.openStream();
	    }else{
	    	is = new FileInputStream(file);
	    }
	      return is;
  }
	
	public FTPServer getFtpClient(String ftpSiteCode) throws IOException, FTPException
	{
		FTPSite ftpSite = (FTPSite)siteTable.get(ftpSiteCode);
		if(ftpSite == null)
		{
			log.error("ERROR: "+ftpSiteCode+"'FTP site is not found! ");
			return null;
		}
		return initFTPClient(ftpSite);	
	}
	
	private FTPServer initFTPClient(FTPSite ftpSite) throws IOException, FTPException
	{
		FTPServer ftp = new FTPServer();

		ftp.setRemoteHost(ftpSite.getHost());
		
		if(ftpSite.getEncoding() != null && !"".equals(ftpSite.getEncoding()))
			ftp.setControlEncoding(ftpSite.getEncoding());
		
		ftp.connect();
		
		ftp.login(ftpSite.getUser(), ftpSite.getPassword());
		
		if(ftpSite.getConnectMode()!= null && !"".equals(ftpSite.getConnectMode()))
		{
			if (ftpSite.getConnectMode().equalsIgnoreCase("PASV")) {
                ftp.setConnectMode(FTPConnectMode.PASV);
            }
            else if (ftpSite.getConnectMode().equalsIgnoreCase("ACTIVE")) {
                ftp.setConnectMode(FTPConnectMode.ACTIVE);
            }
            else
            {
            	log.error("ERROR: "+ftpSite.getConnectMode()+" is ERROR connectMode! ");
    			return null;
            }
		}
		
		if(ftpSite.getDirectory() != null && !"".equals(ftpSite.getDirectory()))
			ftp.chdir(ftpSite.getDirectory());
		
		if(ftpSite.getTransferType() != null && !"".equals(ftpSite.getTransferType()))
		{
			if (ftpSite.getTransferType().equalsIgnoreCase("BINARY")) {
                ftp.setType(FTPTransferType.BINARY);
            }
            else if (ftpSite.getTransferType().equalsIgnoreCase("ASCII")) {
                ftp.setType(FTPTransferType.ASCII);
            }
            else {
            	log.error("ERROR: "+ftpSite.getTransferType()+" is ERROR transferType! ");
    			return null;
            }
		}

		return ftp;

	}
	
	
	public Hashtable getSiteTable()
	{
		return siteTable;
	}

	public void setSiteTable(Hashtable siteTable)
	{
		this.siteTable = siteTable;
	}
	
	private void initSite(Element element,FTPSite ftpSite)throws Exception {
        String code = element.getAttribute("code");
        if (null == code || code.equalsIgnoreCase("") ) {
            throw new Exception("Messenger code is invalid, it must have two bytes at most. code=" + code);
        }
        ftpSite.setSiteCode(code);
        ftpSite.setSiteName(element.getAttribute("name"));
        if (element.getElementsByTagName("host").getLength() != 0) {
            Node node = element.getElementsByTagName("host").item(0).getFirstChild();
            if (null != node) {
            	ftpSite.setHost(node.getNodeValue());
            }
        }

        if (element.getElementsByTagName("port").getLength() != 0) {
            Node node = element.getElementsByTagName("port").item(0).getFirstChild();
            if (null != node) {
            	ftpSite.setPort(Integer.parseInt(node.getNodeValue()));
            }
        }

        if (element.getElementsByTagName("user").getLength() != 0) {
            Node node = element.getElementsByTagName("user").item(0).getFirstChild();
            if (null != node) {
            	ftpSite.setUser(node.getNodeValue());
            }
        }

        if (element.getElementsByTagName("password").getLength() != 0) {
            Node node = element.getElementsByTagName("password").item(0).getFirstChild();
            if (null != node) {
            	ftpSite.setPassword(node.getNodeValue());
            }
        }

        if (element.getElementsByTagName("transfertype").getLength() != 0) {
            Node node = element.getElementsByTagName("transfertype").item(0).getFirstChild();
            if (null != node) {
            	ftpSite.setTransferType(node.getNodeValue());
            }
        }
        
        if (element.getElementsByTagName("connectmode").getLength() != 0) {
            Node node = element.getElementsByTagName("connectmode").item(0).getFirstChild();
            if (null != node) {
            	ftpSite.setConnectMode(node.getNodeValue());
            }
        }
        
        if (element.getElementsByTagName("directory").getLength() != 0) {
            Node node = element.getElementsByTagName("directory").item(0).getFirstChild();
            if (null != node) {
            	ftpSite.setDirectory(node.getNodeValue());
            }
        }
        
        if (element.getElementsByTagName("encoding").getLength() != 0) {
            Node node = element.getElementsByTagName("encoding").item(0).getFirstChild();
            if (null != node) {
            	ftpSite.setEncoding(node.getNodeValue());
            }
        }
        
        if (element.getElementsByTagName("timeout").getLength() != 0) {
            Node node = element.getElementsByTagName("timeout").item(0).getFirstChild();
            if (null != node) {
            	ftpSite.setTimeout(Integer.parseInt(node.getNodeValue()));
            }
        }

    }
	
	public SFTPServer getSFTPServer(String ftpSiteCode) throws IOException, FTPException
	{
		FTPSite ftpSite = (FTPSite)siteTable.get(ftpSiteCode);
		if(ftpSite == null)
		{
			log.error("ERROR: "+ftpSiteCode+"'FTP site is not found! ");
			return null;
		}
		return SFTPFactory.getSFTPServer(ftpSite);	
	}

}
