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
 * Description : ȡ�ò�ѯ�����ж�Ӧ��sql ����
 * @author lethe
 * </pre>
 */
public interface NamingParseStrategy {
	
	/**
	 * �õ�SQL�� �ñ�ı������ýӿ�����hsql �������־������������ű�ȡ��ʵ���������ֲ��sql
	 * Ĭ������£�ȡֵ��xm_entityspec �е�code
	 * @param metaClassName
	 * @return
	 */
	public String getTableAliasName();
	
	/**
	 * ����SQL table�б���
	 * @param tableAliasName
	 * @return
	 */
	public String setTableAliasName(String tableAliasName);
	
	/**
	 * �����������õ�SQL�����еı���
	 * @param columnAliasName
	 * @return
	 */
	public String getColumnAliasName(String attrName);
}

