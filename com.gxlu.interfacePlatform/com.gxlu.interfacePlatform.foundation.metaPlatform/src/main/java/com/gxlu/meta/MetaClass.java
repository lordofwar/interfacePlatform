/**************************************************************************
 * $RCSfile: MetaClass.java,v $  $Revision: 1.11 $  $Date: 2010/04/07 05:21:30 $
 *
 * $Log: MetaClass.java,v $
 * Revision 1.11  2010/04/07 05:21:30  richie
 * *** empty log message ***
 *
 * Revision 1.10  2009/08/09 08:06:01  liuding
 * *** empty log message ***
 *
 * Revision 1.9  2009/07/29 08:20:56  richie
 * *** empty log message ***
 *
 * Revision 1.8  2009/05/08 05:09:28  liuding
 * *** empty log message ***
 *
 * Revision 1.7  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.6  2009/02/27 02:02:00  richie
 * *** empty log message ***
 *
 * Revision 1.5  2009/01/04 01:21:03  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta;

import java.io.Serializable;
import java.util.List;

/**
 * @author lethe
 */
public interface MetaClass extends Serializable{
	
	/**
	 * Corresponding of XM_ENTITYSPEC.CODE in database.
	 * @return
	 */
	String getCode();
	
  /**
   * Corresponding of XM_ENTITYSPEC.DISPLAYNAME in database.
   * @return
   */
	String getDisplayName();
	
  /**
   * Corresponding of XM_ENTITYSPEC.CLASSNAME in database.
   * @return
   */
	String getClassName();
	
	/**
	 * Class name of its inherited MetaClass.
	 * @return
	 */
	String getParentClassName();
	
	/**
	 * All attribute of MetaClass.
	 * @return
	 */
	List<MetaAttribute> getMetaAttributes();
	
	/**
	 * Inherited MetaClass
	 * @return
	 */
	MetaClass getParentMetaClass();
	
	
	/**
	 * If a MetaObject is an instance of this MetaClass. like "instance of" in java. 
	 * @param object
	 * @return
	 */
	boolean isInstance(MetaObject mo);
	
	/**
	 * Relationships between this MetaClass and another MetaClass. 
	 * @return
	 */
	List<MetaClassRelation> getMetaClassRelations();
	
	/**
   * Relationships between this MetaClass and its MetaComponent. 
   * @return
   */
	List<MetaComponentRelation> getMetaComponentRelations();

	/**
   * Find MetaAttribute by attribute's name. 
   * @return
   */
  MetaAttribute findMetaAttribute(String key);

  /**
   * Find MetaComponentRelation by MetaComponent's name. 
   * @return
   */
  MetaComponentRelation findMetaComponentRelation(String componentName); 
  
  /**
   * Find MetaClassRelation by MetaClassRelation's code.
   * @return
   */
  MetaClassRelation findMetaClassRelation(String code);

  /**
   * Corresponding of XM_ENTITYSPEC.ID in database.
   * @return
   */
  long getId();
  
  MetaAttribute getPrimitiveAttribute();

  List<MetaClass> getChildren();
  /**
   * Corresponding of XM_ENTITYSPEC.VERSIONINSTANCE in database.
   * @return
   */
  long getVersionInstanceId();
}

