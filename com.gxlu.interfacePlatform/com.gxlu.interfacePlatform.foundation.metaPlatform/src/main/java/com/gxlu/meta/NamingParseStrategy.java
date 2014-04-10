/**********************************************************************
 *
 * $RCSfile: NamingParseStrategy.java,v $  $Revision: 1.1 $  $Date: 2009/12/25 03:40:18 $
 *
 * $Log: NamingParseStrategy.java,v $
 * Revision 1.1  2009/12/25 03:40:18  liuding
 * *** empty log message ***
 *
 *
 *
 *********************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : NamingParseStrategy.java
 * Created on : Dec 21, 2009 3:52:30 PM
 * Creator : lethe
 */
package com.gxlu.meta;

/**
 * <pre>
 * Description : 取得查询条件中对应的sql 别名
 * @author lethe
 * </pre>
 */
public interface NamingParseStrategy {
	
	/**
	 * 得到SQL中 该表的别名，该接口类似hsql 不用区分具体属性在哪张表，取得实体别名即可植入sql
	 * 默认情况下，取值与xm_entityspec 中的code
	 * @param metaClassName
	 * @return
	 */
	public String getTableAliasName();
	
	/**
	 * 设置SQL table中别名
	 * @param tableAliasName
	 * @return
	 */
	public String setTableAliasName(String tableAliasName);
	
	/**
	 * 根据属性名得到SQL属性列的别名
	 * @param columnAliasName
	 * @return
	 */
	public String getColumnAliasName(String attrName);
}

