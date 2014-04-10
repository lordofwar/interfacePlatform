package com.gxlu.interfacePlatform.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.interfacePlatform.database.bean.InterfaceLib;
import com.gxlu.interfacePlatform.database.common.CommonQueryDAO;
import com.gxlu.interfacePlatform.database.common.CommonQueryDAOImpl;

public class DetailLogStorage {
	private static Log log = LogFactory.getLog(DetailLogStorage.class);
	private static Map<String, StringBuffer> storageMap;

	private DetailLogStorage() {
		CommonQueryDAO queryDao = new CommonQueryDAOImpl();
		try {
			List<Object> libList = queryDao.queryAll(InterfaceLib.class);
			storageMap = new HashMap<String, StringBuffer>();
			for (Object obj : libList) {
				InterfaceLib interfacelib = (InterfaceLib) obj;
				storageMap.put(interfacelib.getCode(), new StringBuffer());
			}
		} catch (Exception e) {
			log.error("initial storage error, detailLog will save fail,please check:", e);
		}
	}

	private static class LazyHolder {
		private static final DetailLogStorage INSTANCE = new DetailLogStorage();
	}

	public static DetailLogStorage getInstance() {
		return LazyHolder.INSTANCE;
	}

	public void append(String libCode, String info) {
		synchronized (this) {
			if (libCode != null && !libCode.equals("")) {
				if (storageMap != null) {
					if (storageMap.get(libCode) != null) {
						StringBuffer sb = storageMap.get(libCode);
						sb.append(info);
					} else {
						log.error("There is no storage space for InterfaceLibCode:" + libCode);
					}
				}
			}
		}
	}

	public void flush(String libCode) {
		if (libCode != null && !libCode.equals("")) {
			if (storageMap != null) {
				storageMap.put(libCode, new StringBuffer());
			}
		}
	}

	public String getDetailLog(String libCode) {
		String logInfo = null;
		if (libCode != null && !libCode.equals("")) {
			if (storageMap != null) {
				if (storageMap.get(libCode) != null) {
					StringBuffer sb = storageMap.get(libCode);
					logInfo = sb.toString();
				} else {
					log.error("There is no storage space for InterfaceLibCode:" + libCode);
				}
			}
		}
		return logInfo;
	}

}
