/**********************************************************************
 *
 * $$RCSfile: MetaObjectHelper.java,v $$  $$Revision: 1.18 $$  $$Date: 2010/05/08 07:02:33 $$
 *
 * $$Log: MetaObjectHelper.java,v $
 * $Revision 1.18  2010/05/08 07:02:33  liuding
 * $*** empty log message ***
 * $
 * $Revision 1.17  2010/03/18 08:16:19  liuding
 * $*** empty log message ***
 * $
 * $Revision 1.16  2009/11/09 07:41:02  liuding
 * $*** empty log message ***
 * $
 * $Revision 1.15  2009/09/01 01:48:38  liuding
 * $*** empty log message ***
 * $$
 *
 *
 *********************************************************************/

/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : MetaObjectHelper.java
 * Created on : Jan 9, 2009 9:17:31 AM
 * Creator : lethe
 */
package com.gxlu.meta.help;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gxlu.meta.MetaAttribute;
import com.gxlu.meta.MetaClass;
import com.gxlu.meta.MetaComponent;
import com.gxlu.meta.MetaComponentObject;
import com.gxlu.meta.MetaDBManager;
import com.gxlu.meta.MetaDBManagerImpl;
import com.gxlu.meta.MetaObject;
import com.gxlu.meta.cfg.DefaultMetaComponentObject;
import com.gxlu.meta.cfg.DefaultMetaObject;
import com.gxlu.meta.cfg.dbdescriptor.ClassTableDescriptor;

/**
 * <pre>
 * Description : TODO
 * @author lethe
 * </pre>
 */
public class MetaObjectHelper {
	
	private static Logger logger = Logger.getLogger(MetaObjectHelper.class);
	
	public static MetaObjectHelper getInstance(){
		return instance;
	}

	/**
	 * 通过metaClass的名称得到 metaClass
	 * @param metaClassName
	 * @return
	 */
	public static MetaClass getMetaClass(String metaClassName){
		if(metaClassName == null || "".equals(metaClassName)){
			return null;
		}
		return instance.metaDBManager.getMetaClass(metaClassName);
	}
	
	public static List<MetaClass> getTotalMetaClass(){
		List<String> metaClassNameList = instance.metaDBManager.getTotalMetaClassNames();
		List<MetaClass> ret = new ArrayList<MetaClass>();
		for (String metaClassName : metaClassNameList) {
			MetaClass metaClass = getMetaClass(metaClassName);
			if(metaClass!=null){
				ret.add(metaClass);
			}
		}
		return ret;
	}
	
	/**
	 * 得到给定metaClassName下的第一层的子MetaClass
	 * @param metaClassName
	 * @return
	 */
	public static List<MetaClass> getChildMetaClass(String metaClassName){
		if(metaClassName == null || "".equals(metaClassName)){
			return null;
		}
		return instance.metaDBManager.getChildMetaClass(metaClassName);
	}
	
	/**
	 * 返回所有的儿子，（包含儿子的儿子）
	 * @param metaClassName
	 * @return
	 */
	public static List<MetaClass> getAllChildMetaClass(String metaClassName){
		if(metaClassName == null || "".equals(metaClassName)){
			return null;
		}
		List<MetaClass> list = instance.metaDBManager.getChildMetaClass(metaClassName);
		if(list!=null && list.size()>0){
			List<MetaClass>  temp = new ArrayList<MetaClass>();
			Iterator<MetaClass> iter = list.iterator();
			for (Iterator<MetaClass> iterator = iter; iterator.hasNext();) {
				MetaClass metaClass = (MetaClass) iterator.next();
				List<MetaClass> subList = getAllChildMetaClass(metaClass.getClassName());
				if(subList!=null && subList.size()>0){
					temp.addAll(subList);
				}
			}
			if(temp.size()>0){
				list.addAll(temp);
			}
		}
		return list;
	}
	/**
	 * 是否是MetaClass的实例
	 * @param metaObject
	 * @param metaClass
	 * @return
	 */
	public static boolean isInstance(MetaObject metaObject,MetaClass metaClass){
		if(metaObject==null
				|| metaObject.getMetaClass()==null
				|| metaClass==null){
			return false;
		}
		if(metaObject.getMetaClass().getClassName().equals(metaClass.getClassName())){
			return true;
		}else{
			return isChildren(metaObject.getMetaClass(),metaClass);
		}
	}
	
	/**
	 * 是否是子类
	 * @param children
	 * @param parent
	 * @return
	 */
	public static boolean isChildren(MetaClass children,MetaClass parent){
		if(children ==null || parent == null ){
			return false;
		}
		boolean isChildren = false;
		MetaClass temp = children.getParentMetaClass();
		while(temp!=null){
			if(temp.getClassName().equals(parent.getClassName())){
				isChildren = true;
				break;
			}
			temp = temp.getParentMetaClass();
		}
		return isChildren;
	}
	
	
	/**
	 * 通过metaClass的名称创建一个metaClass的实例
	 * @param metaClassName
	 * @return
	 */
	public static MetaObject newInstance(String metaClassName){
		MetaClass metaClass = instance.metaDBManager.getMetaClass(metaClassName);
		MetaObject metaObject = newInstance(metaClass);
		return metaObject;
	}
	
	/**
	 * 创建一个metaClass的实例
	 * @param metaClass
	 * @return
	 */
	public static MetaObject newInstance(MetaClass metaClass){
		MetaObject metaObject = new DefaultMetaObject(metaClass);
		if(metaClass.findMetaAttribute("METACATEGORY")!=null){
			metaObject.setValue("METACATEGORY", metaClass.getClassName());
		}
		return metaObject;
	}
	
	public static MetaAttribute getPrimitiveAttribute(MetaClass metaClass){
		MetaAttribute primitiveKey = null;
		ClassTableDescriptor desc = instance.metaDBManager.getClassTableDescriptor(metaClass);
		if(desc!=null && desc.getPrimitiveProperty()!=null){
			primitiveKey = desc.getPrimitiveProperty().getMetaAttribute();
		}
		return primitiveKey;
	}
	
	/**
	 * 根据组件描述<MetaComponent>创建组件对象<MetaComponentObject>
	 * @param metaComponent
	 * @return
	 */
	public static MetaComponentObject newComponentInstance(MetaComponent metaComponent){
		MetaComponentObject compObject = new DefaultMetaComponentObject(metaComponent);
		return compObject;
	}
	
	public static MetaObject cloneMetaObject(MetaObject metaObject){
		try {
			return (DefaultMetaObject) ((DefaultMetaObject)metaObject).clone();
		} catch (CloneNotSupportedException e) {
			logger.error("clone metaObject exception ",e);
			throw new RuntimeException("clone metaObject exception ",e);
		}
	}
	
	
	public static void copyBaseInfo(MetaObject target,MetaObject source) {
		if(source.getMetaClass().getClassName().equals(target.getMetaClass().getClassName())){
			if(source instanceof DefaultMetaObject
					&& target instanceof DefaultMetaObject){
				DefaultMetaObject sourceMeta  = (DefaultMetaObject) source;
				DefaultMetaObject targetMeta  = (DefaultMetaObject) target;
//				targetMeta.getMaps().putAll(sourceMeta.getMaps());
				copyMap(targetMeta.getMaps(),sourceMeta.getMaps());
				targetMeta.setComponents(sourceMeta.getComponents());
//				targetMeta.setRelations(sourceMeta.getRelations());
//				targetMeta.setFeches(sourceMeta.getFeches());
			}
		}else{
			logger.error("not support metaObject subclass:"+source.getClass().getName()+ "  or "+target.getClass().getName());
			throw new RuntimeException("Can not find metaClassName :( " + source.getMetaClass().getClassName()
					+" ) in ( "+target.getMetaClass().getClassName()+")");
		}
	}
	private static void copyMap(Map target,Map source){
		for (Iterator iter = source.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if(target.get(key) == null){
				target.put(key, source.get(key));
			}
			
		}
		
	}

	private MetaObjectHelper(){
    metaDBManager = MetaDBManagerImpl.getInstance();
  }
	 
  private MetaDBManager metaDBManager;
  private static MetaObjectHelper instance = new MetaObjectHelper();
}

