/**************************************************************************
 * $RCSfile: MetaClassManager.java,v $  $Revision: 1.9 $  $Date: 2009/07/28 06:50:12 $
 *
 * $Log: MetaClassManager.java,v $
 * Revision 1.9  2009/07/28 06:50:12  liuding
 * *** empty log message ***
 *
 * Revision 1.8  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.7  2009/04/01 06:50:45  richie
 * *** empty log message ***
 *
 * Revision 1.6  2009/03/03 06:26:17  richie
 * *** empty log message ***
 *
 * Revision 1.5  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.4  2009/01/05 02:34:47  richie
 * change for support spring configuration
 *
 * Revision 1.3  2008/12/24 07:01:23  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/18 09:24:50  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/16 05:51:04  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta;

import java.util.List;
import java.util.Map;

import com.gxlu.meta.cfg.dbdescriptor.ClassRelationDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ClassTableDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ComponentRelationDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ComponentTableDescriptor;
import com.gxlu.meta.engine.jdbc.SqlExecutor;
import com.gxlu.meta.exception.ServiceException;

/**
 * manger to get MetaClass include total MetaClass map, single
 * MetaClass by its unique name and total MetaClass names.
 * Note: implementation should provide a cache to hold all MetaClass
 * to avoid query database frequency.
 *
 * @author K
 */
public interface MetaClassManager {

  /**
   * Total MetaClass and packed in a map.
   *
   * @return total MetaClass and packed in a map.
   */
  public Map<String, MetaClass> getTotalMetaClasses();

  /**
   * MetaClass which unique name is key.
   *
   * @param className MetaObject's unique name.
   * @return MetaClass which unique name is key.
   */
  public MetaClass getMetaClass(String className);

  /**
   * A collection of total MetaClass unique names.
   *
   * @return A collection of total MetaClass unique names.
   */
  public List<String> getTotalMetaClassNames();

  public List<MetaClass> getChildMetaClass(String metaClassName);

  /**
   * Retrieve meta class table description by MetaClass.
   *
   * @param metaClass
   * @return
   */
  public ClassTableDescriptor getClassTableDescriptor(MetaClass metaClass);


  /**
   * Retrieve meta component table description by MetaComponent.
   *
   * @param metaClass
   * @return
   */
  public ComponentTableDescriptor getComponentTableDescriptor(MetaComponent metaComponent);

  /**
   * Retrieve ClassRelationDescriptor by Relationship
   *
   * @param metaClass
   * @return
   */
  public ClassRelationDescriptor getClassRelationDescriptor(MetaClassRelation metaClassRelation);

  /**
   * Retrieve ComponentRelationDescriptor by Relationship
   *
   * @param metaClass
   * @return
   */
  public ComponentRelationDescriptor getComponentRelationDescriptor(MetaComponentRelation metaComponentRelation);

  /**
   * Update cache that held MetaClass Map.  
   * @throws ServiceException 
   */
  public void update() throws ServiceException;

  /**
   * Set sql executor for this manager.
   *
   * @param sqlExecutor
   * @throws ServiceException 
   */
  public void setSqlExecutor(SqlExecutor sqlExecutor);
  
  public SqlExecutor getSqlExecutor();
}
