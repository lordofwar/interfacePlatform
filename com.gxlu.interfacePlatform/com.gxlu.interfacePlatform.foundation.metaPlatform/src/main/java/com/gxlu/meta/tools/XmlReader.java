/**************************************************************************
 * $RCSfile: XmlReader.java,v $  $Revision: 1.2 $  $Date: 2008/12/18 09:24:34 $
 *
 * $Log: XmlReader.java,v $
 * Revision 1.2  2008/12/18 09:24:34  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/16 09:00:49  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.tools;

/**
 * @author K
 */
public interface XmlReader {

  public void setFilePath(String path);

  public String getValue(String beanName, String propertiesName) throws XmlReaderException;
}

