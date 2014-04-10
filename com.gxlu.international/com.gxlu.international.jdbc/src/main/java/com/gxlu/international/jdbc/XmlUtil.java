package com.gxlu.international.jdbc;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlUtil {

    private static String encoding = "GB2312";

    public static void setEncoding(String _encoding) {
        encoding = _encoding;
    }

    public static String getEncoding() {
        return encoding;
    }

    /**
     * ��ʼ��һ��DocumentBuilderFactory
     * @return a DocumentBuilderFactory
     */
    public static DocumentBuilderFactory newDocumentBuilderFactory() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        return dbf;
    }

    /**
     * ��ʼ��һ��DocumentBuilder
     * @return a DocumentBuilder
     * @throws ParserConfigurationException
     */
    public static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        return newDocumentBuilderFactory().newDocumentBuilder();
    }

    /**
     * ��ʼ��һ����Document���󷵻ء�
     * @throws Exception
     * @return Document
     */
    public static Document newXMLDocument() throws Exception {
        try {
            return newDocumentBuilder().newDocument();
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * �������һ��XML Stringת����һ��org.w3c.dom.Document���󷵻ء�
     * @param xmlString String һ������XML�淶���ַ�����
     * @throws Exception
     * @return Document
     */
    public static Document newXMLDocument(String xmlString) throws Exception {
        if (xmlString == null) {
            throw new IllegalArgumentException();
        }

        try {
            return newDocumentBuilder().parse(new InputSource(new StringReader(xmlString)));
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * ����һ��������������Ϊһ��org.w3c.dom.Document���󷵻ء�
     * @param inputStream InputStream
     * @throws Exception
     * @return Document
     */
    public static Document newXMLDocument(InputStream inputStream) throws Exception {
        if (inputStream == null) {
            throw new IllegalArgumentException("parameter inputStream is invalid");
        }

        try {
            return newDocumentBuilder().parse(inputStream);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * ����һ���ļ�����ȡ���ļ�������Ϊһ��org.w3c.dom.Document���󷵻ء�
     * @param file File
     * @throws Exception
     * @return Document
     */
    public static Document newXMLDocument(File file) throws Exception {
        if (file == null) {
            throw new IllegalArgumentException("parameter file is null.");
        }

        try {
            return newDocumentBuilder().parse(file);
        }
        catch (SAXException e) {
            throw new IllegalArgumentException("DestFileName=" + file.getName() + " SAXException:\n" + e.getMessage());
        }
        catch (IOException e) {
            throw new IllegalArgumentException("DestFileName=" + file.getName() + " IOException:\n" + e.getMessage());
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * ��ȡһ��Transformer����
     * @throws Exception
     * @return Transformer
     */
    private static Transformer newTransformer() throws Exception {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Properties properties = transformer.getOutputProperties();
            properties.setProperty(OutputKeys.ENCODING, encoding);
            properties.setProperty(OutputKeys.METHOD, "xml");
            properties.setProperty(OutputKeys.VERSION, "1.0");
            properties.setProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperties(properties);
            return transformer;
        }
        catch (TransformerConfigurationException tce) {
            throw new RuntimeException(tce.getMessage());
        }
    }

    /**
     * ����һ���ļ�������һ��org.w3c.dom.Document����д����ļ���
     * @param document Document
     * @param fileName String
     * @throws Exception
     */
    public static void saveXMLDocumentAsFile(Document document, String fileName) throws Exception {
        if (document == null) {
            throw new IllegalArgumentException("document is null.");
        }

        if (fileName == null) {
            throw new IllegalArgumentException("fileName is null.");
        }

        try {
            Transformer transformer = newTransformer();
            FileWriter sw = new FileWriter(fileName);
            transformer.transform(new DOMSource(document), new StreamResult(sw));
            sw.close();
        }
        catch (IOException e) {
            throw new IllegalArgumentException("DestfileName=" + fileName + " IOException:\n" + e.getMessage());
        }
        catch (TransformerException te) {
            throw new RuntimeException(te.getMessage());
        }
    }

    /**
     * �������һ��DOM Node����������ַ���
     * @param node Node
     * @throws Exception
     * @return String
     */
    public static String saveXMLDocumentNodeAsString(Node node) throws Exception {
        if (node == null) {
            throw new IllegalArgumentException();
        }

        try {
            Transformer transformer = newTransformer();
            StringWriter sw = new StringWriter();
            transformer.transform(new DOMSource(node), new StreamResult(sw));
            return sw.toString();
        }
        catch (TransformerException te) {
            throw new RuntimeException(te.getMessage());
        }
    }

    /**
     * �������һ��DOM ����������ַ��������ʧ���򷵻�һ�����ַ���""��
     * @param doc Document
     * @throws Exception
     * @return String a XML String from Document
     */
    public static String saveXMLDocumentAsString(Document doc) throws Exception {
        if (doc == null) {
            throw new IllegalArgumentException();
        }
        Node node = doc.getDocumentElement();
        return saveXMLDocumentNodeAsString(node);
    }

    /**
     * ����path��ȡdoc�е�Ԫ�ؽڵ㼯��
     * @param doc Document
     * @param path String ·��: ���÷���/�ָ��tag���ơ��� A/B/C
     * @throws Exception
     * @return List �����Ķ���ΪElement
     */
    public static List getElementsFromXMLDocumentByPath(Document doc, String path) throws Exception {
        if (doc == null || path == null || path.equals("")) {
            throw new IllegalArgumentException("parameter is invalid");
        }

        String currentPath = path;
        String nextPath = "";
        int firstIndex = path.indexOf("/");
        if (firstIndex > 0) {
            currentPath = path.substring(0, firstIndex);
            nextPath = path.substring(firstIndex + 1, path.length());
        }

        List result = new ArrayList();
        Element element = doc.getDocumentElement();
        if (nextPath.equals("") && element.getTagName().equals(currentPath)) {
            result.add(element);
            return result;
        }

        getChildElementsByPath(element, nextPath, result);
        return result;
    }

    /**
     * ����path��ȡparentElement���ӽڵ㼯��
     * @param parentElement Element
     * @param path String ·��: ���÷���/�ָ��tag���ơ��� A/B/C
     * @param result List
     */
    public static void getChildElementsByPath(Element parentElement, String path, List result) {
        if (parentElement == null ||
            path == null || path.equals("") ||
            result == null) {
            return;
        }

        String currentPath = path;
        String nextPath = "";
        int firstIndex = path.indexOf("/");
        if (firstIndex > 0) {
            currentPath = path.substring(0, firstIndex);
            nextPath = path.substring(firstIndex + 1, path.length());
        }

        //��ȡ��ǰtag���ӽڵ�
        NodeList nodeList = parentElement.getElementsByTagName(currentPath);
        if (nodeList == null) {
            return;
        }
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element childElement = (Element) nodeList.item(i);
            //���ڲ�����parentElement��ֱ�Ӷ��ӵ��ӽڵ�����޳����������nextPath��ֵ���д���
            if (!childElement.getParentNode().equals(parentElement)) {
                continue;
            }
            else if (nextPath.equals("")) {
                result.add(childElement);
            }
            else {
                getChildElementsByPath(childElement, nextPath, result);
            }
        }
    }

    /**
     * ��ȡElement�µĵ�һ��TextNode�ڵ��ֵ
     * @param element Element
     * @return String
     */
    public static String getFirstTextNodeFromElement(Element element) {
        if (element == null) {
            return "";
        }

        if (element.getFirstChild() != null &&
            element.getFirstChild().getNodeType() == Node.TEXT_NODE) {
            return element.getFirstChild().getNodeValue();
        }
        return "";
    }

    /**
     * ��XML�������ַ�ת�����ַ�ʵ��
     * 5�������ַ���<��>��&��'��"��
     * ���ж�XML������Ӱ���ֻ��&��<
     * @param oldValue
     * @return
     */
    public static String getXMLChangeString(String oldValue)
    {
    	String value = oldValue;
    	if(oldValue == null)
    		return oldValue;
    	
    	value = value.replaceAll("&","&amp;");  //�����Ѿ�����ת���������ַ����Ὣ�ַ�ʵ���е�&Ҳת��,��ʱ������
    	
    	value =  value.replaceAll("<","&lt;");
    	
    	return value;
    }

    public static void main(String[] args) {

        Document docMsgconf = null;
        try {
            File file = new File("conf/struts-config.xml");
            docMsgconf = XmlUtil.newXMLDocument(file);

            System.out.println(XmlUtil.saveXMLDocumentAsString(docMsgconf));
            List result = XmlUtil.getElementsFromXMLDocumentByPath(docMsgconf, "struts-config/form-beans/form-bean");
            Element element = (Element) result.get(0);
            System.out.println(element.getAttribute("code"));
            System.out.println(XmlUtil.getFirstTextNodeFromElement(element));

            docMsgconf = XmlUtil.newXMLDocument();

            Element e1 = docMsgconf.createElement("socket");
            e1.setAttribute("a", "1");
            e1.setAttribute("b", "2");

            Text text = docMsgconf.createTextNode("test");
            e1.appendChild(text);

            Element e2 = docMsgconf.createElement("socket");
            e2.setAttribute("a", "1");
            e2.setAttribute("b", "2");

            e1.appendChild(e2);
            docMsgconf.appendChild( e1);

            System.out.println(XmlUtil.saveXMLDocumentAsString(docMsgconf));
        }
        catch (Exception ex1) {
            System.out.println(ex1);
        }
    }

}