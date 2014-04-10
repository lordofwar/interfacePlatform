/**************************************************************************
 * $RCSfile: XmlReaderDom4J.java,v $  $Revision: 1.3 $  $Date: 2008/12/19 09:40:07 $
 *
 * $Log: XmlReaderDom4J.java,v $
 * Revision 1.3  2008/12/19 09:40:07  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/18 09:24:34  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/16 09:00:49  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.tools;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author K
 */
public class XmlReaderDom4J implements XmlReader {

  public XmlReaderDom4J(String path) {
    this.path = path;
  }

  public XmlReaderDom4J() {
    
  }

  public String getValue(String beanName, String propertiesName) throws XmlReaderException {
    if(map == null) {
      try {
        root = parse().getRootElement();
        map = new HashMap<String, Map<String, String>>();
        loadBeans();
      }
      catch(DocumentException exception) {
        throw new XmlReaderException(exception);
      }
    }

    return map.get(beanName).get(propertiesName);
  }

  private void loadBeans() {
    for(Iterator i = root.elementIterator("bean"); i.hasNext();) {
      Element bean = (Element)i.next();
      Attribute beanNameAttr = bean.attribute("id");
      Map<String, String> properties = new HashMap<String, String>();
      loadProperties(bean, properties);
      map.put(beanNameAttr.getValue(), properties);
    }
  }

  private void loadProperties(Element bean, Map<String, String> properties) {
    for (Iterator x = bean.elementIterator("property"); x.hasNext();) {
      Element property = (Element)x.next();
      Attribute attribute = property.attribute("name");
      properties.put(attribute.getValue(), property.getTextTrim());
    }
  }

  public void setFilePath(String path) {
    this.path = path;
  }

  private Document parse() throws DocumentException {
    File file = new File(path);
    SAXReader reader = new SAXReader();
    Document document = reader.read(file);

    return document;
  }

  private Element root;

  private String path;

  private Map<String, Map<String, String>> map;
}
