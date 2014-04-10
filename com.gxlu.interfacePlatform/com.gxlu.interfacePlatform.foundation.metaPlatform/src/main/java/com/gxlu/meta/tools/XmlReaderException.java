/**************************************************************************
 * $RCSfile: XmlReaderException.java,v $  $Revision: 1.1 $  $Date: 2008/12/16 09:00:48 $
 *
 * $Log: XmlReaderException.java,v $
 * Revision 1.1  2008/12/16 09:00:48  richie
 * *** empty log message ***
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : XmlReaderException.java
 * Created on : Dec 16, 2008 4:34:18 PM
 * Creator : RichieJin
 */
package com.gxlu.meta.tools;

/**
 * <pre>
 * Description : TODO
 * @author RichieJin
 * </pre>
 */
public class XmlReaderException extends Exception {

  public XmlReaderException(Exception exception) {
    super(exception);
  }
}

