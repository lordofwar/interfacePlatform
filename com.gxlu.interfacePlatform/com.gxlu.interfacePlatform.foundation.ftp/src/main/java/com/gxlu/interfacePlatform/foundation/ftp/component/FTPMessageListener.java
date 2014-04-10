/**************************************************************************
 *
 * $RCSfile: FTPMessageListener.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:53 $
 *
 * $Log: FTPMessageListener.java,v $
 * Revision 1.1  2007/11/22 15:05:53  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/

package com.gxlu.interfacePlatform.foundation.ftp.component;

/**
 *  Listens for and is notified of FTP commands and replies. 
 *
 */
public interface FTPMessageListener {
    
    /**
     * Log an FTP command being sent to the server
     * 
     * @param cmd   command string
     */
    public void logCommand(String cmd); 
    
    /**
     * Log an FTP reply being sent back to the client
     * 
     * @param reply   reply string
     */
    public void logReply(String reply); 

}
