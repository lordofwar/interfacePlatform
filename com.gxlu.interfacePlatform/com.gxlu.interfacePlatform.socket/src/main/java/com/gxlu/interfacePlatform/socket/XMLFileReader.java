package com.gxlu.interfacePlatform.socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XMLFileReader {
  public List getSelectElements(String path)
  {
      return document.selectNodes(path);
  }

  public List getSelectElements(Element element, String path)
  {
      return element.selectNodes(path);
  }

  public XMLFileReader(String fileName)
  {
      document = null;
      ClassLoader cl = getClass().getClassLoader();
      InputStream input = cl.getResourceAsStream(fileName);
      try
      {
    	  if(input == null){
    		  input = doGetURL(fileName);
    	  }
    	  SAXReader reader = new SAXReader();
          document = reader.read(input);
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
  }

  public String getPorpertyText(String porpertyPath)
  {
      Node node = document.selectSingleNode(porpertyPath);
      return node.getText();
  }

  public String getPorpertyTexts(String porpertyPath, String nodeName, String divisionChar)
  {
      StringBuffer cp = new StringBuffer("");
      List list = document.selectNodes((new StringBuilder(String.valueOf(porpertyPath))).append("/").append(nodeName).toString());
      Node node;
      for(Iterator it = list.iterator(); it.hasNext(); cp.append((new StringBuilder(String.valueOf(node.getText()))).append(divisionChar).toString()))
          node = (Node)it.next();

      if(cp.length() > 1)
          cp.deleteCharAt(cp.length() - 1);
      return cp.toString();
  }

  public Vector getPorpertyTexts(String porpertyPath, String nodeName)
  {
      Vector vReturn = new Vector();
      List list = document.selectNodes((new StringBuilder(String.valueOf(porpertyPath))).append("/").append(nodeName).toString());
      Node node;
      for(Iterator it = list.iterator(); it.hasNext(); node.getText())
      {
          node = (Node)it.next();
          node.getName();
      }

      return vReturn;
  }
  
  public static InputStream doGetURL(String realpath) throws FileNotFoundException, IOException {
	  URL url = null;  
	    InputStream is  = null;
	    File file  = new File(realpath);
	    if(!file.exists()){
	        url = XMLFileReader.class.getResource(realpath);
	        if (url == null) {
	          ClassLoader cl = XMLFileReader.class.getClassLoader();
	          while (cl != null) {
	            url = cl.getResource(realpath);
	            if (url != null) {
	              break;
	            }
	            cl = cl.getParent();
	          }

	          if (url == null) {
	            url = ClassLoader.getSystemResource(realpath);
	          }
	        }

	        if (url == null) {
	          throw new FileNotFoundException(realpath);
	        }
	        is = url.openStream();
	    }else{
	    	is = new FileInputStream(file);
	    }
	      return is;
  }

  private Document document;
}
