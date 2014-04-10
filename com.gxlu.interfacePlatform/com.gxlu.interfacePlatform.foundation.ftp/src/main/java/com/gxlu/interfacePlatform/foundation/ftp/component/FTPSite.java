/**************************************************************************
 *
 * $RCSfile: FTPSite.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:54 $
 *
 * $Log: FTPSite.java,v $
 * Revision 1.1  2007/11/22 15:05:54  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/
package com.gxlu.interfacePlatform.foundation.ftp.component;

/**
 * <p>ftp site</p>
 * <p>Title: ftp站点</p>
 * <p>Description: 维护FTP站点列表</p>
 * <p>Copyright: Copyright (c) 2007.11</p>
 * <p>Company: Guoxin Lucent</p>
 * @author Fu Zhongwei
 * @version 1.0
 */
public class FTPSite
{
	/**
     * 系统名称
     */
    private String siteName;

    /**
     * 系统代号
     */
    private String siteCode;
    
	private String host ;
	
	private int port = FTPControlSocket.CONTROL_PORT;
	
	private String user ;
	
    private String password ;
    
    private String directory ;
    
    private String transferType = "ASCII";
    
    private String connectMode = "PASV";
    
    protected int timeout = 0;
    
    protected String encoding = "US-ASCII";

	public String getConnectMode()
	{
		return connectMode;
	}

	public void setConnectMode(String connectMode)
	{
		this.connectMode = connectMode;
	}

	public String getDirectory()
	{
		return directory;
	}

	public void setDirectory(String directory)
	{
		this.directory = directory;
	}

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getTransferType()
	{
		return transferType;
	}

	public void setTransferType(String transferType)
	{
		this.transferType = transferType;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}
    

	public String getSiteCode()
	{
		return siteCode;
	}

	public void setSiteCode(String systemCode)
	{
		this.siteCode = systemCode;
	}

	public String getSiteName()
	{
		return siteName;
	}

	public void setSiteName(String systemName)
	{
		this.siteName = systemName;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		if (null != siteName)
		{
			sb.append("systemName=");
			sb.append(siteName);
		}

		if (null != siteCode)
		{
			sb.append(", ");
			sb.append("systemCode=");
			sb.append(siteCode);
		}
		
		if (null != host)
		{
			sb.append(", ");
			sb.append("host=");
			sb.append(host);
		}
		
		if ( port >0)
		{
			sb.append(", ");
			sb.append("port=");
			sb.append(port);
		}
		else
		{
			sb.append(", ");
			sb.append("port=");
			sb.append(FTPControlSocket.CONTROL_PORT);
		}
		
		if (null != user)
		{
			sb.append(", ");
			sb.append("user=");
			sb.append(user);
		}
		
		if (null != password)
		{
			sb.append(", ");
			sb.append("password=");
			sb.append(password);
		}
		
		return sb.toString();
	}

	public int getTimeout()
	{
		return timeout;
	}

	public void setTimeout(int timeout)
	{
		this.timeout = timeout;
	}

	public String getEncoding()
	{
		return encoding;
	}

	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}

}
