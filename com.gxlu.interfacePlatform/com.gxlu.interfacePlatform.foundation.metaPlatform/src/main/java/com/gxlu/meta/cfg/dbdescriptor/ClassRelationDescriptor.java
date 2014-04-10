/**************************************************************************
 * $RCSfile: ClassRelationDescriptor.java,v $  $Revision: 1.3 $  $Date: 2008/12/24 02:57:56 $
 *
 * $Log: ClassRelationDescriptor.java,v $
 * Revision 1.3  2008/12/24 02:57:56  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/16 05:51:25  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/12 07:05:03  richie
 * no message
 *
 **************************************************************************/
package com.gxlu.meta.cfg.dbdescriptor;

import com.gxlu.meta.MetaClassRelation;

/**
 * Represent the Connection of MetaClass to MetaClass in database.
 *
 * @author K
 */
public class ClassRelationDescriptor extends RelationDescriptor {

  public MetaClassRelation getMetaClassRelation() {
    return metaClassRelation;
  }

  public void setMetaClassRelation(MetaClassRelation metaClassRelation) {
    this.metaClassRelation = metaClassRelation;
  }

  private MetaClassRelation metaClassRelation;
}

