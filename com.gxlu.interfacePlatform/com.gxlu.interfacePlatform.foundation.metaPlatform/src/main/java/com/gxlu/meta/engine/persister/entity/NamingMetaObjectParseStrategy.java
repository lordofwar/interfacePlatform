/**********************************************************************
 *
 * $RCSfile: NamingMetaObjectParseStrategy.java,v $  $Revision: 1.1 $  $Date: 2009/12/25 03:39:50 $
 *
 * $Log: NamingMetaObjectParseStrategy.java,v $
 * Revision 1.1  2009/12/25 03:39:50  liuding
 * *** empty log message ***
 *
 *
 *
 *********************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : NamingMetaObjectParseStrategy.java
 * Created on : Dec 22, 2009 10:33:34 AM
 * Creator : lethe
 */
package com.gxlu.meta.engine.persister.entity;

import com.gxlu.meta.NamingParseStrategy;

/**
 * <pre>
 * Description : 一个元数据对象 由：主表，扩展表，组件表，三部分构成
 * @author lethe
 * </pre>
 */
public interface NamingMetaObjectParseStrategy extends NamingParseStrategy {
	
	/**
	 * 根据数据库表名得到当前查询的别名
	 * @param tableName
	 * @return
	 */
	String getTableAliasName(String tableName);
	
	/**
	 * 根据关系Code 得到关系的解析策略
	 * @param relationCode
	 * @return
	 */
	NamingMetaObjectParseStrategy getRelationNamingParseStrategy(String relationCode);
	
	
}

