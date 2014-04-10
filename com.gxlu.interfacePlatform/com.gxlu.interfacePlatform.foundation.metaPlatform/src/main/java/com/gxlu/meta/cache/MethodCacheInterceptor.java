/**********************************************************************
 *
 * $RCSfile: MethodCacheInterceptor.java,v $  $Revision: 1.5 $  $Date: 2010/05/10 14:36:44 $
 *
 * $Log: MethodCacheInterceptor.java,v $
 * Revision 1.5  2010/05/10 14:36:44  wujialin
 * MR#:telant-GD-500-339  集成测试  3  物理——机框，机架，板卡 模板改造
 *
 * Revision 1.4  2009/07/15 01:51:21  liuding
 * *** empty log message ***
 *
 * Revision 1.3  2009/05/21 09:07:06  liuding
 * *** empty log message ***
 *
 * Revision 1.2  2009/05/21 07:33:35  liuding
 * *** empty log message ***
 *
 * Revision 1.1  2009/05/08 05:09:28  liuding
 * *** empty log message ***
 *
 *
 *
 *********************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : MethodCacheInterceptor.java
 * Created on : Apr 28, 2009 9:31:02 AM
 * Creator : lethe
 */
package com.gxlu.meta.cache;



import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.gxlu.meta.MetaObjectQueryResult;
import com.gxlu.meta.QueryCriteria;

/**
 * <pre>
 * Description : TODO
 * @author lethe
 * </pre>
 */
public class MethodCacheInterceptor implements MethodInterceptor, InitializingBean {

	private static Logger logger = Logger.getLogger(MethodCacheInterceptor.class);
	
	private CacheManager cacheManager;


	public Object invoke(MethodInvocation invocation) throws Throwable {
//		String targetName = invocation.getThis().getClass().getName();
		String methodName = invocation.getMethod().getName();
		Object[] arguments = invocation.getArguments();
		Object result;
		if (arguments.length == 1 && arguments[0] instanceof com.gxlu.meta.QueryCriteria && methodName.equals("query")) {
			QueryCriteria criteria = (QueryCriteria) arguments[0];
			boolean isexecute = cacheManager.isExecuteCache(criteria);
			if (isexecute) {
				String key = cacheManager.getCacheKey(criteria);
				Object object = cacheManager.getCacheObject(key);
				if (object == null) {
					result = invocation.proceed();
					cacheManager.putDictionaryToCache(result, key);
					if(result instanceof MetaObjectQueryResult){
						object = ((MetaObjectQueryResult)result).clone();
					}else{
						object = result;
					}
				}
				return object;
			}
		}
		return invocation.proceed();

	}

	public void afterPropertiesSet() throws Exception {

	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}
