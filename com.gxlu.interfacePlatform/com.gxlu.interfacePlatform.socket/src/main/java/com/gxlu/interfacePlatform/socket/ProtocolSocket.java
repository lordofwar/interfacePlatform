package com.gxlu.interfacePlatform.socket;

import java.io.IOException;

/**
 * ProtocolSocket is a socket client using transport protocol, etc http, ftp and so on.
 * 
 * @author Jame
 *
 */
public interface ProtocolSocket {

	public void start()throws IOException;
	
	public void start(SocketListener listener) throws IOException;
	
	public void send(String msg);
	
	public void changeListener(SocketListener listener);
	
	public void close();
}
