/**************************************************************************
 * $RCSfile: PropertyDescriptor.java,v $  $Revision: 1.3 $  $Date: 2009/06/19 10:41:23 $
 *
 * $Log: PropertyDescriptor.java,v $
 * Revision 1.3  2009/06/19 10:41:23  richie
 * *** empty log message ***
 *
 * Revision 1.2  2009/05/08 05:09:28  liuding
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/12 07:05:04  richie
 * no message
 *
 **************************************************************************/
package com.gxlu.meta.cfg.dbdescriptor;

import com.gxlu.meta.MetaAttribute;

/**
 * Describe an attribute's database information
 *
 * @author K
 */
public class PropertyDescriptor {

  /**
   * get an attribute's database table column name.
   *
   * @return
   */
  public String getColumn() {
    return column;
  }

  /**
   * set an attribute's database table column name.
   *
   * @param column table column name
   */
  public void setColumn(String column) {
    this.column = column;
  }

  /**
   * get Attribute description for meta class
   * 
   * @return
   */
  public MetaAttribute getMetaAttribute() {
    return metaAttribute;
  }

  /**
   * set related meta class's attribute description
   * 
   * @param metaAttribute
   */
  public void setMetaAttribute(MetaAttribute metaAttribute) {

    this.metaAttribute = metaAttribute;
  }

  private String column;
  private MetaAttribute metaAttribute;
}
