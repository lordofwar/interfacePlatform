/**************************************************************************
 * $RCSfile: MetaDBManager.java,v $  $Revision: 1.10 $  $Date: 2009/08/20 13:45:51 $
 *
 * $Log: MetaDBManager.java,v $
 * Revision 1.10  2009/08/20 13:45:51  liuding
 * *** empty log message ***
 *
 * Revision 1.9  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.8  2009/03/03 06:26:17  richie
 * *** empty log message ***
 *
 * Revision 1.7  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.6  2009/01/05 02:34:47  richie
 * change for support spring configuration
 *
 * Revision 1.5  2009/01/04 01:21:01  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta;

import java.util.List;

import com.gxlu.meta.engine.persister.entity.EntityPersister;
import com.gxlu.meta.exception.ServiceException;

/**
 * @author lethe & K
 */
public interface MetaDBManager extends MetaClassManager {

	/**
	 * Common persistent methods for a single MetaObject.
	 * Support:
	 * (1)Create, when metaObject.getPersistType() == PERSIST_TYPE_ADD then insert metaObject to database<br>
	 * (2)Update, when metaObject.getPersistType() == PERSIST_TYPE_UPDATE then update metaObject to database<br>
	 * (3)Delete, when metaObject.getPersistType() == PERSIST_TYPE_DELETE then delete metaObject to database<br>
	 * PersistType need to be set by method user
	 * @throws MetaPlatformException 
	 * @throws ServiceException 
	 */
	public void persist(MetaObject metaObject) throws ServiceException;

	 /**
   * Common persistent methods for a collection of MetaObject.Support:<br>
   * (1)Create, when metaObject.getPersistType() == PERSIST_TYPE_ADD then insert metaObject to database
   * <pre>
   *Sample:
   *      
   *     MetaClass bts = metaDBManager.getMetaClass("com.gxlu.ngrm.ne.BTS");
   *     MetaObject object = new DefaultMetaObject(bts);
   *     object.setValue("CODE", "TESTCODE1");
   *     ...
   *     object.setPersistType(MetaObject.PERSIST_TYPE_ADD);
   *     metaDBManager.persist(object);
   *     
   * </pre>
   * (2)Update, when metaObject.getPersistType() == PERSIST_TYPE_UPDATE then update metaObject to database <br>
   * <pre>
   * Sample:
   * 
   *     MetaClass bts = metaDBManager.getMetaClass("com.gxlu.ngrm.ne.BTS");
   *     QueryCriteria criteria = new QueryCriteria(bts);
   *     criteria.addExpression(QueryExpressionUtil.equal("ID", 1));
   *     criteria.addChildrenFetch("NETOLOCATION");
   *     MetaObjectQueryResult result = metaDBManager.query(criteria);
   *     MetaObject btsNoOne = result.getList().get(0);
   *     
   *     //Change attribute value.
   *     btsNoOne.setValue("CODE", "NO1");
   *     
   *     //remove relationship
   *     MetaClassRelation metaClassRelation = metaClass.findMetaClassRelation("NETOLOCATION");
   *     btsNoOne.setRelateObject(null, metaClassRelation);
   *     btsNoOne.setPersistType(MetaObject.PERSIST_TYPE_UPDATE);
   *     metaDBManager.persist(btsNoOne);
   * </pre>
   * 
   * (3)Delete, when metaObject.getPersistType() == PERSIST_TYPE_DELETE then delete metaObject to database<br>
   *    * <pre>
   * Sample:
   * 
   *     MetaClass bts = metaDBManager.getMetaClass("com.gxlu.ngrm.ne.BTS");
   *     QueryCriteria criteria = new QueryCriteria(bts);
   *     criteria.addExpression(QueryExpressionUtil.equal("ID", 1));
   *     criteria.addChildrenFetch("NETOLOCATION");
   *     MetaObjectQueryResult result = metaDBManager.query(criteria);
   *     MetaObject btsNoOne = result.getList().get(0);
   *
   *     btsNoOne.setPersistType(MetaObject.PERSIST_TYPE_DELETE);
   *     metaDBManager.persist(btsNoOne);
   * </pre>
   * PersistType need to be set by method user
   * @throws MetaPlatformException 
   */
	public void persist(List<MetaObject> metaObjects) throws ServiceException;

	/**
	 * Execute query by giving QueryCriteria.
	 * <pre>
   * Sample:
   * 
   *     MetaClass bts = metaDBManager.getMetaClass("com.gxlu.ngrm.ne.BTS");
   *     QueryCriteria criteria = new QueryCriteria(bts);
   *     criteria.addExpression(QueryExpressionUtil.equal("ID", 1));
   *     criteria.addChildrenFetch("NETOLOCATION");
   *     MetaObjectQueryResult result = metaDBManager.query(criteria);
   * </pre>
   * @return list of result.
   */
	public MetaObjectQueryResult query(QueryCriteria queryCriteria) throws ServiceException;
	
	public void deleteWithoutCheckRelation(List<MetaObject> metaObjects) throws ServiceException;
	
	public void setMetaClassManager(MetaClassManager metaClassManager);
	
	public void setPersister(EntityPersister persister);
}
