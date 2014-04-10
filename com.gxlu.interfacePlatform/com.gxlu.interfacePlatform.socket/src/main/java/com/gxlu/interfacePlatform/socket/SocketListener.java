package com.gxlu.interfacePlatform.socket;

/**
 * SocketListener is used with ProtocolSocket.
 * It's designed to handle msg returned from server.
 * 
 * @author pudding
 *
 */
public interface SocketListener {
	
	public boolean isExitMsg(String responseText);
		
	public void handle(String responseText);
}
