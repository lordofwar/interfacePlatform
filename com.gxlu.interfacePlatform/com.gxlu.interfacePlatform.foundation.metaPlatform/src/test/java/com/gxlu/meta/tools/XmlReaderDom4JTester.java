/**************************************************************************
 * $RCSfile: XmlReaderDom4JTester.java,v $  $Revision: 1.2 $  $Date: 2008/12/18 09:25:22 $
 *
 * $Log: XmlReaderDom4JTester.java,v $
 * Revision 1.2  2008/12/18 09:25:22  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/16 09:00:59  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.tools;

import org.junit.Assert;
import org.junit.Ignore;


/**
 * @author K
 */
@Ignore
public class XmlReaderDom4JTester {

  public XmlReaderDom4JTester(String name) {
  }

  public void testReader() throws Exception {
    XmlReader reader = new XmlReaderDom4J();
    String path = "unit/com/gxlu/meta/tools/test.xml";
    reader.setFilePath(path);
    String namesSql = reader.getValue("dataSource", "queryMetaClassNames");
    Assert.assertEquals("select * from", namesSql);
  }
}

