/**************************************************************************
 * $RCSfile: MetaData.java,v $  $Revision: 1.5 $  $Date: 2009/05/08 05:09:28 $
 *
 * $Log: MetaData.java,v $
 * Revision 1.5  2009/05/08 05:09:28  liuding
 * *** empty log message ***
 *
 * Revision 1.4  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.3  2009/01/04 01:21:02  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lethe
 */
public interface MetaData extends Serializable{
	
	/**
	 * Value of an attribute by attribute's name.
	 * @return
	 */
	Object getValue(String attribute);
	
	
	/**
	 * All MetaAttribute.
	 *
	 * @return
	 */
	List<MetaAttribute> getAttributeList();

	 /**
   * Boolean value of an attribute by attribute's name.
   * @return
   */
	Boolean getBoolean(String attributeName);
	
  /**
   * Set value of an attribute by attribute's name.
   * @param attributeName attribute name.
   * @param value attribute value.
   * @return
   */
	void setValue(String attributeName,Boolean value);

  /**
   * Byte value of an attribute by attribute's name.
   * @return
   */
	Byte getByte(String attributeName);
	
  /**
   * Set value of an attribute by attribute's name.
   * @param attributeName attribute name.
   * @param value attribute value.
   * @return
   */
	void setValue(String attributeName,Byte value);

	/**
   * Double value of an attribute by attribute's name.
   * @return
   */
	Double getDouble(String attributeName);

  /**
   * Set value of an attribute by attribute's name.
   * @param attributeName attribute name.
   * @param value attribute value.
   * @return
   */
	void setValue(String attributeName,Double value);

	/**
   * Float value of an attribute by attribute's name.
   * @return
   */
	Float getFloat(String attributeName);

  /**
   * Set value of an attribute by attribute's name.
   * @param attributeName attribute name.
   * @param value attribute value.
   * @return
   */
	void setValue(String attributeName,Float value);

	/**
   * Int value of an attribute by attribute's name.
   * @return
   */
	Integer getInt(String attributeName);

  /**
   * Set value of an attribute by attribute's name.
   * @param attributeName attribute name.
   * @param value attribute value.
   * @return
   */
	void setValue(String attributeName,Integer value);

	/**
   * Long value of an attribute by attribute's name.
   * @return
   */
	Long getLong(String attributeName);

  /**
   * Set value of an attribute by attribute's name.
   * @param attributeName attribute name.
   * @param value attribute value.
   * @return
   */
	void setValue(String attributeName,Long value);

	/**
   * Short value of an attribute by attribute's name.
   * @return
   */
	Short getShort(String attributeName);

  /**
   * Set value of an attribute by attribute's name.
   * @param attributeName attribute name.
   * @param value attribute value.
   * @return
   */
	void setValue(String attributeName,Short value);

	/**
   * BigDecimal value of an attribute by attribute's name.
   * @return
   */
	BigDecimal getBigDecimal(String attributeName);

  /**
   * Set value of an attribute by attribute's name.
   * @param attributeName attribute name.
   * @param value attribute value.
   * @return
   */
	void setValue(String attributeName,BigDecimal value);

	 /**
   * Date value of an attribute by attribute's name.
   * @return
   */
	Date getDate(String attributeName);

  /**
   * Set value of an attribute by attribute's name.
   * @param attributeName attribute name.
   * @param value attribute value.
   * @return
   */
	void setValue(String attributeName,Date value);

	 /**
   * String value of an attribute by attribute's name.
   * @return
   */
	String getString(String attributeName);

  /**
   * Set value of an attribute by attribute's name.
   * @param attributeName attribute name.
   * @param value attribute value.
   * @return
   */
	void setValue(String attributeName,String value);

  /**
   * Set value of an attribute by attribute's name.
   * @param attributeName attribute name.
   * @param value attribute value.
   * @return
   */
	void setValue(String attributeName,Object value);
}

