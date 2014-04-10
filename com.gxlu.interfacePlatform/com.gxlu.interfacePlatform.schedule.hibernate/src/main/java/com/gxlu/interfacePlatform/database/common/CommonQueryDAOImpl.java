package com.gxlu.interfacePlatform.database.common;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.gxlu.interfacePlatform.database.hibernate.HibernateUtil;

public class CommonQueryDAOImpl implements CommonQueryDAO {

  @SuppressWarnings("unchecked")
  public Object queryObject(String hql) throws Exception {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery(hql);
    try {
      List<Object> list = query.list();
      if(list != null && list.size() > 0)
        return list.get(0);
      else
        return null;
    }
    finally {
      session.close();
    }
  }

  public List<Object> queryObjects(String hql) throws Exception {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery(hql);
    try {
      List<Object> list = query.list();
      if(list != null && list.size() > 0)
        return list;
      else
        return null;
    }
    finally {
      session.close();
    }
  }

  public List<Object> queryAll(Class<?> className) throws Exception {
    Session session =HibernateUtil.getSessionFactory().openSession();
    Criteria criteria = session.createCriteria(className);
    try{
      return criteria.list();
    }finally{
      session.close();
    }
  }

}
