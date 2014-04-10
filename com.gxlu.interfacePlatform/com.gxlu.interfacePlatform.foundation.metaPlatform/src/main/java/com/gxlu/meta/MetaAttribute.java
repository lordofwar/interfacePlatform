/**************************************************************************
 * $RCSfile: MetaAttribute.java,v $  $Revision: 1.7 $  $Date: 2009/03/03 06:25:12 $
 *
 * $Log: MetaAttribute.java,v $
 * Revision 1.7  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.6  2009/02/27 02:02:00  richie
 * *** empty log message ***
 *
 * Revision 1.5  2009/02/04 03:24:12  liuding
 * *** empty log message ***
 *
 * Revision 1.4  2009/01/07 02:37:57  richie
 * *** empty log message ***
 *
 * Revision 1.3  2009/01/04 01:21:02  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta;

/**
 * @author lethe
 */
public interface MetaAttribute {
	
	/**
	 * Name of attribute, corresponding of XM_ENTITYDESCRIPTOR.CODE in database.
	 * @return
	 */
	String getAttributeName();
	
	/**
	 * Display name of attribute, will be showed in UI, corresponding of XM_ENTITYDESCRIPTOR.DISPLAYNAME in database.
	 * @return
	 */
	String getDisplayName();
	
	/**
	 * Data type of attribute, maybe long, byte, int, float, double, string, etc.
	 * @see com.gxlu.meta.tools.MetaConsts
	 * @return
	 */
	int getDataType();
	
	/**
	 * Default value of attribute.
	 * @return
	 */
	Object getDefaultValue();
	
	/**
	 * Is user defined or system defined.
	 * @return
	 */
	int getDataCategory();
	
	/**
	 * Table name of attribute included.
	 * @return
	 */
	String getTableName();
	
	/**
	 * Table column name of attribute. 
	 * @return
	 */
	String getTableColumnName();
	
	/**
	 * If attribute represents a dictionary value. its Class ID is MetaClass's Code.
	 * @return
	 */
	String getDictionaryClassID();
	
	/**
	 * If attribute represents a dictionary value. its attribute Id is MetaClass's Attribute Name.
	 * @return
	 */
	String getDictionaryAttributeID();
	
	/**
	 * corresponding of XM_ENTITYDESCRIPTOR.ID in database.
	 * @return
	 */
  long getId();
}

