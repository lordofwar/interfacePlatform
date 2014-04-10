package com.gxlu.international.gd.lte;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.interfacePlatform.foundation.ftp.component.FTPContext;
import com.gxlu.interfacePlatform.foundation.ftp.component.FTPException;
import com.gxlu.interfacePlatform.foundation.ftp.component.FTPServer;
import com.gxlu.international.common.utils.CommonTool;
import com.gxlu.internatoinal.misc.Table;

public class LTEHuaWeiJob {
	private static Log log = LogFactory.getLog(LTEHuaWeiJob.class);

	public void execute() throws Exception {
		FTPServer ftp = getFTPServer();
		String[] files = ftp.dir();
		ftp.quit();
		String dateStr = CommonTool.FormatDate(new Date());
//		dateStr="20140326"; //测试文件数据
		
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i];
			if (files[i] == null || !files[i].contains(dateStr)) {
				continue;
			}
			FTPServer ftpServer = getFTPServer();
			byte[] bytes = ftpServer.get(fileName);
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			ftpServer.quit();		// 文件加载内存后关闭连接，避免ftp超时
			log.info("解析数据文件：" + fileName);
			LTEHuaWeiCSVParser parser = new LTEHuaWeiCSVParser();
			Map<String, Table> tablesMap = parser.parse(in);
			LTEHuaWeiDao dao = new LTEHuaWeiDao();
			Iterator<String> it = tablesMap.keySet().iterator();
			while (it.hasNext()) {
				dao.saveData(tablesMap.get(it.next()));
			}
		}
		log.info("Complete all dataCollect.");
	}
	
	
	
	
	

	public Thread startNewThread(final String fileName) {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					FTPServer ftpServer = getFTPServer();
					byte[] bytes = ftpServer.get(fileName);
					ByteArrayInputStream in = new ByteArrayInputStream(bytes);
					ftpServer.quit();		// 文件加载内存后关闭连接，避免ftp超时
					log.info("解析数据文件：" + fileName);
					LTEHuaWeiCSVParser parser = new LTEHuaWeiCSVParser();
					Map<String, Table> tablesMap = parser.parse(in);
					LTEHuaWeiDao dao = new LTEHuaWeiDao();
					Iterator<String> it = tablesMap.keySet().iterator();
					while (it.hasNext()) {
						dao.saveData(tablesMap.get(it.next()));
					}
				} catch (IOException e) {
					log.error("IO异常：", e);
				} catch (FTPException e) {
					log.error("FTP异常：", e);
				}
			}
		};
		return t;
	}

	private FTPServer getFTPServer() throws IOException, FTPException {
		return FTPContext.getInstance().getFtpClient("SITE_FTP_GD_LTE");
	}

}
