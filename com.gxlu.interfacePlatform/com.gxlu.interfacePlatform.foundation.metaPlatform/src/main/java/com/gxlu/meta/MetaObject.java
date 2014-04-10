/**************************************************************************
 * $RCSfile: MetaObject.java,v $  $Revision: 1.9 $  $Date: 2009/06/08 09:30:39 $
 *
 * $Log: MetaObject.java,v $
 * Revision 1.9  2009/06/08 09:30:39  richie
 * *** empty log message ***
 *
 * Revision 1.8  2009/04/15 01:32:11  richie
 * *** empty log message ***
 *
 * Revision 1.7  2009/03/30 03:36:24  richie
 * *** empty log message ***
 *
 * Revision 1.6  2009/03/23 01:31:01  richie
 * *** empty log message ***
 *
 * Revision 1.5  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.4  2009/01/04 01:21:02  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta;


import java.util.List;

/**
 * @author lethe
 */
public interface MetaObject extends MetaData {
	
	byte PERSIST_TYPE_NOCHANGE = 1;

	byte PERSIST_TYPE_ADD = 2;

	byte PERSIST_TYPE_UPDATE = 3;

	byte PERSIST_TYPE_DELETE = 4;

	/**
	 * MetaClass this MetaObject relay on.
	 * @return
	 */
	MetaClass getMetaClass();
	
	/**
	 * add a fech for relation. 
	 */
	void addFech(String fech);
	
	 /**
   * add a fech for relation. 
   */
  void removeFech(String fech);

	/**
   * all feches of this MetaObject.
   */
	String[] getFeches();
	
	/**
	 * Collection of all relation object by specification MetaClassRelation.
	 * @return
	 */
	List<MetaObject> getRelateObject(MetaClassRelation metaClassRelation);

	 /**
   * Collection of all relation object by specification MetaClassRelation.
   * @return
   */
  List<MetaObject> getRelateObject(String alias);


  /**
	 * Set relation objects.
	 */
	void setRelateObject(List<MetaObject> metaObjects, MetaClassRelation metaClassRelation);
	

  /**
   * Set relation objects.
   */
  void setRelateObject(List<MetaObject> metaObjects, String metaClassRelationCode);
  

  /**
	 * Set associated components.
	 */
	void setComponentObject(MetaComponentObject metaComponentObject, MetaComponentRelation metaComponentRelation);
	

  /**
   * Set associated components.
   */
  void setComponentObject(MetaComponentObject metaComponentObject, String metaComponentRelationCode);
	
	/**
	 * Collection of all relation component by specification MetaComponentRelation
	 * @return
	 */
	MetaComponentObject getRelateComponentObject(MetaComponentRelation metaComponentRelation);

	 /**
   * Collection of all relation component by specification MetaComponentRelation
   * @return
   */
  MetaComponentObject createRelateComponentObject(MetaComponentRelation metaComponentRelation);

	/**
   * Collection of all relation component by specification MetaComponentRelation
   * @return
   */
  MetaComponentObject getRelateComponentObject(String componentCode);

  /**
   * Collection of all relation component by specification MetaComponentRelation
   * @return
   */
  MetaComponentObject createRelateComponentObject(String componentCode);

  
	
	/**
	 * All atrribute.
	 *
	 * @return
	 */
	List<MetaAttribute> getAttributeList();
	
	/**
	 * Persist type
	 * @see com.gxlu.meta.MetaObject.PERSIST_TYPE_NOCHANGE
	 * @see com.gxlu.meta.MetaObject.PERSIST_TYPE_ADD
	 * @see com.gxlu.meta.MetaObject.PERSIST_TYPE_UPDATE
	 * @see com.gxlu.meta.MetaObject.PERSIST_TYPE_DELETE
	 * @return
	 */
	byte getPersistType();
	
	
	 /**
   * Set persist type
   * @see com.gxlu.meta.MetaObject.PERSIST_TYPE_NOCHANGE
   * @see com.gxlu.meta.MetaObject.PERSIST_TYPE_ADD
   * @see com.gxlu.meta.MetaObject.PERSIST_TYPE_UPDATE
   * @see com.gxlu.meta.MetaObject.PERSIST_TYPE_DELETE
   */
	void setPersistType(byte persistType);
	
	/**
	 * @return
	 */
	Object toJavaBean();
	
	/**
	 * @return
	 */
	Object toJavaBean(Object javaBean);
	
	/**
	 * @param javaBean
	 */
	void fromJavaBean(Object javaBean);
}
