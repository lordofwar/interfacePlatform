package com.gxlu.interfacePlatform.schedule.schedulelog;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.gxlu.interfacePlatform.database.bean.InterfaceLib;
import com.gxlu.interfacePlatform.database.bean.XDictionary;
import com.gxlu.interfacePlatform.database.common.CommonQueryDAO;
import com.gxlu.interfacePlatform.database.common.CommonQueryDAOImpl;
import com.gxlu.interfacePlatform.database.hibernate.HibernateUtil;
import com.gxlu.interfacePlatform.schedule.Schedule;

public class ScheduleQueryService {
	private CommonQueryDAO commonQueryDAO = new CommonQueryDAOImpl();

	private Log log = LogFactory.getLog(getClass());

	public InterfaceLib findInterfaceLibById(Long id) {
		InterfaceLib obj = new InterfaceLib();
		String hql = "from InterfaceLib t where t.id=" + id;
		try {
			obj = (InterfaceLib) commonQueryDAO.queryObject(hql);
		} catch (Exception e) {
			log.error("findInterfaceLibById() Error! id=" + id, e);
		}
		return obj;
	}

	/*
	 * ��ѯschedule�Ƿ���ڶ�Ӧ�ĸ澯���� type=2
	 * �Զ�����ƽ̨��statu=1����enable,autodisname:�ӿ�����code��status�ӿڵ�״̬
	 */
	public List<Object> QueryAlertRules(Schedule schedule, int status) {
		List<Object> result = new ArrayList<Object>();
		InterfaceLib lib = findInterfaceLibById(schedule.getInterfacelibid());
		XDictionary disName = getDicObjectByDescription("AUTODISCOVERYRULE","AUTODISNAME", lib.getCode());
		if(disName==null)
			return result;
		
		String hql = "from XbAutoDiscoveryRule x where x.xbAlertrule.type=2 and x.xbAlertrule.status=1 and x.autodisname="
				+ disName.getValue() + " and x.autodisstatus =" + status;
		try {
			result = commonQueryDAO.queryObjects(hql);
		} catch (Exception e) {
			log.error("QueryAlertRule() Error! scheduleID=" + schedule.getId(),e);
		}
		return result;
	}

	/*
	 * �����ֵ��classId��attributeName��description��ѯ��Ӧ���ֵ�ֵ
	 */
	public XDictionary getDicObjectByDescription(String CLASSID,String ATTRIBUTEID, String DESCRIPTION) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(XDictionary.class);
		criteria.add(Restrictions.eq("classid", CLASSID));
		criteria.add(Restrictions.eq("attributeid", ATTRIBUTEID));
		criteria.add(Restrictions.eq("description", DESCRIPTION));
		try {
			if(criteria.list().size()==0){
				log.warn(String.format("Cannot find item in dictionary, classid=?,attributeid=?,description=?",CLASSID,ATTRIBUTEID,DESCRIPTION));
				return null;
			}
			return (XDictionary) criteria.list().get(0);
		} finally {
			session.close();
		}
	}
}
