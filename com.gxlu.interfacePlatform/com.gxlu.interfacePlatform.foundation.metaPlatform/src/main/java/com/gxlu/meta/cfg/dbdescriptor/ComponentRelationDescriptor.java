/**************************************************************************
 * $RCSfile: ComponentRelationDescriptor.java,v $  $Revision: 1.1 $  $Date: 2008/12/12 07:05:03 $
 *
 * $Log: ComponentRelationDescriptor.java,v $
 * Revision 1.1  2008/12/12 07:05:03  richie
 * no message
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : ComponetTableRelationDescriptor.java
 * Created on : Dec 12, 2008 10:53:10 AM
 * Creator : RichieJin
 */
package com.gxlu.meta.cfg.dbdescriptor;

import com.gxlu.meta.MetaComponentRelation;

/**
 * Represent the Connection of MetaClass to Component in database.
 *
 * @author K
 */
public class ComponentRelationDescriptor extends RelationDescriptor {

  public MetaComponentRelation getMetaComponentRelation() {
    return metaComponentRelation;
  }

  public void setMetaComponentRelation(MetaComponentRelation metaComponentRelation) {
    this.metaComponentRelation = metaComponentRelation;
  }

  private MetaComponentRelation metaComponentRelation;
}

