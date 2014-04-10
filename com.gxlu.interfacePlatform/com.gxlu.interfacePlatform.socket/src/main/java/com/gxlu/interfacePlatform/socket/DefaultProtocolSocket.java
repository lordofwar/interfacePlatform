package com.gxlu.interfacePlatform.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 
 * @see {@link ProtocolSocket}
 * @author Jame
 *
 */
public class DefaultProtocolSocket implements ProtocolSocket{

	private static Log logger = LogFactory.getLog(DefaultProtocolSocket.class);
	private final Socket socket ;
	private boolean isStarted = false;
	private PrintWriter writer;
	private SocketListener listener;
	private Object lock = new Object();
	
	public DefaultProtocolSocket(Socket socket){
		this.socket=socket;
		
	}
	
	@Override
	public void start() throws IOException {
		// TODO Auto-generated method stub
		this.start(null);
	}

	@Override
	public void start(final SocketListener listener) throws IOException {
		// TODO Auto-generated method stub
		
		if(!isStarted){
			isStarted=true;
			writer = new PrintWriter(socket.getOutputStream(), true);
			this.listener=listener;
			Thread thread = getListenThread();
			thread.start();
		}
	}

	private Thread getListenThread() throws IOException{
		
		final BufferedReader buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		return new Thread(){
			
			@Override
			public void run() {
				
				// TODO Auto-generated method stub
				while(isStarted){
					try {
						String responseText = buff.readLine();
						if(responseText!=null){
							if(null != listener){
								if(listener.isExitMsg(responseText)){
									exit();
									return;//stop listen
									
								}else{
									listener.handle(responseText);
								}
							}
							
						}
						
						Thread.sleep(200);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;//stop listen;
					}
				}
				
				exit();
			}
	    	
	    };
	}
	
	private void exit(){
		try {
	        socket.close();
	        logger.info("Socket Closed !");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void changeListener(SocketListener listener) {
		// TODO Auto-generated method stub
		synchronized (this.lock) {
			this.listener=listener;
		}
	}

	@Override
	public void send(String msg) {
		// TODO Auto-generated method stub
		logger.info(String.format("Sending msg to server : %s",msg));
	   	writer.println(msg);
	}

	public void close(){
		this.stopListenning();
	}
	
	private void stopListenning() {
		// TODO Auto-generated method stub
		this.isStarted=false;
	}
}
