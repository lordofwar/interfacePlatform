package com.gxlu.international.all;

import java.io.IOException;
import java.util.List;

import org.dom4j.Element;

import com.gxlu.interfacePlatform.foundation.ftp.component.FTPException;
import com.gxlu.interfacePlatform.foundation.ftp.component.FTPServer;
import com.gxlu.interfacePlatform.foundation.sftp.SFTPServer;
import com.gxlu.interfacePlatform.schedule.IMSScheduleManager;
import com.gxlu.interfacePlatform.schedule.plugins.LLPM4HuaWeiSchedule;
import com.gxlu.interfacePlatform.schedule.plugins.LTEHuaWeiSchedule;
import com.gxlu.interfacePlatform.schedule.plugins.MotsSchedule;
import com.gxlu.interfacePlatform.server.webservice.Server;
import com.gxlu.interfacePlatform.server.webservice.ServerConfig;
import com.gxlu.interfacePlatform.server.webservice.ServerConfig.ServerConfigEntry;
import com.gxlu.interfacePlatform.socket.SocketConfiguration;
import com.gxlu.interfacePlatform.socket.SocketSite;
import com.gxlu.interfacePlatform.socket.XMLFileReader;
import com.gxlu.interfacePlatform.webservice.plugins.ScheduleCode2HandleClass;
import com.gxlu.international.common.utils.DBUtils;
import com.gxlu.international.llpm.LLPM4HuaWei;
import com.gxlu.international.netcool.NetCoolJob;
import com.gxlu.international.vas.Vas4HPSQM;
import com.jcraft.jsch.SftpException;

public class TEST {
	public static void main(String[] args) {
		try {
//			 runScheduleimmediately(args);

//			 LLPMTest();
			LTEHuaWeiSchedule lte = new LTEHuaWeiSchedule();
			lte.doAction(null);

			// NetCoolJob job = new NetCoolJob();
			// job.execute();

			// AirComJob airjob =new AirComJob();
			// airjob.execute();

			// Double dou = 23454545234D;
			// BigDecimal big = new BigDecimal(dou);
			// big = big.setScale(2, BigDecimal.ROUND_DOWN);
			// System.out.println(big.toString());
			//
			// Vas4HPSQM vas = new Vas4HPSQM();
			// vas.execute();
			//

			// LLPM4HuaWeiSchedule schedule = new LLPM4HuaWeiSchedule();
			// schedule.doAction(null);
			//

			// int threadCount =
			// Thread.currentThread().getThreadGroup().activeCount();
			// System.out.println(threadCount);
			//

//			MotsSchedule mots = new MotsSchedule(); 
//			mots.doAction(null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void LLPMTest() {
//		DBUtils.$().executeSP("p_Drm_Ems_Tn_Deletedata");// delete all data
															// before
		LLPM4HuaWei LLPM4HuaWei = new LLPM4HuaWei();
		SFTPServer server;
		try {
			server = LLPM4HuaWei.getFtpServer();
			List<String> files = server.dir();
			server.quit();

			for (String filename : files) {
				System.out.println(filename);
//				new LLPM4HuaWei().execute(filename);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FTPException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
	}

	public static void runScheduleimmediately(String[] args) throws Exception {
		ServerConfigEntry entry = ServerConfig.getServerConfig().loadConfig();
		String contextPath = entry.getWsContextPath();
//		String rmiAddress = entry.getRmiAddress();
		int rmiPort = entry.getRmiPort();
		String type = entry.getType();
		String description = entry.getDescription();

		// if (args != null && args.length > 0) {
		// contextPath = args[0];
		// }

		Server server = new Server("id","main",rmiPort, contextPath);

		server.startIMSScheduleManager();
		server.publish();

//		IMSScheduleManager scheduleManager = IMSScheduleManager.getIMSScheduleManager();
//		ScheduleCode2HandleClass scheduleCode2HandleClass = new ScheduleCode2HandleClass();
//		String handlerClassName = scheduleCode2HandleClass.getHandlerClassName("DRM_TN_LLPM");
//		scheduleManager.triger(handlerClassName);
	}

}
