/**************************************************************************
 * $RCSfile: MetaDBManagerImpl.java,v $  $Revision: 1.19 $  $Date: 2009/12/18 03:06:47 $
 *
 * $Log: MetaDBManagerImpl.java,v $
 * Revision 1.19  2009/12/18 03:06:47  wayne
 * 1、增加实时监听审计功能，根据审计配置信息的增删改动态实现注册与注销。
 * 2、新增实体属性描述，表格展示：EntityDescriptorDropDown可通用。
 * 3、新增实体对象种类，树型展示：MetaEntitySpecDropDown可通用。
 * 注：默认单选，当参数open_mode为1时表示多选。
 * 4、审计配置模块功能优化。
 *
 * Revision 1.18  2009/11/07 03:45:36  richie
 * *** empty log message ***
 *
 * Revision 1.17  2009/09/11 07:55:02  richie
 * *** empty log message ***
 *
 * Revision 1.16  2009/08/20 13:45:51  liuding
 * *** empty log message ***
 *
 * Revision 1.15  2009/08/09 08:06:01  liuding
 * *** empty log message ***
 *
 * Revision 1.14  2009/07/28 06:50:12  liuding
 * *** empty log message ***
 *
 * Revision 1.13  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.12  2009/05/08 05:09:28  liuding
 * *** empty log message ***
 *
 * Revision 1.11  2009/04/01 06:50:45  richie
 * *** empty log message ***
 *
 * Revision 1.10  2009/03/03 06:26:17  richie
 * *** empty log message ***
 *
 * Revision 1.9  2009/02/11 05:43:28  liuding
 * *** empty log message ***
 *
 * Revision 1.8  2009/01/19 07:23:43  liuding
 * *** empty log message ***
 *
 * Revision 1.7  2009/01/05 02:34:47  richie
 * change for support spring configuration
 *
 * Revision 1.6  2009/01/04 01:21:02  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.gxlu.meta.cfg.dbdescriptor.ClassRelationDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ClassTableDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ComponentRelationDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ComponentTableDescriptor;
import com.gxlu.meta.engine.jdbc.SqlExecutor;
import com.gxlu.meta.engine.persister.entity.EntityPersister;
import com.gxlu.meta.exception.ServiceException;

/**
 * @author lethe & K
 */
public class MetaDBManagerImpl implements MetaDBManager,BeanFactoryAware {
	
	private static MetaDBManager instance;
	
	private static BeanFactory beanFacorty;
  
  public MetaDBManagerImpl(){
  }
  
  public void setBeanFactory(BeanFactory beanFacorty) throws BeansException {
	  MetaDBManagerImpl.beanFacorty = beanFacorty;
  }
	
  public static MetaDBManager getInstance(){
	  if(instance == null){
		  //instance = (MetaDBManager) beanFacorty.getBean("metaDBManager");
		  instance = MetaServiceFacade.getMetaDbManager();
	  }
	  return instance; 
  }
  
  public void persist(MetaObject metaObject) throws ServiceException {
		if(metaObject.getPersistType() == MetaObject.PERSIST_TYPE_ADD){
			persister.insert(metaObject);
		}else if(metaObject.getPersistType() == MetaObject.PERSIST_TYPE_UPDATE){
		  persister.update(metaObject);
		}else if(metaObject.getPersistType() == MetaObject.PERSIST_TYPE_DELETE){
			persister.delete(metaObject);
		}
		metaObject.setPersistType(MetaObject.PERSIST_TYPE_NOCHANGE);
	}
  
  public void deleteWithoutCheckRelation(List<MetaObject> metaObjects)
			throws ServiceException {
		if (metaObjects != null && metaObjects.size() > 0) {
			for (MetaObject object : metaObjects) {
				if ( object.getPersistType() == MetaObject.PERSIST_TYPE_DELETE ) {
					persister.deleteWithoutCheckRelation(object);
				}
			}
		}
	}

	public void persist(List<MetaObject> metaObjects) throws ServiceException {
		if(metaObjects!=null && metaObjects.size()>0){
			for (MetaObject object : metaObjects) {
				persist(object);
			}
		}
	}

	public MetaObjectQueryResult query(QueryCriteria queryCriteria) throws ServiceException {
	  return persister.query(queryCriteria);
	}

	public ClassRelationDescriptor getClassRelationDescriptor(MetaClassRelation metaClassRelation) {
    
    return metaClassManager.getClassRelationDescriptor(metaClassRelation);
  }

  public ClassTableDescriptor getClassTableDescriptor(MetaClass metaClass) {

    return metaClassManager.getClassTableDescriptor(metaClass);
  }

  public ComponentRelationDescriptor getComponentRelationDescriptor(MetaComponentRelation metaComponentRelation) {

    return metaClassManager.getComponentRelationDescriptor(metaComponentRelation);
  }

  public ComponentTableDescriptor getComponentTableDescriptor(MetaComponent metaComponent) {

    return metaClassManager.getComponentTableDescriptor(metaComponent);
  }

  public MetaClass getMetaClass(String className) {

    return metaClassManager.getMetaClass(className);
  }

  public List<String> getTotalMetaClassNames() {

    return metaClassManager.getTotalMetaClassNames();
  }

  public Map<String, MetaClass> getTotalMetaClasses() {

    return metaClassManager.getTotalMetaClasses();
  }
  
  public List<MetaClass> getChildMetaClass(String metaClassName){
	  return metaClassManager.getChildMetaClass(metaClassName);
  }

  public void update() throws ServiceException {
    metaClassManager.update();
  }

  public MetaClassManager getMetaClassManager() {
    return metaClassManager;
  }

  public void setMetaClassManager(MetaClassManager metaClassManager) {
    this.metaClassManager = metaClassManager;
  }

  public EntityPersister getPersister() {
    return persister;
  }

  public void setPersister(EntityPersister persister) {
    this.persister = persister;
  }

  public void setSqlExecutor(SqlExecutor sqlExecutor) {
    metaClassManager.setSqlExecutor(sqlExecutor);
  }
  
  public SqlExecutor getSqlExecutor() {

    return metaClassManager.getSqlExecutor();
  }

  private MetaClassManager metaClassManager;
  private EntityPersister persister;



}
