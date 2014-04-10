/**************************************************************************
 * $RCSfile: MetaAttributeObject.java,v $  $Revision: 1.1 $  $Date: 2010/05/28 08:54:28 $
 *
 * $Log: MetaAttributeObject.java,v $
 * Revision 1.1  2010/05/28 08:54:28  agazeng
 * 图形展示显示中文名称
 *
 **************************************************************************/
 
package com.gxlu.meta.cfg;

import java.util.List;

import com.gxlu.meta.MetaAttribute;
import com.gxlu.meta.MetaClass;
import com.gxlu.meta.MetaClassRelation;
import com.gxlu.meta.MetaComponentObject;
import com.gxlu.meta.MetaComponentRelation;
import com.gxlu.meta.MetaObject;
 
/**
 * <p>Title: MetaAttributeObject.java </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: GXLU </p>
 * @author ZengXiangGuo
 * @see 
 * @version 1.0
 */
public class MetaAttributeObject extends SimpleMetaData implements MetaObject{
	public MetaAttributeObject() {
	
	}
	
	
	public static MetaAttributeObject fromMetaAttributeList(List<MetaAttribute> metaAttributeList) {
		MetaAttributeObject metaAttributeObject = new MetaAttributeObject();
		for(MetaAttribute ma : metaAttributeList) {
			metaAttributeObject.maps.put(ma.getAttributeName(), ma.getDisplayName());
		}
		return metaAttributeObject;
	}
	

	public void addFech(String fech) {
		// TODO Auto-generated method stub
		
	}


	public MetaComponentObject createRelateComponentObject(
			MetaComponentRelation metaComponentRelation) {
		// TODO Auto-generated method stub
		return null;
	}


	public MetaComponentObject createRelateComponentObject(String componentCode) {
		// TODO Auto-generated method stub
		return null;
	}


	public void fromJavaBean(Object javaBean) {
		// TODO Auto-generated method stub
		
	}


	public List<MetaAttribute> getAttributeList() {
		// TODO Auto-generated method stub
		return null;
	}


	public String[] getFeches() {
		// TODO Auto-generated method stub
		return null;
	}


	public MetaClass getMetaClass() {
		// TODO Auto-generated method stub
		return null;
	}


	public byte getPersistType() {
		// TODO Auto-generated method stub
		return 0;
	}


	public MetaComponentObject getRelateComponentObject(
			MetaComponentRelation metaComponentRelation) {
		// TODO Auto-generated method stub
		return null;
	}


	public MetaComponentObject getRelateComponentObject(String componentCode) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<MetaObject> getRelateObject(MetaClassRelation metaClassRelation) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<MetaObject> getRelateObject(String alias) {
		// TODO Auto-generated method stub
		return null;
	}


	public void removeFech(String fech) {
		// TODO Auto-generated method stub
		
	}


	public void setComponentObject(MetaComponentObject metaComponentObject,
			MetaComponentRelation metaComponentRelation) {
		// TODO Auto-generated method stub
		
	}


	public void setComponentObject(MetaComponentObject metaComponentObject,
			String metaComponentRelationCode) {
		// TODO Auto-generated method stub
		
	}


	public void setPersistType(byte persistType) {
		// TODO Auto-generated method stub
		
	}


	public void setRelateObject(List<MetaObject> metaObjects,
			MetaClassRelation metaClassRelation) {
		// TODO Auto-generated method stub
		
	}


	public void setRelateObject(List<MetaObject> metaObjects,
			String metaClassRelationCode) {
		// TODO Auto-generated method stub
		
	}


	public Object toJavaBean() {
		// TODO Auto-generated method stub
		return null;
	}


	public Object toJavaBean(Object javaBean) {
		// TODO Auto-generated method stub
		return null;
	}



	 
}
