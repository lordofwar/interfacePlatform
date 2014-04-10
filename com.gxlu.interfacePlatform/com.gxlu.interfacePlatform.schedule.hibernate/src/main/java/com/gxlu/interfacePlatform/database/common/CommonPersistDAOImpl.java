package com.gxlu.interfacePlatform.database.common;

import java.util.Vector;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gxlu.interfacePlatform.database.hibernate.HibernateUtil;

public class CommonPersistDAOImpl implements CommonPersistDAO {

  public Object createObject(Object _obj) {
    Session session =HibernateUtil.getSessionFactory().openSession();
    try{
      Transaction trasaction =session.beginTransaction();
      session.save(_obj);
      trasaction.commit();
    }finally{
      session.close();
    }
      return _obj;
  }

  public Object updateObject(Object _obj) {
    Session session =HibernateUtil.getSessionFactory().openSession();
    try{
      Transaction trasaction =session.beginTransaction();
      session.saveOrUpdate(_obj);
      trasaction.commit();
    }finally{
      session.close();
    }
      return _obj;
  }

  public Object mergeObject(Object _obj) {
    Session session =HibernateUtil.getSessionFactory().openSession();
    try{
      Transaction trasaction =session.beginTransaction();
      session.merge(_obj);
      trasaction.commit();
    }finally{
      session.close();
    }
      return _obj;
  }

  public void deleteObject(Object _obj) {
    Session session =HibernateUtil.getSessionFactory().openSession();
    try{
      Transaction trasaction =session.beginTransaction();
      session.delete(_obj);
      trasaction.commit();
    }finally{
      session.close();
    }
  }

  public Vector<Object> createAll(Vector<Object> vector) {
    return null;
  }

  public Vector<Object> updateAll(Vector<Object> vector) {
    // TODO Auto-generated method stub
    return null;
  }

  public void deleteAll(Vector<Object> vector) {
    // TODO Auto-generated method stub

  }

}
