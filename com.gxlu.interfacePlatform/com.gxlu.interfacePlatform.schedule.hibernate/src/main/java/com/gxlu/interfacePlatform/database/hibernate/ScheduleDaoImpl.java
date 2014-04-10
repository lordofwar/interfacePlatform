package com.gxlu.interfacePlatform.database.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.gxlu.interfacePlatform.schedule.Schedule;
/**
 * DataBase transaction implement.
 * @author pudding
 *
 */
public class ScheduleDaoImpl implements ScheduleDao {

	public void insert(Schedule schedule) {
		// TODO Auto-generated method stub
		Session session =HibernateUtil.getSessionFactory().openSession();
		try{
			Transaction trasaction =session.beginTransaction();
			session.save(schedule);
			trasaction.commit();
		}finally{
			session.close();
		}
	}

	public List<Schedule> queryAll() {
		// TODO Auto-generated method stub
		Session session =HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Schedule.class);
		try{
			return criteria.list();
		}finally{
			session.close();
		}
	}

	public List<Schedule> queryByCondition(Schedule condition) {
		// TODO Auto-generated method stub
		
		Session session =HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Schedule.class);
		if(condition.getId()!=null)
			criteria.add(Restrictions.eq("id", condition.getId()));
		try{
			return criteria.list();
		}finally{
			session.close();
		}
		
	}

	public void delete(Schedule schedule) {
		// TODO Auto-generated method stub
		Session session =HibernateUtil.getSessionFactory().openSession();
		try{
			Transaction trasaction =session.beginTransaction();
			session.delete(schedule);
			trasaction.commit();
		}finally{
			session.close();
		}
	}

	public void modify(Schedule schedule) {
		// TODO Auto-generated method stub
		Session session =HibernateUtil.getSessionFactory().openSession();
		try{
			Transaction trasaction =session.beginTransaction();
			session.update(schedule);
			trasaction.commit();
		}finally{
			session.close();
		}
	}

	public Schedule get(Integer id) {
		// TODO Auto-generated method stub
		Session session =HibernateUtil.getSessionFactory().openSession();
		try{
			return (Schedule)session.get(Schedule.class, id);
		}finally{
			session.close();
		}
	}

}
