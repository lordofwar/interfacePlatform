package com.gxlu.international.common.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.international.common.obj.XB_INTPT_LOG;
import com.gxlu.international.common.service.InterfaceLogService;
import com.gxlu.international.common.utils.DBUtils;

public class InterfaceLogServiceImpl implements InterfaceLogService {
	private DBUtils dbUtil = DBUtils.$();
	private static Log logger = LogFactory.getLog(InterfaceLogServiceImpl.class);

	@Override
	public void insertLog(String metacategory, String iNTERFACENAME, String cOMMENTS, String lOGLEVEL, String mESSAGE, String tARGETCODE, String tARGETSYS,
			int sERIALNO) {

		String threadId = Long.toString(Thread.currentThread().getId());
		XB_INTPT_LOG log = new XB_INTPT_LOG(metacategory, threadId, iNTERFACENAME, cOMMENTS, lOGLEVEL, mESSAGE, tARGETCODE, tARGETSYS, sERIALNO, null);
		logger.debug(log.getInsertSql());
		dbUtil.updateObject(log.getInsertSql());
	}

}
