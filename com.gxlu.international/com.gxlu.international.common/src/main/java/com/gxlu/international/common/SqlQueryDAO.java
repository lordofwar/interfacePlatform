package com.gxlu.international.common;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import au.com.bytecode.opencsv.CSVWriter;

import com.gxlu.international.common.utils.DBUtils;
import com.gxlu.international.jdbc.ConnectionManager;
import com.gxlu.international.jdbc.transaction.DefaultConnectionManager;

public class SqlQueryDAO {
  private static Log logger = LogFactory.getLog(SqlQueryDAO.class);

  public static void query4CSVWriter(String sql, CSVWriter writer) throws IOException {
    PreparedStatement prest = null;
    ResultSet rs = null;
    ConnectionManager manager = new DefaultConnectionManager();
    Connection connection = manager.openSession().getConnection();
    try {
      prest = connection.prepareStatement(sql);
      rs = prest.executeQuery();
      if(rs != null) {
        writer.writeAll(rs, true);
      }
    }
    catch(SQLException e) {
      logger.error("脚本异常:", e);
    }
    catch(IOException e) {
      logger.error("IO异常:", e);
    }
    finally {
      writer.close();
      DBUtils.$().closeQuietly(connection);
      DBUtils.$().closeQuietly(prest);
      DBUtils.$().closeQuietly(rs);
    }
  }

  public static void query4XMLWriter(String sql, Document document, String MOClassName) {
    PreparedStatement prest = null;
    ResultSet rs = null;
    ConnectionManager manager = new DefaultConnectionManager();
    Connection connection = manager.openSession().getConnection();
    try {
      prest = connection.prepareStatement(sql);
      rs = prest.executeQuery();
      if(rs != null) {
        writeInElement(rs, document, MOClassName);
      }
    }
    catch(SQLException e) {
      logger.error("脚本异常:", e);
    }
    catch(IOException e) {
      logger.error("IO异常:", e);
    }
    finally {
      com.gxlu.international.common.utils.DBUtils.$().closeQuietly(connection);
      DBUtils.$().closeQuietly(prest);
      DBUtils.$().closeQuietly(rs);
    }
  }

  private static void writeInElement(ResultSet rs, Document document, String MOClassName) throws SQLException, IOException {
    ResultSetMetaData metadata = rs.getMetaData();
    String columnNames[] = null;
    columnNames = getColumnNames(metadata);
    int columnCount = metadata.getColumnCount();
    Element MOTree = document.addElement("MOTree");
    Element MO = MOTree.addElement("MO");
    MO.addAttribute("className", MOClassName);
    boolean isFirst = true;
    while(rs.next()) {
      if(isFirst){
        isFirst=false;
        for(int i = 1; i < columnCount; i++) {//first column is rowNo,so i start from 1
          Element attr = MO.addElement("attr");
          attr.addAttribute("name", columnNames[i]);
          attr.setText(getColumnValue(rs, metadata.getColumnType(i + 1), i + 1));
        }
      }else{
        Element innerMo = MO.addElement("MO");
        innerMo.addAttribute("className", MOClassName);
        for(int i = 1; i < columnCount; i++) {//first column is rowNo,so i start from 1
          Element attr = innerMo.addElement("attr");
          attr.addAttribute("name", columnNames[i]);
          attr.setText(getColumnValue(rs, metadata.getColumnType(i + 1), i + 1));
        }
      }
    }

  }

  private static String [] getColumnNames(ResultSetMetaData metadata) throws SQLException {
    int count = metadata.getColumnCount();
    String [] columnNames = new String [count];
    for(int i = 0; i < count; i++)
      columnNames[i] = metadata.getColumnName(i + 1);
    return columnNames;
  }

  private static String getColumnValue(ResultSet rs, int colType, int colIndex) throws SQLException, IOException {
    String value = "";
    switch(colType) {
      case -7:
        Object bit = rs.getObject(colIndex);
        if(bit != null)
          value = String.valueOf(bit);
        break;

      case 16: // '\020'
        boolean b = rs.getBoolean(colIndex);
        if(!rs.wasNull())
          value = Boolean.valueOf(b).toString();
        break;

      case 2005:
        Clob c = rs.getClob(colIndex);
        if(c != null)
          value = read(c);
        break;

      case -5:
        long lv = rs.getLong(colIndex);
        if(!rs.wasNull())
          value = Long.toString(lv);
        break;

      case 2: // '\002'
      case 3: // '\003'
      case 6: // '\006'
      case 7: // '\007'
      case 8: // '\b'
        BigDecimal bd = rs.getBigDecimal(colIndex);
        if(bd != null)
          value = bd.toString();
        break;

      case -6:
      case 4: // '\004'
      case 5: // '\005'
        int intValue = rs.getInt(colIndex);
        if(!rs.wasNull())
          value = Integer.toString(intValue);
        break;

      case 2000:
        Object obj = rs.getObject(colIndex);
        if(obj != null)
          value = String.valueOf(obj);
        break;

      case 91: // '['
        java.sql.Date date = rs.getDate(colIndex);
        if(date != null) {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          value = dateFormat.format(date);
        }
        break;

      case 92: // '\\'
        Time t = rs.getTime(colIndex);
        if(t != null)
          value = t.toString();
        break;

      case 93: // ']'
        java.sql.Timestamp tstamp = rs.getTimestamp(colIndex);
        if(tstamp != null) {
          SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          value = timeFormat.format(tstamp);
        }
        break;

      case -1:
      case 1: // '\001'
      case 12: // '\f'
        value = rs.getString(colIndex);
        try{
          if(value != null){
            Pattern p = Pattern.compile("^\\d+[°]+\\d+\\p{Punct}{1}.+?\\p{Upper}$");
            Matcher m = p.matcher(value);
            if(m.matches()) {
              value = convertFormart(value);
            }
          }
        }catch(Exception e){
          System.out.println("Format ERROR:"+e.getMessage());
        }
        break;

      default:
        value = "";
        break;
    }
    if(value == null)
      value = "";
    return value;
  }

  private static String convertFormart(String value) {
    value = value.replace("°", ":");
    value = value.replace("'", ":");
    value = value.replace("\"", ":");
    return value;
  }

  private static String read(Clob c) throws SQLException, IOException {
    StringBuilder sb = new StringBuilder((int)c.length());
    Reader r = c.getCharacterStream();
    char cbuf[] = new char [2048];
    int n = 0;
    do {
      if((n = r.read(cbuf, 0, cbuf.length)) == -1)
        break;
      if(n > 0)
        sb.append(cbuf, 0, n);
    }
    while(true);
    return sb.toString();
  }
  
  
  public static Object queryObject(String sql) {
    Object obj =null;
    PreparedStatement prest = null;
    ResultSet rs = null;
    ConnectionManager manager = new DefaultConnectionManager();
    Connection connection = manager.openSession().getConnection();
    try {
      prest = connection.prepareStatement(sql);
      rs = prest.executeQuery();
      if(rs.next()) {
        obj = rs.getObject(1);
        ResultSetMetaData metadata = rs.getMetaData();
        int type =metadata.getColumnType(1);
        System.out.println("ColumeType:"+type);
      }
    }
    catch(SQLException e) {
      logger.error("脚本异常:", e);
    }
    finally {
      DBUtils.$().closeQuietly(connection);
      DBUtils.$().closeQuietly(prest);
      DBUtils.$().closeQuietly(rs);
    }
    
    return obj;
  }

}
