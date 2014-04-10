package com.gxlu.interfacePlatform.schedule.plugins;

import com.gxlu.interfacePlatform.schedule.ScheduleContext;
import com.gxlu.interfacePlatform.schedule.ScheduleHandler;
import com.gxlu.international.gd.lte.LTEHuaWeiJob;
import com.gxlu.international.jdbc.DBUtils;

/**
 * 广东LTE数据采集接口
 * @author LordOfWar
 *
 */
public class LTEHuaWeiSchedule implements ScheduleHandler {

	@Override
	public void doAction(ScheduleContext context) throws Exception {
		DBUtils.$().executeSP("P_GD_EMS_LTE_DELETEDATA");//清空存过。
		new LTEHuaWeiJob().execute();
//		DBUtils.$().executeSP("");//比对存过。
	}
	
	
	@Override
	public String toString() {
		return "广东LTE数据采集接口";
	}


}
