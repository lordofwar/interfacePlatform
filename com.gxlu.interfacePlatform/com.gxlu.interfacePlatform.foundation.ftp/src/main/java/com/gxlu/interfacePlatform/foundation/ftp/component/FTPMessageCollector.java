/**************************************************************************
 *
 * $RCSfile: FTPMessageCollector.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:53 $
 *
 * $Log: FTPMessageCollector.java,v $
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
public class FTPMessageCollector implements FTPMessageListener {
    
    /**
     * Log of messages
     */
    private StringBuffer log = new StringBuffer();
    
    /**
     * Log an FTP command being sent to the server
     * 
     * @param cmd   command string
     */
    public void logCommand(String cmd) {
        log.append(cmd).append("\n");
    }
    
    /**
     * Log an FTP reply being sent back to the client
     * 
     * @param reply   reply string
     */
    public void logReply(String reply) {
        log.append(reply).append("\n");
    }
    
    /**
     * Get the log of messages
     * 
     * @return  message log as a string
     */
    public String getLog() {
        return log.toString();
    }
    
    /**
     * Clear the log of all messages
     */
    public void clearLog() {
        log = new StringBuffer();
    }

}
