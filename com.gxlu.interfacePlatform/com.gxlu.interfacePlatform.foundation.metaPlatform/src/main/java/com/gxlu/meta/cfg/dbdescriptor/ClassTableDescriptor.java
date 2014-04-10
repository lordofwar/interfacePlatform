/**************************************************************************
 * $RCSfile: ClassTableDescriptor.java,v $  $Revision: 1.3 $  $Date: 2009/03/03 06:25:12 $
 *
 * $Log: ClassTableDescriptor.java,v $
 * Revision 1.3  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/26 05:55:24  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/12 07:05:03  richie
 * no message
 *
 **************************************************************************/
package com.gxlu.meta.cfg.dbdescriptor;

import java.util.List;

import com.gxlu.meta.MetaClassRelation;

/**
 * Describe a MetaClass database table.
 *
 * @author K
 * @see com.gxlu.meta.cfg.dbdescriptor.TableDescriptor
 */
public class ClassTableDescriptor extends TableDescriptor {

  /**
   * a list of a MetaClass' all component relation tables.
   *
   * @return list of component relation tables
   */
  public List<ComponentRelationDescriptor> getRelationComponentTables() {
    return relationComponentTables;
  }

  /**
   * set a list of a MetaClass' all component relation tables.
   *
   * @param relationComponentTables list of component relation tables
   */
  public void setRelationComponentTables(List<ComponentRelationDescriptor> relationComponentTables) {
    this.relationComponentTables = relationComponentTables;
  }

  /**
   * a list of a MetaClass' all related MetaClass relation tables.
   *
   * @return list of related MetaClass relation tables
   */
  public List<ClassRelationDescriptor> getRelationClassTables() {
    return relationClassTables;
  }

  /**
   * set a list of a MetaClass' all related MetaClass relation tables.
   *
   * @param relationClassTables list of related MetaClass relation tables
   */
  public void setRelationClassTables(List<ClassRelationDescriptor> relationClassTables) {
    this.relationClassTables = relationClassTables;
  }

  public ComponentRelationDescriptor findRelationComponentTable(ComponentTableDescriptor componentDes) {
    for (ComponentRelationDescriptor descriptor : relationComponentTables) {
      if (descriptor.getOppositeTable() == componentDes) {
        return descriptor;
      }
    }

    return null;
  }

//  public ClassRelationDescriptor findRelationClassTable(ClassTableDescriptor classTableDes) {
//    for (ClassRelationDescriptor descriptor : relationClassTables) {
//      if (descriptor.getOppositeTable() == classTableDes) {
//        return descriptor;
//      }
//    }
//
//    return null;
//  }
  
  public ClassRelationDescriptor findRelationClassTable(MetaClassRelation metaClassRelation) {
    for (ClassRelationDescriptor descriptor : relationClassTables) {
      if (descriptor.getMetaClassRelation() == metaClassRelation) {
        return descriptor;
      }
    }

    return null;
  }

  private List<ComponentRelationDescriptor> relationComponentTables;
  private List<ClassRelationDescriptor> relationClassTables;
}
