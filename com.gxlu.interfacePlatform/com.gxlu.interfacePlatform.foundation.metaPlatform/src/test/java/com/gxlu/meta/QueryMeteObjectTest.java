/**************************************************************************
 * $RCSfile: QueryMeteObjectTest.java,v $  $Revision: 1.2 $  $Date: 2009/01/04 10:03:34 $
 *
 * $Log: QueryMeteObjectTest.java,v $
 * Revision 1.2  2009/01/04 10:03:34  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/26 05:55:47  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/24 09:30:49  richie
 * *** empty log message ***
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : InsertMeteObjectTest.java
 * Created on : Dec 24, 2008 4:20:47 PM
 * Creator : RichieJin
 */
package com.gxlu.meta;

import org.junit.Assert;
import org.junit.Ignore;

import com.gxlu.meta.cfg.Configuration;


/**
 * <pre>
 * Description : TODO
 * @author RichieJin
 * </pre>
 */
@Ignore
public class QueryMeteObjectTest {
  public QueryMeteObjectTest(String name) throws Exception{
    
  }
  
  public void testSimpleQuery() throws Exception {
    metaDBManager = (new Configuration("conf/meta.cfg.xml")).getMetaDBManager();
    MetaClass bts = metaDBManager.getMetaClass("com.gxlu.ngrm.ne.BTS");
    QueryCriteria criteria = new QueryCriteria(bts);
    MetaObject metaObject = metaDBManager.query(criteria).getList().get(0);
    Assert.assertEquals("/managedElement=TianheWestern1", metaObject.getString("CODE"));
  }
  
  public void testSimpleQueryWithCondition() throws Exception {
    metaDBManager = (new Configuration("conf/meta.cfg.xml")).getMetaDBManager();
    MetaClass bts = metaDBManager.getMetaClass("com.gxlu.ngrm.ne.BTS");
    QueryCriteria criteria = new QueryCriteria(bts);
    criteria.addExpression(QueryExpressionUtil.tslike("CODE", "managedElement"));
    metaDBManager.query(criteria);
  }

  public void testComQuery() throws Exception {
    metaDBManager = (new Configuration("conf/meta.cfg.xml")).getMetaDBManager();
    MetaClass bts = metaDBManager.getMetaClass("com.gxlu.ngrm.ne.BTS");
    QueryCriteria criteria = new QueryCriteria(bts);
    criteria.addExpression(QueryExpressionUtil.tslike("CODE", "managedElement"));
    criteria.addComponentQueryCriteria(bts.getMetaComponentRelations().get(0)).addExpression(QueryExpressionUtil.equals("IDSUFFIX", "dracula"));
    metaDBManager.query(criteria);
    
  }

  private MetaDBManager metaDBManager;
}

