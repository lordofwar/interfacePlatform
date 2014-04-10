/**************************************************************************
 * $RCSfile: GxluServerSocket.java,v $  $Revision: 1.3 $  $Date: 2005/01/21 07:03:38 $
 *
 * $Log: GxluServerSocket.java,v $
 * Revision 1.3  2005/01/21 07:03:38  brookqi
 * MR#: NM60-785
 * ʹaccept()�������JDK1.4��ʵ��
 *
 * Revision 1.2  2004/03/30 12:07:39  brookqi
 * MR#: NM60-370
 * ��log4j���������Ϣ
 *
 ***************************************************************************/
package com.gxlu.interfacePlatform.socket;

import java.io.*;
import java.net.*;


public class GxluServerSocket extends ServerSocket {

	public GxluServerSocket(int port) throws IOException {
        super(port);
    }

  
	/**
     * Creates a socket with a assigned port of type GxluSocket and then calls
     * implAccept to wait for a client connection.
     */
    public Socket accept() throws IOException {
    	
    	if (isClosed())
    	    throw new SocketException("Socket is closed");
    	if (!isBound())
    	    throw new SocketException("Socket is not bound yet");

        Socket s = new GxluSocket();
        implAccept(s);
        System.out.println("hostaddress : " + s.getInetAddress().getHostAddress());
        System.out.println("localport : " + s.getLocalPort());
        System.out.println("remoteport : " + s.getPort());
        
        return s;
    }
    
}
