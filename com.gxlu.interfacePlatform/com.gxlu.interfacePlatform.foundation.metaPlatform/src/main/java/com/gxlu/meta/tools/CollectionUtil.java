/**************************************************************************
 * $RCSfile: CollectionUtil.java,v $  $Revision: 1.5 $  $Date: 2009/04/13 06:39:19 $
 *
 * $Log: CollectionUtil.java,v $
 * Revision 1.5  2009/04/13 06:39:19  richie
 * *** empty log message ***
 *
 * Revision 1.4  2009/04/13 05:11:46  richie
 * *** empty log message ***
 *
 * Revision 1.3  2009/03/05 09:53:31  richie
 * *** empty log message ***
 *
 * Revision 1.2  2009/02/27 02:02:00  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/31 04:44:42  richie
 * *** empty log message ***
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : CollectionUtil.java
 * Created on : Dec 30, 2008 2:50:14 PM
 * Creator : RichieJin
 */
package com.gxlu.meta.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author K
 */
public class CollectionUtil {

  public static boolean notContainInList(List<String> list, String value) {
    if (list == null) return false;
    return list.contains(value);
  }

  public static boolean notContainInList(String[] list, String value) {
    if (list == null) return false;

    for (String string : list) {
      if (string != null && value != null && string.equals(value)) {
        return true;
      }
    }

    return false;
  }

  public static Object[] mergeArray(Object[] arrayA, Object[] arrayB) {
    //Object[] result = new Object[arrayA.length + arrayB.length];
    List arrayAList = new ArrayList();
    if (arrayA != null) {
      for (int i = 0; i < arrayA.length; ++i) {
        arrayAList.add(arrayA[i]);
      }
    }
    if (arrayB != null) {
      for (int i = 0; i < arrayB.length; ++i) {
        arrayAList.add(arrayB[i]);
      }
    }
    
    return arrayAList.toArray();
  }
}

