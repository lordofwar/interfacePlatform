/**********************************************************************
 *
 * $RCSfile: DefaultNamingMetaObjectParseStrategy.java,v $  $Revision: 1.2 $  $Date: 2010/03/17 09:10:11 $
 *
 * $Log: DefaultNamingMetaObjectParseStrategy.java,v $
 * Revision 1.2  2010/03/17 09:10:11  liuding
 * *** empty log message ***
 *
 * Revision 1.1  2009/12/25 03:39:50  liuding
 * *** empty log message ***
 *
 *
 *
 *********************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : DefaultNamingMetaObjectParseStrategy.java
 * Created on : Dec 22, 2009 3:34:16 PM
 * Creator : lethe
 */
package com.gxlu.meta.engine.persister.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gxlu.meta.MetaAttribute;
import com.gxlu.meta.MetaClass;
import com.gxlu.meta.MetaComponent;
import com.gxlu.meta.MetaComponentRelation;
import com.gxlu.meta.MetaDBManagerImpl;
import com.gxlu.meta.cfg.dbdescriptor.ClassTableDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.RelationDescriptor;

/**
 * <pre>
 * Description : TODO
 * @author lethe
 * </pre>
 */
public class DefaultNamingMetaObjectParseStrategy implements NamingMetaObjectParseStrategy{
	
	private MetaClass metaClass = null;
	
	private MetaComponent metaComponent = null;
	
	protected Map<String,DefaultNamingMetaObjectParseStrategy> relationMapping = new HashMap<String,DefaultNamingMetaObjectParseStrategy>();
	
	private Map<String,String> tableNameMapping = new HashMap<String,String>();
	
	private Map<String,String> attrNameMapping = new HashMap<String,String>();
	
	private String metaClassTableAliasName = null;
	
	protected Counter counter = null;
	
	protected static String coreTableAlias = "C";
	
	protected static String elseTableAlias = "P";
	
	public DefaultNamingMetaObjectParseStrategy(MetaClass metaClass){
		this.metaClass = metaClass;
		this.counter = new Counter();
		this.metaClassTableAliasName = metaClass.getCode();
		init();
	}
	
	private void init(){
		boolean isFirst = false;
		int point = counter.nextInt();
		if(point == 1){
			isFirst = true;
		}
		List<MetaAttribute> attrList  = metaClass.getMetaAttributes();
		ClassTableDescriptor descriptor = MetaDBManagerImpl.getInstance().getClassTableDescriptor(metaClass);
		if(isFirst){
			tableNameMapping.put(descriptor.getTable(), coreTableAlias);
		}else{
			tableNameMapping.put(descriptor.getTable(), elseTableAlias+point);
			point++;
		}
		for (MetaAttribute metaAttribute : attrList) {
			attrNameMapping.put(metaAttribute.getAttributeName(), metaAttribute.getAttributeName());
		}
		
		List<RelationDescriptor> relationDescList = descriptor.getPlainTableRelations();
		for (RelationDescriptor relationDescriptor : relationDescList) {
			tableNameMapping.put(relationDescriptor.getOppositeTable().getTable(),elseTableAlias+counter.nextInt());
		}
	}
	
	public DefaultNamingMetaObjectParseStrategy(MetaComponent metaComponent,DefaultNamingMetaObjectParseStrategy parent,String relationCode){
		this.metaComponent = metaComponent;
		this.counter = parent.counter;
		parent.relationMapping.put(relationCode, this);
		this.metaClassTableAliasName = this.metaComponent.getCode();
		List<MetaAttribute> attrList  = metaComponent.getMetaAttributes();
		for (MetaAttribute metaAttribute : attrList) {
			attrNameMapping.put(metaAttribute.getAttributeName(), metaAttribute.getAttributeName());
		}
		tableNameMapping.put(attrList.get(0).getTableName(),elseTableAlias+counter.nextInt());
	}
	
	public DefaultNamingMetaObjectParseStrategy(MetaClass metaClass,DefaultNamingMetaObjectParseStrategy parent,String relationCode){
		this.metaClass = metaClass;
		this.counter = parent.counter;
		parent.relationMapping.put(relationCode, this);
		this.metaClassTableAliasName = metaClass.getCode();
		init();
	}

	public NamingMetaObjectParseStrategy getRelationNamingParseStrategy(String relationCode) {
		DefaultNamingMetaObjectParseStrategy ret = relationMapping.get(relationCode);
		if(ret == null){
			MetaComponentRelation compRel = metaClass.findMetaComponentRelation(relationCode);
			if(compRel != null){
				ret = new DefaultNamingMetaObjectParseStrategy(compRel.getRelationMetaComponent(),this,relationCode);
				relationMapping.put(relationCode, ret);
			}
		}
		return ret;
	}

	public String getTableAliasName(String tableName) {
		return tableNameMapping.get(tableName);
	}

	public String getColumnAliasName(String attrName) {
		String ret =  attrNameMapping.get(attrName);
		if(ret == null || ret.length() == 0 ){
			return attrName;
		}
		return ret;
	}

	public String getTableAliasName() {
		return metaClassTableAliasName;
	}

	public String setColumnAliasName(String attrName, String columnAliasName) {
		return attrNameMapping.put(attrName, columnAliasName);
	}

	public String setTableAliasName(String tableAliasName) {
		return metaClassTableAliasName = tableAliasName;
	}

	public class Counter{
		private int i = 0;
		public int nextInt(){
			i++;
			return i; 
		}
	}
}

