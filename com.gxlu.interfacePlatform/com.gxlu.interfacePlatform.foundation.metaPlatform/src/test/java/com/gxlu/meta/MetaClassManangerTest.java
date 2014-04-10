/**************************************************************************
 * $RCSfile: MetaClassManangerTest.java,v $  $Revision: 1.2 $  $Date: 2008/12/24 07:05:06 $
 *
 * $Log: MetaClassManangerTest.java,v $
 * Revision 1.2  2008/12/24 07:05:06  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/19 09:43:42  richie
 * *** empty log message ***
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : MetaClassManangerTest.java
 * Created on : Dec 18, 2008 5:37:57 PM
 * Creator : RichieJin
 */
package com.gxlu.meta;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.gxlu.meta.cfg.Configuration;
import com.gxlu.meta.cfg.dbdescriptor.ClassTableDescriptor;

/**
 * <pre>
 * Description : TODO
 * @author RichieJin
 * </pre>
 */
@Ignore
public class MetaClassManangerTest {

  public MetaClassManangerTest() throws Exception{
    metaClassManager = (new Configuration("conf/meta.cfg.xml")).getMetaDBManager();
  }

  @Test
  public void testAllMetaClassName() {
    Assert.assertEquals(11, metaClassManager.getTotalMetaClassNames().size());
  }
  
  @Test
  public void testChildMetaClassAttribute() {
    MetaClass child = metaClassManager.getMetaClass("com.gxlu.ngrm.ne.BTS");
    Assert.assertEquals(16, child.getMetaAttributes().size());
    Assert.assertEquals("com.gxlu.ngrm.ne.MANAGEDELEMENT", child.getParentClassName());
    Assert.assertEquals(1, child.getMetaComponentRelations().size());
    Assert.assertEquals(1, child.getMetaClassRelations().size());
  }

  @Test
  public void testParentMetaClassAttribute() {
    MetaClass parent = metaClassManager.getMetaClass("com.gxlu.ngrm.ne.MANAGEDELEMENT");
    Assert.assertEquals(16, parent.getMetaAttributes().size());
    Assert.assertEquals(0, parent.getMetaComponentRelations().size());
    Assert.assertEquals(1, parent.getMetaClassRelations().size());
  }

  @Test
  public void testParentDescriptor() {
    MetaClass parent = metaClassManager.getMetaClass("com.gxlu.ngrm.ne.MANAGEDELEMENT");
    ClassTableDescriptor descriptor = metaClassManager.getClassTableDescriptor(parent);
    Assert.assertEquals("XB_MANAGEDELEMENT", descriptor.getTable());
    Assert.assertEquals("S_XB_MANAGEDELEMENT", descriptor.getSequence());
    Assert.assertEquals("ID", descriptor.getPrimitiveProperty().getColumn());
//    Assert.assertEquals("VERSION", descriptor.getVersionProperty().getColumn());
    Assert.assertEquals(16, descriptor.getMetaProerties().size());
    Assert.assertEquals(0, descriptor.getPlainTableRelations().size());
    Assert.assertEquals(0, descriptor.getRelationComponentTables().size());
    Assert.assertEquals(1, descriptor.getRelationClassTables().size());
  }

  @Test
  public void testChildDescriptor() {
    MetaClass parent = metaClassManager.getMetaClass("com.gxlu.ngrm.ne.BTS");
    ClassTableDescriptor descriptor = metaClassManager.getClassTableDescriptor(parent);
    Assert.assertEquals("XB_MANAGEDELEMENT", descriptor.getTable());
    Assert.assertEquals("S_XB_MANAGEDELEMENT", descriptor.getSequence());
    Assert.assertEquals("ID", descriptor.getPrimitiveProperty().getColumn());
//    Assert.assertEquals("VERSION", descriptor.getVersionProperty().getColumn());
    Assert.assertEquals(16, descriptor.getMetaProerties().size());
    Assert.assertEquals(0, descriptor.getPlainTableRelations().size());
    Assert.assertEquals(1, descriptor.getRelationComponentTables().size());
    Assert.assertEquals(1, descriptor.getRelationClassTables().size());
  }

  private MetaDBManager metaClassManager;
}

