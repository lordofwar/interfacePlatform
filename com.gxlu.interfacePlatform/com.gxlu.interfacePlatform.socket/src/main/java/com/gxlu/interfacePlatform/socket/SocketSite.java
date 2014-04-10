package com.gxlu.interfacePlatform.socket;

public class SocketSite {
	private String systemCode;// ϵͳ����
	private String ipAddr;// IP��ַ
	private int port;// �˿�
	private int timeout;// ��ʱʱ�� millisecond
	private int maxthread;// ����߳���
	private String userName;
	private String passWord;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getMaxthread() {
		return maxthread;
	}

	public void setMaxthread(int maxthread) {
		this.maxthread = maxthread;
	}

}
