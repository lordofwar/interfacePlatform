/**************************************************************************
 * $RCSfile: JdbcHelper.java,v $  $Revision: 1.1 $  $Date: 2008/12/16 07:35:03 $
 *
 * $Log: JdbcHelper.java,v $
 * Revision 1.1  2008/12/16 07:35:03  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Ignore;

import com.gxlu.meta.i18n.I18N;
/**
 * @author K
 */
@Ignore
public class JdbcHelper {

  public static String execute(String driverClass, String url, String username, String password, String sql) {
    Connection con = null;
    java.sql.PreparedStatement pstmt = null;
    String message = null;
    try {
      Class.forName(driverClass);
      con = DriverManager.getConnection(url, username, password);
      // ����״̬   
      pstmt = con.prepareStatement(sql);
      // ִ��SQL��䣬���ؽ����   
      ResultSet rs = pstmt.executeQuery(); 
      // �Խ�������д���   
      message = I18N.getString ("ExecuteSuccess");
    }
    catch(Exception e) {
      message = I18N.getString ("ExecitionFailString") + e.getMessage() + ")";
      e.printStackTrace();
      return message;
    }
    finally {
      try {
        pstmt.close();
        con.close();
      }
      catch(Throwable e) {
        message = I18N.getString ("ExecitionFailString") + e.getMessage() + ")";
        e.printStackTrace();
        return message;
      }
    }
    
    return message;
  }
}

