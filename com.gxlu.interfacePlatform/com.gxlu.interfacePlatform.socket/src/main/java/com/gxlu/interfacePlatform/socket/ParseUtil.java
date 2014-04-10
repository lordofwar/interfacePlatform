package com.gxlu.interfacePlatform.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ParseUtil {
  private static final Log log = LogFactory.getLog(ParseUtil.class);

  public static String inputStream2Str(InputStream is, Socket socket) {
    String str = null;
    try {
      byte [] buffer = new byte [1024];
      int length = is.read(buffer);
      str = new String(buffer, 0, length);
    }
    catch(SocketException se) {
      se.printStackTrace();
      try {
        is.close();
        socket.close();
      }
      catch(IOException e) {
        e.printStackTrace();
      }
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    return str;
  }

  public static void writeOut(String outStr, final Socket socket) {
    try {
      OutputStream os = socket.getOutputStream();
      os.write(outStr.getBytes());
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }

}
