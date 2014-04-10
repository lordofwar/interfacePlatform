/**********************************************************************
 *
 * $RCSfile: CacheManager.java,v $  $Revision: 1.4 $  $Date: 2009/12/25 03:40:18 $
 *
 * $Log: CacheManager.java,v $
 * Revision 1.4  2009/12/25 03:40:18  liuding
 * *** empty log message ***
 *
 * Revision 1.3  2009/08/28 04:46:36  liuding
 * *** empty log message ***
 *
 * Revision 1.2  2009/08/28 03:50:12  liuding
 * *** empty log message ***
 *
 * Revision 1.1  2009/07/15 01:51:21  liuding
 * *** empty log message ***
 *
 *
 *
 *********************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : CacheManager.java
 * Created on : Jul 13, 2009 9:09:35 PM
 * Creator : lethe
 */
package com.gxlu.meta.cache;

import java.util.Vector;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.gxlu.meta.MetaObjectQueryResult;
import com.gxlu.meta.QueryCriteria;
import com.gxlu.meta.criterion.Criterion;

/**
 * <pre>
 * Description : TODO
 * @author lethe
 * </pre>
 */
public class CacheManager {
	private static Logger logger = Logger.getLogger(CacheManager.class);
	
	private Cache dictionaryCache;

	public Cache getDictionaryCache() {
		return dictionaryCache;
	}

	public void setDictionaryCache(Cache dictionaryCache) {
		this.dictionaryCache = dictionaryCache;
	}
	
	public Object getCacheObject(String key){
		if(key != null){
			Element element = dictionaryCache.get(key);
			if (element == null) {
				logger.debug("DictionaryCacheKey not find in cache,reload key :" + key.toString());
				System.out.println("DictionaryCacheKey not find in cache,reload key :" + key.toString());
				return null;
			}
			Object object = element.getValue();
			if(object instanceof MetaObjectQueryResult){
				try {
					object = ((MetaObjectQueryResult)object).clone();
				} catch (CloneNotSupportedException e) {
					logger.error("clone error", e);
					e.printStackTrace();
				}
			}
			return object;
		}else{
			return null;
		}
	}
	
	public void removeCacheObject(String classId,String attributeId) {
		String key = null;
		if (classId != null && attributeId != null) {
			key =  classId + "__" + attributeId;
			this.dictionaryCache.remove(key);
		}
	}
	
	public void putDictionaryToCache(Object object ,String key){
		Element element = new Element(key, object);
		dictionaryCache.put(element);
	}
	
	public String getCacheKey(QueryCriteria criteria) {
		Vector<Criterion> vector = criteria.getExpressions();
		String classId = null;
		String attributeId = null;
		for (Criterion criterion : vector) {
			if ("CLASSID".equals(criterion.getKey())) {
				classId = (String) criterion.getParams()[0];
			} else if ("ATTRIBUTEID".equals(criterion.getKey())) {
				attributeId = (String) criterion.getParams()[0];
			}
		}
		if (classId != null && attributeId != null) {
			return classId + "__" + attributeId;
		} else {
			return null;
		}
	}

	public boolean isExecuteCache(QueryCriteria criteria) {
		if (criteria.getMetaClass() != null
				&& !criteria.getMetaClass().getClassName().equals("com.gxlu.ngrm.common.Dictionary")) {
			return false;
		}

		Vector<Criterion> vector = criteria.getExpressions();
		String classId = null;
		String attributeId = null;
		for (Criterion criterion : vector) {
			if ("CLASSID".equals(criterion.getKey())) {
				classId = (String) criterion.getParams()[0];
			} else if ("ATTRIBUTEID".equals(criterion.getKey())) {
				attributeId = (String) criterion.getParams()[0];
			}
		}

		if (classId != null && attributeId != null && vector.size() == 2 && criteria.getChildrenFetchs().size()==0
				&& criteria.getRelationQueryCriterias().size()==0) {
			return true;
		} else {
			return false;
		}
	}
	

}

