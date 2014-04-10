package com.gxlu.interfacePlatform.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TestServer {
  public static void main(String [] args) {
    SocketServer server = new SocketServer() {
    protected void executeByIs(InputStream is, final Socket socket) throws IOException{
      try{
        String inputStr = ParseUtil.inputStream2Str(is,socket);
        if(inputStr != null && !inputStr.equals("")){
          System.out.println(inputStr);
          if(inputStr.indexOf("test") != -1){
            ParseUtil.writeOut("has received,中文测试", socket);
            Thread.sleep(1000);
            ParseUtil.writeOut("<RESPONSE,SUCCESSED,requestId,DATA_Trail_20130605180120.zip>", socket);
          }else if(inputStr.contains("byebye")){
            System.out.println("链接关闭");
            ParseUtil.writeOut("byebye", socket);
            socket.close();
          }else{
            ParseUtil.writeOut("nothing", socket);
          }
          Thread.sleep(10000);
          ParseUtil.writeOut("<RESPONSE,SUCCESSED,requestId,DATA_Trail_20130605180120.zip>", socket);
        }
      }catch(IOException e){
        System.out.println("");
        e.printStackTrace();
      }
      catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
  };
  server.start();
  }
}
