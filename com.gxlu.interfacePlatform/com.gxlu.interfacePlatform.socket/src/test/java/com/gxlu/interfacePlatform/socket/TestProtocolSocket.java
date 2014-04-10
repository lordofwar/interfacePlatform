package com.gxlu.interfacePlatform.socket;

import java.io.IOException;

public class TestProtocolSocket {

	  public static void main(String [] args) throws IOException, InterruptedException {
		    
		    final ProtocolSocket socket = new DefaultProtocolSocket(SocketConfiguration.getSocketBySysCode("LLPM"));
		    
		    socket.start(new SocketListener(){
		    	
		    	public boolean isExitMsg(String responseText){
		    		if(responseText.equals("byebye")){
		    			try {
							Thread.sleep(1*1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    			System.out.println("bye!");
		    			return true;
		    		}
		    		return false;
		    	}
		    	
		    	public void handle(String responseText){
		    		System.out.println(responseText);
		    	}
		    	
		    });
		    
		    long start = System.currentTimeMillis();
		    socket.send("<REQUEST,COMMON,CONNECT,admin,llpmllpm>");
		    socket.send("<REQUEST,CONFIG,LIST_ALL_NES>");
		   	long end = System.currentTimeMillis();
		   	System.out.println((end-start)/1000);
		   	
		   	Thread.sleep(3*1000);
		   	System.out.println("KO");
		   	socket.send("byebye");
			System.out.println("End");
			Thread.sleep(3*1000);
		    socket.close();
		  }
}
