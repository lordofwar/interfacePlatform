package com.gxlu.internatoinal.misc;

import java.io.InputStream;


public interface Parser<T> {

	public T parse(InputStream remoteFtpFile);
	
	public T parse(InputStream remoteFtpFile,BatchHandler handler);
	
}
