package com.gxlu.international.common;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVWriter;

public class GxluCSVWriter extends CSVWriter {

  public GxluCSVWriter(Writer writer){
    super(writer);
  }

  @Override
  public void writeAll(ResultSet rs, boolean includeColumnNames) throws SQLException, IOException {
    ResultSetMetaData metadata = rs.getMetaData();
    if(includeColumnNames)
      writeColumnNames(metadata);
    int columnCount = metadata.getColumnCount();
    String nextLine[];
    for(; rs.next(); writeNext(nextLine)) {
      nextLine = new String [columnCount];
      for(int i = 0; i < columnCount; i++)
        nextLine[i] = getColumnValue(rs, metadata.getColumnType(i + 1), i + 1);

    }

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
          SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
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
          SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
          value = timeFormat.format(tstamp);
        }
        break;

      case -1:
      case 1: // '\001'
      case 12: // '\f'
        value = rs.getString(colIndex);
        try{
          if(value != null){
            Pattern p = Pattern.compile("^\\d+[бу]+\\d+\\p{Punct}{1}.+?\\p{Upper}$");
            Matcher m = p.matcher(value);
            if(m.matches()) {
              value = convert2Decimal(value);
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

  private static String convert2Decimal(String value) {
    int degree = value.indexOf("бу");
    int minite = value.indexOf("'");
    Float du = Float.valueOf(value.substring(0, degree));
    Float fen = Float.valueOf(value.substring(degree+1, minite));
    Float second = Float.valueOf(value.substring(minite+1,value.length()-2));
    Float result = du+ fen/60 + second/3600;
    return result.toString();
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

}
