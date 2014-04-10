/**************************************************************************
 * $RCSfile: MetaClassManagerImpl.java,v $  $Revision: 1.11 $  $Date: 2009/07/28 09:45:22 $
 *
 * $Log: MetaClassManagerImpl.java,v $
 * Revision 1.11  2009/07/28 09:45:22  liuding
 * *** empty log message ***
 *
 * Revision 1.10  2009/07/28 06:50:12  liuding
 * *** empty log message ***
 *
 * Revision 1.9  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.8  2009/04/01 06:50:45  richie
 * *** empty log message ***
 *
 * Revision 1.7  2009/03/03 06:26:17  richie
 * *** empty log message ***
 *
 * Revision 1.6  2009/01/05 02:34:47  richie
 * change for support spring configuration
 *
 * Revision 1.5  2008/12/24 07:01:23  richie
 * *** empty log message ***
 *
 * Revision 1.4  2008/12/19 09:40:44  richie
 * *** empty log message ***
 *
 * Revision 1.3  2008/12/18 09:24:50  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/16 05:53:39  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/16 05:51:04  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gxlu.meta.cache.DefaultCache;
import com.gxlu.meta.cache.MetaClassLoader;
import com.gxlu.meta.cfg.dbdescriptor.ClassRelationDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ClassTableDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ComponentRelationDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ComponentTableDescriptor;
import com.gxlu.meta.engine.jdbc.SqlExecutor;
import com.gxlu.meta.exception.ServiceException;

/**
 * @author K
 */
public class MetaClassManagerImpl implements MetaClassManager {

  public MetaClass getMetaClass(String className) {

    return metaClassCache.get(className);
  }

  public List<String> getTotalMetaClassNames() {
    List<String> names = new ArrayList<String>();
    for(String name : metaClassCache.keySet()) {
      names.add(name);
    }

    return names;
  }

  public Map<String, MetaClass> getTotalMetaClasses() {

    return metaClassCache;
  }

  /* (non-Javadoc)
   * @see com.gxlu.meta.cfg.dbdescriptor.DescriptorFactory#getClassRelationDescriptor(com.gxlu.meta.MetaClassRelation)
   */
  public ClassRelationDescriptor getClassRelationDescriptor(MetaClassRelation metaClassRelation) {

    return classRelationDescriptorCache.get(metaClassRelation);
  }

  /* (non-Javadoc)
   * @see com.gxlu.meta.cfg.dbdescriptor.DescriptorFactory#getClassTableDescriptor(com.gxlu.meta.MetaClass)
   */
  public ClassTableDescriptor getClassTableDescriptor(MetaClass metaClass) {

    return metaClassMixedCache.get(metaClass);
  }

  /* (non-Javadoc)
   * @see com.gxlu.meta.cfg.dbdescriptor.DescriptorFactory#getComponentRelationDescriptor(com.gxlu.meta.MetaComponentRelation)
   */
  public ComponentRelationDescriptor getComponentRelationDescriptor(MetaComponentRelation metaComponentRelation) {

    return componentRelationDescriptorCache.get(metaComponentRelation);
  }

  /* (non-Javadoc)
   * @see com.gxlu.meta.cfg.dbdescriptor.DescriptorFactory#getComponentTableDescriptor(com.gxlu.meta.MetaComponent)
   */
  public ComponentTableDescriptor getComponentTableDescriptor(MetaComponent metaComponent) {

    return componentTableDescriptorCache.get(metaComponent);
  }

  public void update() throws ServiceException {
    metaClassMixedCache = metaClassLoader.reload();
    assembleMetaClassCache();
  }

  public void setSqlExecutor(SqlExecutor sqlExecutor) {
    this.sqlExecutor = sqlExecutor;
    try {
      initalHandlers();
    }
    catch(ServiceException exception) {
      throw new RuntimeException(exception);
    }
    
    assembleMetaClassCache();
  }

  private void initalHandlers() throws ServiceException {
    metaClassLoader = new MetaClassLoader(sqlExecutor);
    metaClassMixedCache = metaClassLoader.reload();
  }

  private void assembleMetaClassCache() {
	classChildren = metaClassLoader.getClassChildren();
    metaClassCache = new DefaultCache<String, MetaClass>();
    componentTableDescriptorCache = new DefaultCache<MetaComponent, ComponentTableDescriptor>();
    componentRelationDescriptorCache = new DefaultCache<MetaComponentRelation, ComponentRelationDescriptor>();
    classRelationDescriptorCache = new DefaultCache<MetaClassRelation, ClassRelationDescriptor>();

    for(MetaClass metaClass : metaClassMixedCache.keySet()) {
      metaClassCache.put(metaClass.getClassName(), metaClass);
      ClassTableDescriptor descriptor = metaClassMixedCache.get(metaClass);
      List<ComponentRelationDescriptor> relationComponentTables = descriptor.getRelationComponentTables();
      if (relationComponentTables != null) {
        for(ComponentRelationDescriptor crd : relationComponentTables) {
          componentRelationDescriptorCache.put(crd.getMetaComponentRelation(), crd);
          componentTableDescriptorCache.put(crd.getMetaComponentRelation().getRelationMetaComponent(), (ComponentTableDescriptor)crd.getOppositeTable());
        }
      }

      List<ClassRelationDescriptor> relationClassTables = descriptor.getRelationClassTables();
      if (relationClassTables != null) {
        for(ClassRelationDescriptor crd : relationClassTables) {
          classRelationDescriptorCache.put(crd.getMetaClassRelation(), crd);
        }
      }
    }
  }
  
  public List<MetaClass> getChildMetaClass(String metaClassName){
	  if(metaClassName == null || "".equals(metaClassName)){
			return null;
	  }
	  List<MetaClass> ret = new ArrayList<MetaClass>(); 
	  List<MetaClass> temp = classChildren.get(metaClassName);
	  if(temp!=null){
		  ret.addAll(temp);
	  }
	  if(ret.size()>0){
		  return ret;
	  }else{
		  return null;
	  }
  }

  public SqlExecutor getSqlExecutor() {

    return sqlExecutor;
  }
  private Cachable<String,List<MetaClass>> classChildren;
  private SqlExecutor sqlExecutor;
  private MetaClassLoader metaClassLoader;
  private Cachable<MetaClass, ClassTableDescriptor> metaClassMixedCache;
  private Cachable<String, MetaClass> metaClassCache;
  private Cachable<MetaComponent, ComponentTableDescriptor> componentTableDescriptorCache;
  private Cachable<MetaComponentRelation, ComponentRelationDescriptor> componentRelationDescriptorCache;
  private Cachable<MetaClassRelation, ClassRelationDescriptor> classRelationDescriptorCache;

}
