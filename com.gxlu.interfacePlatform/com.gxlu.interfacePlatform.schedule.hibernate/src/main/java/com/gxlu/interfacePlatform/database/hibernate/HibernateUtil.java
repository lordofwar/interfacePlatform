package com.gxlu.interfacePlatform.database.hibernate;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	static{
		Properties p = System.getProperties();
		//p.put("derby.system.home", "C:\\databases\\sample");
		Configuration config = new Configuration();
		sessionFactory = config.configure().buildSessionFactory();
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
