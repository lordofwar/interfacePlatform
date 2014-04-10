/**************************************************************************
 * $RCSfile: DefaultMetaAttribute.java,v $  $Revision: 1.5 $  $Date: 2009/07/07 12:58:27 $
 *
 * $Log: DefaultMetaAttribute.java,v $
 * Revision 1.5  2009/07/07 12:58:27  liuding
 * *** empty log message ***
 *
 * Revision 1.4  2009/02/27 02:02:00  richie
 * *** empty log message ***
 *
 * Revision 1.3  2009/02/04 03:26:22  liuding
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/24 02:57:35  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/19 09:40:06  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.cfg;

import com.gxlu.meta.MetaAttribute;

/**
 * @author K
 */
public class DefaultMetaAttribute implements MetaAttribute {

	public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableColumnName() {
		return tableColumnName;
	}

	public void setTableColumnName(String tableColumnName) {
		this.tableColumnName = tableColumnName;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public int getDataCategory() {
		return dataCategory;
	}

	public void setDataCategory(int dataCategory) {
		this.dataCategory = dataCategory;
	}

	public String getDictionaryAttributeID() {
		return dictionaryAttributeID;
	}

	public void setDictionaryAttributeID(String dictionaryAttributeID) {
		this.dictionaryAttributeID = dictionaryAttributeID;
	}

	public String getDictionaryClassID() {
		return dictionaryClassID;
	}

	public void setDictionaryClassID(String dictionaryClassID) {
		this.dictionaryClassID = dictionaryClassID;
	}
	@Override
	public String toString() {
		StringBuffer toString = new StringBuffer();
		if(attributeName!=null){
			toString.append(attributeName);
			toString.append(" | ");
		}
		if(displayName!=null){
			toString.append(displayName);
		}
		return toString.toString();
	}
	private String dictionaryAttributeID;

	private String dictionaryClassID;

	private String tableName;

	private String tableColumnName;

	private Object defaultValue;

	private String displayName;

	private String attributeName;

	private int dataType;

	private int dataCategory;

	private long id;
}
