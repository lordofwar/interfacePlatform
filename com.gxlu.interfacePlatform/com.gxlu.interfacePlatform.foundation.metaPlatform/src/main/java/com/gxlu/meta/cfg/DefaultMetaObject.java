/**************************************************************************
 * $RCSfile: DefaultMetaObject.java,v $  $Revision: 1.14 $  $Date: 2009/12/25 03:40:18 $
 *
 * $Log: DefaultMetaObject.java,v $
 * Revision 1.14  2009/12/25 03:40:18  liuding
 * *** empty log message ***
 *
 * Revision 1.13  2009/09/11 02:46:59  liuding
 * *** empty log message ***
 *
 * Revision 1.12  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.11  2009/06/08 09:30:39  richie
 * *** empty log message ***
 *
 * Revision 1.10  2009/05/21 07:33:22  liuding
 * *** empty log message ***
 *
 * Revision 1.9  2009/04/15 01:32:11  richie
 * *** empty log message ***
 *
 * Revision 1.8  2009/03/30 03:36:24  richie
 * *** empty log message ***
 *
 * Revision 1.7  2009/03/23 01:31:01  richie
 * *** empty log message ***
 *
 * Revision 1.6  2009/03/04 10:06:33  richie
 * *** empty log message ***
 *
 * Revision 1.5  2009/03/04 10:06:15  richie
 * *** empty log message ***
 *
 * Revision 1.4  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.3  2008/12/31 04:44:42  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/26 05:55:24  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/24 09:31:24  richie
 * *** empty log message ***
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : DefaultMetaObject.java
 * Created on : Dec 24, 2008 4:24:20 PM
 * Creator : RichieJin
 */
package com.gxlu.meta.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gxlu.meta.MetaAttribute;
import com.gxlu.meta.MetaClass;
import com.gxlu.meta.MetaClassRelation;
import com.gxlu.meta.MetaComponentObject;
import com.gxlu.meta.MetaComponentRelation;
import com.gxlu.meta.MetaObject;

/**
 * @author K
 */
public class DefaultMetaObject extends DefaultMetaData implements MetaObject, Cloneable {

	private static final long serialVersionUID = 4639957911303819572L;

	public Map<MetaComponentRelation, MetaComponentObject> getComponents() {
		return components;
	}

	public void setComponents(Map<MetaComponentRelation, MetaComponentObject> components) {
		this.components = components;
	}

	public Map<MetaClassRelation, List<MetaObject>> getRelations() {
		return relations;
	}

	public void setRelations(Map<MetaClassRelation, List<MetaObject>> relations) {
		this.relations = relations;
	}

	public DefaultMetaObject(MetaClass metaClass) {
		super();
		this.metaClass = metaClass;
		components = new HashMap<MetaComponentRelation, MetaComponentObject>();
		relations = new HashMap<MetaClassRelation, List<MetaObject>>();
	}

	@Override
	public List<MetaAttribute> getAttributeList() {

		return metaClass.getMetaAttributes();
	}

	public MetaClass getMetaClass() {

		return metaClass;
	}

	public void setMetaClass(MetaClass metaClass) {

		this.metaClass = metaClass;
	}

	public byte getPersistType() {

		return persistType;
	}

	public void fromJavaBean(Object javaBean) {
		// TODO
	}

	public MetaComponentObject getRelateComponentObject(MetaComponentRelation metaComponentRelation) {

		return components.get(metaComponentRelation);
	}

	public MetaComponentObject createRelateComponentObject(MetaComponentRelation metaComponentRelation) {
	  return new DefaultMetaComponentObject(metaComponentRelation.getRelationMetaComponent());
	}
	
	public MetaComponentObject getRelateComponentObject(String componentCode) {
		return components.get(metaClass.findMetaComponentRelation(componentCode));
	}

  public MetaComponentObject createRelateComponentObject(String componentCode) {
    return createRelateComponentObject(this.metaClass.findMetaComponentRelation(componentCode));
  }

  public List<MetaObject> getRelateObject(MetaClassRelation metaClassRelation) {

		return relations.get(metaClassRelation);
	}

	public List<MetaObject> getRelateObject(String alias) {

		return relations.get(metaClass.findMetaClassRelation(alias));
	}

	public void setComponentObject(MetaComponentObject metaComponentObject, MetaComponentRelation metaComponentRelation) {

		components.put(metaComponentRelation, metaComponentObject);
	}

	public void setComponentObject(MetaComponentObject metaComponentObject, String metaComponentRelationCode) {
		MetaComponentRelation metaComponentRelation = metaClass.findMetaComponentRelation(metaComponentRelationCode);
		if (metaComponentRelation == null) {
			throw new RuntimeException("Can not find relation by code : " + metaComponentRelation);
		} else {
			setComponentObject(metaComponentObject, metaComponentRelation);
		}
	}

	public void setPersistType(byte persistType) {
		this.persistType = persistType;
	}

	public void setRelateObject(List<MetaObject> metaObjects, MetaClassRelation metaClassRelation) {
		addFech(metaClassRelation.getAlias());
		relations.put(metaClassRelation, metaObjects);
	}

	/**
	 * Set relation objects.
	 */
	public void setRelateObject(List<MetaObject> metaObjects, String metaClassRelationCode) {
		MetaClassRelation metaClassRelation = metaClass.findMetaClassRelation(metaClassRelationCode);
		if (metaClassRelation == null) {
			throw new RuntimeException("Can not find relation by code : " + metaClassRelationCode);
		} else {
			setRelateObject(metaObjects, metaClassRelation);
		}
	}

	public Object toJavaBean() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object toJavaBean(Object javaBean) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setFeches(String[] feches) {
		if (this.feches == null) {
			this.feches = new ArrayList<String>();
		}

		for (String fech : feches) {
			this.feches.add(fech);
		}
	}

	public void addFech(String fech) {
		if (feches == null) {
			feches = new ArrayList<String>();
		}

		feches.add(fech);
	}

	public void removeFech(String fech) {
		List<String> newFeches = new ArrayList<String>();
		if (feches != null) {
			for (String oldFech : feches) {
				if (oldFech != null && fech != null && !fech.equals(oldFech)) {
					newFeches.add(oldFech);
				}
			}
		}

		this.feches = newFeches;
	}

	public String[] getFeches() {

		if (feches != null) {
			return feches.toArray(new String[feches.size()]);
		} else {
			return null;
		}
	}

	/**
	 * clone metaobject
	 */
	public Object clone() throws CloneNotSupportedException {
		DefaultMetaObject clone = (DefaultMetaObject) super.clone();

		List<String> fechesClone = null;
		if(feches!=null){
			fechesClone = new ArrayList<String>();
			fechesClone.addAll(feches);
		}
		clone.resetFeches(fechesClone);

		Map<MetaComponentRelation, MetaComponentObject> componentsClone = null;
		if (components != null) {
			componentsClone = new HashMap<MetaComponentRelation, MetaComponentObject>();
			for (Iterator iter = components.keySet().iterator(); iter.hasNext();) {
				MetaComponentRelation relation = (MetaComponentRelation) iter.next();
				DefaultMetaComponentObject componentObject = (DefaultMetaComponentObject) components.get(relation);
				DefaultMetaComponentObject componentClone = (DefaultMetaComponentObject) componentObject.clone();
				componentsClone.put(relation, componentClone);
			}
		}
		clone.setComponents(componentsClone);

		Map<MetaClassRelation, List<MetaObject>> relationObjectClone = null;
		if (relations != null) {
			relationObjectClone = new HashMap<MetaClassRelation, List<MetaObject>>();
			for (Iterator iter = relations.keySet().iterator(); iter.hasNext();) {
				MetaClassRelation relation = (MetaClassRelation) iter.next();
				List<MetaObject> metaObjects = relations.get(relation);
				List<MetaObject> metaObjectsClone = null;
				if (metaObjects != null) {
					metaObjectsClone = new ArrayList<MetaObject>();
					for (MetaObject object : metaObjects) {
						DefaultMetaObject objectClone = (DefaultMetaObject) ((DefaultMetaObject) object).clone();
						metaObjectsClone.add(objectClone);
					}
				}
				relationObjectClone.put(relation, metaObjectsClone);
			}
		}
		clone.setRelations(relationObjectClone);

		Map<String, Object> mapsClone = null;
		if (getMaps() != null) {
			mapsClone = new HashMap<String, Object>();
			mapsClone.putAll(getMaps());
		}
		clone.setMaps(mapsClone);

		return clone;
	}

	public void resetFeches(List<String> feches) {
		this.feches = feches;
	}
	
	@Override
	public String toString() {
		return this.getMetaClass().toString()+" | persistType : " +persistType +" | " + maps.toString();
	}

	private List<String> feches;

	private byte persistType;

	private MetaClass metaClass;

	private Map<MetaComponentRelation, MetaComponentObject> components;

	private Map<MetaClassRelation, List<MetaObject>> relations;
}
