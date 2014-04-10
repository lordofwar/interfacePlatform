package com.gxlu.interfacePlatform.socket;

import java.io.IOException;
import java.net.Socket;


public class TestClient {

  public static void main(String [] args) throws IOException, InterruptedException {
    
    SocketClient client = new SocketClient();
    Socket socket = SocketConfiguration.getSocketBySysCode("LLPM");
    client.sendStr(socket, "test");
//    for(int i=0; i< 300; i++){
//      String aa = client.sendStr(socket, "test");
//      System.out.println("testClient:"+aa);
//    }
    
    String response =client.sendStr(socket, "<REQUEST,COMMON,CONNECT,admin,llpmllpm>\r\n");
    System.out.println(response);
  }
}
