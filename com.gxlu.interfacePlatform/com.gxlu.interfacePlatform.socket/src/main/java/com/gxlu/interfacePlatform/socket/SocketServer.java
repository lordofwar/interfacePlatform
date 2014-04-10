package com.gxlu.interfacePlatform.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class SocketServer {
  private ServerSocket serverSocekt;
  private static final Log log = LogFactory.getLog(SocketServer.class);
  private static int port = 12345;
  private static int maxThread=50;
  private static int timeout=120000;
  
  static { 
    XMLFileReader xmlFileReader = new XMLFileReader("SocketConfig.xml");
    String timeO = xmlFileReader.getPorpertyText("/config/server/timeout");
    String maxT = xmlFileReader.getPorpertyText("/config/server/maxthread");
    String p = xmlFileReader.getPorpertyText("/config/server/port");
    if(timeO != null && !timeO.equals("")){
      timeout = Integer.valueOf(timeO);
    }
    if(maxT != null && !maxT.equals("")){
      maxThread = Integer.valueOf(maxT);
    }
    if(p != null && !p.equals("")){
      port = Integer.valueOf(p);
    }
    log.info("timeout:"+timeout+"maxThread:"+maxThread+"port:"+port);
  }
  
  
  public void start(){
    GxluServerSocketFactory serverFactory = new GxluServerSocketFactory();
    try{
      ServerSocket serverSocekt = serverFactory.createServerSocket(port);
      this.serverSocekt =serverSocekt;
      while(true) {
        final Socket socket = serverSocekt.accept();
        socket.setSoTimeout(timeout);
        if(Thread.activeCount()<SocketServer.maxThread){
          new Thread(){
            public void run() {
              System.out.println("new Thread!");
              try {
                InputStream is = socket.getInputStream();
                while(true && socket.isConnected() && socket.isBound() && !socket.isClosed())
                {
                  if(is !=null){
                    executeByIs(is,socket);
                  }else{
                    ParseUtil.writeOut("InputMessage Can't be null!", socket);
                  }
                }
              }
              catch(IOException e) {
               log.error("InputMessage IOException:",e);
              }
            }
          }.start();
        }else{
          ParseUtil.writeOut("connection pool is full,please wait a minite!", socket);
        }
      }
    }catch(SocketTimeoutException toe){
      log.error("socket time out!"+timeout+"ms", toe);
    }catch(IOException e){
      log.error("SocketServer start exception:", e);
    }
  }
  
  
  protected abstract void executeByIs(InputStream is,Socket socket) throws IOException;
  
  public void restart(){
    stop();
    start();
  }
  
  public void stop(){
    if(serverSocekt != null && !serverSocekt.isClosed()){
      try {
        serverSocekt.close();
      }
      catch(IOException e) {
        log.error("SocketServer stop exception:", e);
      }
    }
  }

  public ServerSocket getServerSocekt() {
    return serverSocekt;
  }

  public void setServerSocekt(ServerSocket serverSocekt) {
    this.serverSocekt = serverSocekt;
  }
  
}
