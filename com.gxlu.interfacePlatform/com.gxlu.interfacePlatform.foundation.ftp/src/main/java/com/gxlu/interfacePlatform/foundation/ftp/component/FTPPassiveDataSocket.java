/**************************************************************************
 *
 * $RCSfile: FTPPassiveDataSocket.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:53 $
 *
 * $Log: FTPPassiveDataSocket.java,v $
 * Revision 1.1  2007/11/22 15:05:53  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/
package com.gxlu.interfacePlatform.foundation.ftp.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *  Passive data socket handling class
 */
public class FTPPassiveDataSocket implements FTPDataSocket {


    /**
     *  The underlying socket 
     */
    protected Socket sock = null;

    /**
     *  Constructor
     * 
     *  @param  sock  client socket to use
     */
    protected FTPPassiveDataSocket(Socket sock) {
        this.sock = sock;
    }

    /**
     *   Set the TCP timeout on the underlying control socket.
     *
     *   If a timeout is set, then any operation which
     *   takes longer than the timeout value will be
     *   killed with a java.io.InterruptedException.
     *
     *   @param millis The length of the timeout, in milliseconds
     */
    public void setTimeout(int millis) throws IOException {
        sock.setSoTimeout(millis);
    }
    
    /**
     * Returns the local port to which this socket is bound. 
     * 
     * @return the local port number to which this socket is bound
     */
    public int getLocalPort() {
        return sock.getLocalPort();
    }        

    /**
     *  If active mode, accepts the FTP server's connection - in PASV,
     *  we are already connected. Then gets the output stream of
     *  the connection
     *
     *  @return  output stream for underlying socket.
     */
    public OutputStream getOutputStream() throws IOException {        
        return sock.getOutputStream();
    }

    /**
     *  If active mode, accepts the FTP server's connection - in PASV,
     *  we are already connected. Then gets the input stream of
     *  the connection
     *
     *  @return  input stream for underlying socket.
     */
    public InputStream getInputStream() throws IOException {
        return sock.getInputStream();
    }

    /**
     *  Closes underlying socket
     */
    public void close() throws IOException {
        sock.close();
    }

    /**
     * Does nothing in passive mode
     */
    public void closeChild() throws IOException {
        // does nothing
    }
    
}
