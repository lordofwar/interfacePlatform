/**************************************************************************
 *
 * $RCSfile: SocketUtils.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:55 $
 *
 * $Log: SocketUtils.java,v $
 * Revision 1.1  2007/11/22 15:05:55  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/
package com.gxlu.interfacePlatform.foundation.ftp.component;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SocketUtils {
    
    /**
     * Logging object
     */
	private static Log log = LogFactory.getLog(SocketUtils.class);

    /**
     * Create a connected socket, using a timeout if it is available. 
     * Availability is tested by trying to create instances of the 
     * required classes and methods (JRE 1.4+)
     * 
     * @param host     remote host to connect to
     * @param port     port on remote host
     * @param timeout  timeout in milliseconds on 
     * @exception IOException
     */
    public static Socket createSocket(InetAddress host, int port, int timeout)
            throws IOException {

        // don't bother going thru the below if a timeout isn't asked for ...
        if (timeout == 0) {
            return new Socket(host, port);
        } 
        else {
            // attempt to set up 1.4 and later's Socket.connect method which
            // provides a timeout
            try {
                // get the correct connect method
                Class socketAddress = Class.forName("java.net.SocketAddress");
                Method connectMethod = Socket.class.getMethod("connect", new Class[] {
                        socketAddress, int.class });
                
                // create an unconnected socket instance
                Socket sock = (Socket) Socket.class.newInstance();

                // need an InetSocketAddress instance for connect()
                Class inetSocketAddress = Class.forName("java.net.InetSocketAddress");
                Constructor inetSocketAddressCtr = 
                    inetSocketAddress.getConstructor(
                            new Class[] { InetAddress.class, int.class });
                Object address = inetSocketAddressCtr.newInstance(
                        new Object[] { host, new Integer(port) });

                // now invoke the connect method with the timeout
                log.debug("Invoking connect with timeout=" + timeout);
                connectMethod.invoke(sock, new Object[]{address, new Integer(timeout)});
                return sock;
            } 
            catch (InvocationTargetException ex) {
                Throwable target = ex.getTargetException();
                if (target instanceof IOException)
                    throw (IOException)target;
                log.debug("Could not use timeout connecting to host (" + ex.toString() + ")");
                return new Socket(host, port);
            } 
            catch (Exception ex) {
                log.debug("Could not use timeout connecting to host (" + ex.toString() + ")");
                return new Socket(host, port);
            }
        }
    }
    
}
