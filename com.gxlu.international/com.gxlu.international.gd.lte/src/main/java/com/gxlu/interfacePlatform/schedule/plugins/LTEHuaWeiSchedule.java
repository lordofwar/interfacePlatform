package com.gxlu.interfacePlatform.schedule.plugins;

import com.gxlu.interfacePlatform.schedule.ScheduleContext;
import com.gxlu.interfacePlatform.schedule.ScheduleHandler;
import com.gxlu.international.gd.lte.LTEHuaWeiJob;
import com.gxlu.international.jdbc.DBUtils;

/**
 * �㶫LTE���ݲɼ��ӿ�
 * @author LordOfWar
 *
 */
public class LTEHuaWeiSchedule implements ScheduleHandler {

	@Override
	public void doAction(ScheduleContext context) throws Exception {
		DBUtils.$().executeSP("P_GD_EMS_LTE_DELETEDATA");//��մ����
		new LTEHuaWeiJob().execute();
//		DBUtils.$().executeSP("");//�ȶԴ����
	}
	
	
	@Override
	public String toString() {
		return "�㶫LTE���ݲɼ��ӿ�";
	}


}
