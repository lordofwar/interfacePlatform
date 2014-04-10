package com.gxlu.interfacePlatform.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SocketClient {
  
	private static final Log log = LogFactory.getLog(SocketClient.class);


  public String sendStr(Socket socket, String str) {
    String inputStr = null;
    try {
      OutputStream os = socket.getOutputStream();
      if(socket.isBound() && socket.isConnected() && str != null) {
        os.write(str.getBytes());
        os.flush();
        InputStream is = socket.getInputStream();
        inputStr = ParseUtil.inputStream2Str(is,socket);
      }
      else {
        System.out.println("Socket is unavailable and outString:" + str);
      }
    }
    catch(SocketTimeoutException e) {
      System.out.println("SocketInputStream is timeOut!");
      e.printStackTrace();
      return inputStr;
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    return inputStr;
  }

  public String sendStrBySysCode(String sysCode, String str) {
    String inputStr = null;
    final Socket socket;
    socket = SocketConfiguration.getSocketBySysCode(sysCode);
    inputStr = sendStr(socket, str);
    return inputStr;
  }
}
