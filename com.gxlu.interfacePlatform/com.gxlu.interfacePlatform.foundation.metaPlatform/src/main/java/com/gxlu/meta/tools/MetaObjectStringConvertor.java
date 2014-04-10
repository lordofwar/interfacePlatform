package com.gxlu.meta.tools;


public class MetaObjectStringConvertor {

  private static final String XML_DQM = "\"";
  private static final String XML_NL = "\n";
  private static final String XML_QM = "\'";
  private static final String TXML_DQM = "###";
  private static final String TXML_NL = "!!!";
  private static final String TXML_QM = "&&&";
  
  public static String xmlToTXml(String xml) {
      String txml = xml;
      if (xml != null) {
          txml = txml.replaceAll(XML_DQM, TXML_DQM);
          txml = txml.replaceAll(XML_NL, TXML_NL);
          txml = txml.replaceAll(XML_QM, TXML_QM);
      }
      return txml;
  }
  
  public static String txmlToXml(String txml) {
      String xml = txml;
      if (txml != null) {
          xml = xml.replaceAll(TXML_DQM, XML_DQM);
          xml = xml.replaceAll(TXML_NL, XML_NL);
          xml = xml.replaceAll(TXML_QM, XML_QM);
      }
      return xml;
  }
}
