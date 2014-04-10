/**************************************************************************
 * $RCSfile: ComponentTableDescriptor.java,v $  $Revision: 1.2 $  $Date: 2008/12/26 05:55:24 $
 *
 * $Log: ComponentTableDescriptor.java,v $
 * Revision 1.2  2008/12/26 05:55:24  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/12 07:05:03  richie
 * no message
 *
 **************************************************************************/
package com.gxlu.meta.cfg.dbdescriptor;

import java.util.List;

/**
 * Describe a Component database table.
 *
 * @author K
 * @see com.gxlu.meta.cfg.dbdescriptor.TableDescriptor
 */
public class ComponentTableDescriptor extends TableDescriptor {

  /**
   * a list of a MetaClass' all related MetaClass relation tables.
   *
   * @return list of related MetaClass relation tables
   */
  public List<ComponentRelationDescriptor> getRelationClassTables() {
    return relationClassTables;
  }

  /**
   * set a list of a MetaClass' all related MetaClass relation tables.
   *
   * @param relationClassTables list of related MetaClass relation tables
   */
  public void setRelationClassTables(List<ComponentRelationDescriptor> relationClassTables) {
    this.relationClassTables = relationClassTables;
  }

  private List<ComponentRelationDescriptor> relationClassTables;
}

