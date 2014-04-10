package com.gxlu.interfacePlatform.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

public class SocketConfiguration {

	public static Map<String, SocketSite> socketSites = new HashMap<String, SocketSite>();

	static {
		XMLFileReader xmlFileReader = new XMLFileReader("./conf/SocketConfig.xml");
		List<Element> clients = xmlFileReader.getSelectElements("/config/client");
		for (Element element : clients) {
			SocketSite site = new SocketSite();
			element.toString();
			site.setIpAddr(element.selectSingleNode("ipAddr").getText());
			site.setPort(Integer.valueOf(element.selectSingleNode("port").getText()));
			site.setTimeout(Integer.valueOf(element.selectSingleNode("timeout").getText()));
			site.setUserName(element.selectSingleNode("userName").getText());
			site.setPassWord(element.selectSingleNode("passWord").getText());
			socketSites.put(element.selectSingleNode("systemCode").getText(), site);
		}
	}

	public static Socket getSocketBySysCode(String sysCode) {
		Socket socket = null;
		GxluClientSocketFactory clientFactory = new GxluClientSocketFactory();
		SocketSite socketSite = socketSites.get(sysCode);
		if (socketSite == null) {
			System.out.println("There is SocketConfig infomation for systemCode:" + sysCode);
			return socket;
		}
		try {
			socket = clientFactory.createSocket(socketSite.getIpAddr(), socketSite.getPort());
			if (socketSite.getTimeout() != 0) {
				socket.setSoTimeout(socketSite.getTimeout());
			}
		} catch (SocketTimeoutException e) {
			System.out.println("socket timeOut!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}
}
