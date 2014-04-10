/**************************************************************************
 * $RCSfile: ObjectConvetor.java,v $  $Revision: 1.1 $  $Date: 2009/01/06 02:09:00 $
 *
 * $Log: ObjectConvetor.java,v $
 * Revision 1.1  2009/01/06 02:09:00  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.tools;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author K
 */
public class ObjectConvetor {

  public static Long toLong(Object object) {
    if (object != null) {
      return ((BigDecimal) object).longValue();
    }
    else {
      return 0l;
    }
  }

  public static String toString(Object object) {
    if (object != null) {
      return object.toString();
    }
    else {
      return null;
    }
  }

  public static Integer toInt(Object object) {
    if (object != null) {
      return ((BigDecimal) object).intValue();
    }
    else {
      return 0;
    }
  }

  public static Date toDate(Object object) {
    if (object != null) {
      return (Date) object;
    }
    else {
      return null;
    }
  }

  public static Byte toByte(Object object) {
    if (object != null) {
      return ((BigDecimal) object).byteValue();
    }
    else {
      return (byte) 0;
    }
  }

  public static Double toDouble(Object object) {
    if (object != null) {
      return ((BigDecimal) object).doubleValue();
    }
    else {
      return (double) 0;
    }
  }
  
  public static Float toFloat(Object object) {
    if (object != null) {
      return ((BigDecimal) object).floatValue();
    }
    else {
      return (float) 0;
    }
  }
}

