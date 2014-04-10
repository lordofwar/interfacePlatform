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
 * Description : һ��Ԫ���ݶ��� �ɣ�������չ������������ֹ���
 * @author lethe
 * </pre>
 */
public interface NamingMetaObjectParseStrategy extends NamingParseStrategy {
	
	/**
	 * �������ݿ�����õ���ǰ��ѯ�ı���
	 * @param tableName
	 * @return
	 */
	String getTableAliasName(String tableName);
	
	/**
	 * ���ݹ�ϵCode �õ���ϵ�Ľ�������
	 * @param relationCode
	 * @return
	 */
	NamingMetaObjectParseStrategy getRelationNamingParseStrategy(String relationCode);
	
	
}

