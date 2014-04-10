/**************************************************************************
 *
 * $RCSfile: FTPDataSocket.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:52 $
 *
 * $Log: FTPDataSocket.java,v $
 * Revision 1.1  2007/11/22 15:05:52  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/
package com.gxlu.interfacePlatform.foundation.ftp.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  Interface for data socket classes, whether active or passive
 *
 */
public interface FTPDataSocket {


    /**
     *   Set the TCP timeout on the underlying control socket.
     *
     *   If a timeout is set, then any operation which
     *   takes longer than the timeout value will be
     *   killed with a java.io.InterruptedException.
     *
     *   @param millis The length of the timeout, in milliseconds
     */
    public void setTimeout(int millis) throws IOException;
    
    /**
     * Returns the local port to which this socket is bound. 
     * 
     * @return the local port number to which this socket is bound
     */
    public int getLocalPort();

    /**
     *  Get the appropriate output stream for writing to
     *
     *  @return  output stream for underlying socket.
     */
    public OutputStream getOutputStream() throws IOException;

    /**
     *  Get the appropriate input stream for reading from
     *
     *  @return  input stream for underlying socket.
     */
    public InputStream getInputStream() throws IOException;

     /**
      *  Closes underlying socket(s)
      */
    public void close() throws IOException;
    
    /**
     * Closes child socket
     * 
     * @throws IOException
     */
    public void closeChild() throws IOException;
}

