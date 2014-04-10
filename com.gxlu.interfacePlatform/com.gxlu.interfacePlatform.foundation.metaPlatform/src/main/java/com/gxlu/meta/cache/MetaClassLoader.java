/**************************************************************************
 * $RCSfile: MetaClassLoader.java,v $  $Revision: 1.36 $  $Date: 2010/07/04 16:41:07 $
 *
 * $Log: MetaClassLoader.java,v $
 * Revision 1.36  2010/07/04 16:41:07  liuding
 * *** empty log message ***
 *
 * Revision 1.35  2010/06/04 09:09:39  liuding
 * *** empty log message ***
 *
 * Revision 1.34  2010/05/08 07:02:33  liuding
 * *** empty log message ***
 *
 * Revision 1.33  2010/03/17 09:11:00  liuding
 * *** empty log message ***
 *
 * Revision 1.32  2010/01/27 03:09:31  liuding
 * *** empty log message ***
 *
 * Revision 1.31  2010/01/05 06:02:15  hongbow
 * MR#:telant471-8 ENTITYSPECID-->CASCADEDELETE
 *
 * Revision 1.30  2009/11/07 03:45:36  richie
 * *** empty log message ***
 *
 * Revision 1.29  2009/09/02 20:58:49  liuding
 * *** empty log message ***
 *
 * Revision 1.28  2009/08/18 08:06:01  richie
 * *** empty log message ***
 *
 * Revision 1.27  2009/08/10 09:14:34  liuding
 * *** empty log message ***
 *
 * Revision 1.26  2009/08/09 08:06:01  liuding
 * *** empty log message ***
 *
 * Revision 1.25  2009/07/29 08:20:56  richie
 * *** empty log message ***
 *
 * Revision 1.24  2009/07/28 06:50:12  liuding
 * *** empty log message ***
 *
 * Revision 1.23  2009/07/28 03:05:38  richie
 * *** empty log message ***
 *
 * Revision 1.22  2009/07/21 09:37:44  liuding
 * *** empty log message ***
 *
 * Revision 1.21  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.20  2009/05/13 12:07:30  liuding
 * *** empty log message ***
 *
 * Revision 1.19  2009/04/27 02:47:08  richie
 * *** empty log message ***
 *
 * Revision 1.18  2009/04/15 03:51:25  liuq
 * *** empty log message ***
 *
 * Revision 1.17  2009/04/15 02:23:24  richie
 * *** empty log message ***
 *
 * Revision 1.16  2009/03/23 01:31:01  richie
 * *** empty log message ***
 *
 * Revision 1.15  2009/03/09 10:37:00  richie
 * *** empty log message ***
 *
 * Revision 1.14  2009/03/09 07:19:11  shenwq
 * *** empty log message ***
 *
 * Revision 1.13  2009/03/06 07:04:38  richie
 * *** empty log message ***
 *
 * Revision 1.12  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.11  2009/02/27 02:02:00  richie
 * *** empty log message ***
 *
 * Revision 1.10  2009/02/26 06:16:45  richie
 * *** empty log message ***
 *
 * Revision 1.9  2009/02/10 03:22:26  liuding
 * *** empty log message ***
 *
 * Revision 1.8  2009/02/01 05:29:49  richie
 * *** empty log message ***
 *
 * Revision 1.7  2009/02/01 01:35:51  richie
 * *** empty log message ***
 *
 * Revision 1.6  2008/12/31 04:44:42  richie
 * *** empty log message ***
 *
 * Revision 1.5  2008/12/26 05:55:25  richie
 * *** empty log message ***
 *
 * Revision 1.4  2008/12/24 02:31:15  richie
 * *** empty log message ***
 *
 * Revision 1.3  2008/12/22 09:10:04  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/19 09:40:07  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/18 09:23:52  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.cache;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gxlu.meta.Cachable;
import com.gxlu.meta.MetaAttribute;
import com.gxlu.meta.MetaClass;
import com.gxlu.meta.MetaClassRelation;
import com.gxlu.meta.MetaComponent;
import com.gxlu.meta.MetaComponentRelation;
import com.gxlu.meta.cfg.DefaultMetaAttribute;
import com.gxlu.meta.cfg.DefaultMetaClass;
import com.gxlu.meta.cfg.DefaultMetaClassRelation;
import com.gxlu.meta.cfg.DefaultMetaComponent;
import com.gxlu.meta.cfg.DefaultMetaComponentRelation;
import com.gxlu.meta.cfg.dbdescriptor.ClassRelationDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ClassTableDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ComponentRelationDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ComponentTableDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.PropertyDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.RelationDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.TableDescriptor;
import com.gxlu.meta.engine.jdbc.SqlExecutor;
import com.gxlu.meta.engine.jdbc.SqlExecutorException;
import com.gxlu.meta.tools.MetaConsts;

/**
 * @author K
 */
public class MetaClassLoader extends AbstractCacheLoader<MetaClass, ClassTableDescriptor> {

  public MetaClassLoader(SqlExecutor sqlExecutor) {
    componentCache = new DefaultCache<String, DefaultMetaComponent>();
    classCache = new DefaultCache<String, DefaultMetaClass>();
    classChildren = new DefaultCache<String,List<MetaClass>>();
    componentTableDescriptorCache = new DefaultCache<MetaComponent, ComponentTableDescriptor>();
    setSqlExecutor(sqlExecutor);
    metaClassOperationSqlCacheLoader = new MetaClassOperationSqlCacheLoader();
    metaClassOperationSqlCacheLoader.setSqlExecutor(getSqlExecutor());
  }

  @Override
  public Cachable<MetaClass, ClassTableDescriptor> reload() throws SqlExecutorException {
    Cachable <MetaClass, ClassTableDescriptor> cache = new DefaultCache<MetaClass, ClassTableDescriptor>();
    Cachable<String, String> sqls = metaClassOperationSqlCacheLoader.reload();
    totals = getTotalMetaClassNames(sqls);
    //清空缓存
    entitySpecCache = null;
    parentEntitySpecCache = null;
    queryCoreDefineCache = null;
    relationCache = null;
    for (String name : totals) {
      DefaultMetaClass metaClass = new DefaultMetaClass();
      ClassTableDescriptor tableDescriptor = new ClassTableDescriptor();
      makeup(name, metaClass, tableDescriptor, sqls);
      cache.put(metaClass, tableDescriptor);
      classCache.put(name, metaClass);
    }

    handleRelation(cache, sqls);
    handleInherit(cache, sqls);
    
    
    //性能测试并发 发现的问题
    Collection<DefaultMetaClass> collection = ((Map)classCache).values();
    for (DefaultMetaClass metaClass : collection ) {
    	metaClass.findMetaAttribute("");
    	metaClass.findMetaClassRelation("");
    	metaClass.findMetaComponentRelation("");
	}
    
    return cache;
  }

  private void handleInherit(Cachable<MetaClass, ClassTableDescriptor> cache, Cachable<String, String> sqls) throws SqlExecutorException {
    Collection<DefaultMetaClass> metaClasses = classCache.values();
    for (String selfClassName : totals) {
//    for (DefaultMetaClass metaClass : classCache.values()) {
      DefaultMetaClass metaClass = classCache.get(selfClassName);
      String parentClassName = findParentClassName(metaClass, sqls);
      if (parentClassName != null) {
        MetaClass parentMetaClass = classCache.get(parentClassName);
        ((DefaultMetaClass) parentMetaClass).addChild(metaClass);
        metaClass.setParentClassName(parentClassName);
        metaClass.setParentMetaClass(parentMetaClass);
        //==============add classChildren cache
        List<MetaClass> childrenList = classChildren.get(parentClassName);
        if(childrenList==null){
          childrenList = new ArrayList<MetaClass>();
          classChildren.put(parentClassName, childrenList);
        }
        childrenList.add(metaClass);
        //==============add classChildren cache
        
        
        metaClass.getMetaAttributes().addAll(parentMetaClass.getMetaAttributes());
//        metaClass.getMetaClassRelations().addAll(parentMetaClass.getMetaClassRelations());
        if(parentMetaClass.getMetaClassRelations()!=null){
        	for (MetaClassRelation pMr : parentMetaClass.getMetaClassRelations()) {
        		boolean isHave = false;
        		for (MetaClassRelation sMr : metaClass.getMetaClassRelations()) {
					if(sMr.getAlias().equals(pMr.getAlias())){
						isHave = true;
						break;
					}
				}
        		if(!isHave){
        			metaClass.getMetaClassRelations().add(pMr);
        		}
				
			}
        }
        
        metaClass.getMetaComponentRelations().addAll(parentMetaClass.getMetaComponentRelations());
        ClassTableDescriptor selfDescriptor = cache.get(metaClass);
        ClassTableDescriptor parentDescriptor = cache.get(parentMetaClass);
        if (parentDescriptor.getTable().equals(selfDescriptor.getTable())) {
          selfDescriptor.getMetaProerties().addAll(parentDescriptor.getMetaProerties());
          selfDescriptor.getPlainTableRelations().addAll(parentDescriptor.getPlainTableRelations());
          selfDescriptor.getRelationClassTables().addAll(parentDescriptor.getRelationClassTables());
          selfDescriptor.getRelationComponentTables().addAll(parentDescriptor.getRelationComponentTables());
          selfDescriptor.setPrimitiveProperty(parentDescriptor.getPrimitiveProperty());
          if(parentDescriptor.getPrimitiveProperty()!=null){
            metaClass.setPrimitiveAttribute(parentDescriptor.getPrimitiveProperty().getMetaAttribute());
          }
          selfDescriptor.setVersionProperty(parentDescriptor.getVersionProperty());
        }
        else {
          //TODO how to handler different base table.
        }
      }
    }
  }

  private String findParentClassName(DefaultMetaClass metaClass, Cachable<String, String> sqls) throws SqlExecutorException {
    
//    String result = null;
//    Object[] params = {metaClass.getClassName()};
//    String sql = sqls.get(MetaConsts.METACLASSLOAD_SQL_OPERATION_EXISTPARENTCLASSNAME);
//
//    if (getSqlExecutor().queryForInt(sql, params) > 0) {
//      sql = sqls.get(MetaConsts.METACLASSLOAD_SQL_OPERATION_QUERYPARENTCLASSNAME);
//      result = getSqlExecutor().queryForString(sql, params);
//    }
    


    if (this.parentEntitySpecCache == null) {
          parentEntitySpecCache = new HashMap<String, String>();
       
          String sql = "SELECT T1.CLASSNAME PARENTCLASSNAME, T0.CLASSNAME CHILDCLASSNAME FROM XM_ENTITYSPEC T0, XM_ENTITYSPEC T1 WHERE T0.PARENTID = T1.ID";
          List<Map<String,Object>> maps = getSqlExecutor().query(sql, null);
      
          for (Map<String,Object> map : maps) {
            String className = map.get("CHILDCLASSNAME").toString();
            parentEntitySpecCache.put(className, map.get("PARENTCLASSNAME").toString());
          }
        }
    return parentEntitySpecCache.get(metaClass.getClassName());
  }

  private void handleRelation(Cachable <MetaClass, ClassTableDescriptor> cache, Cachable<String, String> sqls) throws SqlExecutorException {
    Object[] relatedSpecParams = {MetaConsts.VALUE_METADATA_STATUS_AVAILABLE};
    String sql = sqls.get(MetaConsts.METACLASSLOAD_SQL_OPERATION_QUERYRELATEENTITYSPEC);
    List<Map<String,Object>> relatedSpecs = getSqlExecutor().query(sql, relatedSpecParams);

    for (Map<String,Object> relatedSpec : relatedSpecs) {
      String classOwner = (String) relatedSpec.get("OWNER");
      String classSupplie = (String) relatedSpec.get("SUPPLIE");
      String name = (String) relatedSpec.get("NAME");
      String code = (String) relatedSpec.get("CODE");
      String ownerAtt = (String) relatedSpec.get("OWNERATTR");
      String supplieAtt = (String) relatedSpec.get("SUPPLIERATTR");
      Integer multiple = ((BigDecimal) relatedSpec.get("MULTIPLE")).intValue();
      String tableName = (String) relatedSpec.get("RELATIONINSTANCETABLENAME");
      Object ownerId = relatedSpec.get("OWNERENTITYSPECID");
      Object supplieId = relatedSpec.get("SUPPLIERENTITYSPECID");
      Object relationId = relatedSpec.get("ID");
     
      handleOwnerRelation(cache, sqls, relatedSpec, classOwner, classSupplie, ownerAtt, supplieAtt, multiple, tableName, ownerId, relationId);
      handleSupplierRelation(cache, sqls, relatedSpec, classOwner, classSupplie, ownerAtt, supplieAtt, multiple, tableName, supplieId, relationId);
    }
  }

  private void handleSupplierRelation(Cachable<MetaClass, ClassTableDescriptor> cache, Cachable<String, String> sqls, Map<String, Object> relatedSpec, String classOwner, String classSupplie, String ownerAtt, String supplieAtt, Integer multiple, String tableName, Object supplieId, Object relationId) throws SqlExecutorException {
    DefaultMetaClassRelation relation = null;
    DefaultMetaClass metaClassA = classCache.get(classSupplie);
    ClassTableDescriptor descriptor = cache.get(metaClassA);
    ClassRelationDescriptor relationDescriptor = new ClassRelationDescriptor();
    DefaultMetaClass metaClassZ = classCache.get(classOwner);
    ClassTableDescriptor descriptorZ = cache.get(metaClassZ);
    

    if (relationDefined(relationId, supplieId, sqls)) {
      relation = addRelationToMetaClass(metaClassA, metaClassZ, relatedSpec);
      relation.setCascade(getCascade(relationId, supplieId, sqls));
    }
    else {
      relation = addRelationToDefaultMetaClass(metaClassA, metaClassZ, relatedSpec);
    }
    
    if (metaClassA == metaClassZ) {
      relation.setCode((String) relatedSpec.get("CODE") + "_Z");
      relation.setAlias((String) relatedSpec.get("CODE") + "_Z");
    }

    addRelationToClassTableDescriptor(cache, supplieAtt, ownerAtt, tableName, descriptor, relationDescriptor, metaClassZ, descriptorZ, relation);
    
    if (multiple == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_ONE2N) {
      multiple = MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_N2ONE;
    }
    else if (multiple == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_N2ONE) {
      multiple = MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_ONE2N;
    }

    relation.setRelationType(multiple);
  }

  private void handleOwnerRelation(Cachable<MetaClass, ClassTableDescriptor> cache, Cachable<String, String> sqls, Map<String, Object> relatedSpec, String classOwner, String classSupplie, String ownerAtt, String supplieAtt, Integer multiple, String tableName, Object ownerId, Object relationId) throws SqlExecutorException {
    DefaultMetaClass metaClassA = classCache.get(classOwner);
    ClassTableDescriptor descriptor = cache.get(metaClassA);
    ClassRelationDescriptor relationDescriptor = new ClassRelationDescriptor();
    DefaultMetaClass metaClassZ = classCache.get(classSupplie);
    ClassTableDescriptor descriptorZ = cache.get(metaClassZ);
    
    DefaultMetaClassRelation relation = null;
    if (relationDefined(relationId, ownerId, sqls)) {
      relation = addRelationToMetaClass(metaClassA, metaClassZ, relatedSpec);
      
      relation.setCascade(getCascade(relationId, ownerId, sqls));
    }
    else {
      relation = addRelationToDefaultMetaClass(metaClassA, metaClassZ, relatedSpec);
    }
    addRelationToClassTableDescriptor(cache, ownerAtt, supplieAtt, tableName, descriptor, relationDescriptor, metaClassZ, descriptorZ, relation);
    relation.setRelationType(multiple);
   
  }

  private boolean relationDefined(Object relationId, Object ownerId, Cachable<String, String> sqls) throws SqlExecutorException {
//    boolean result = false;
//    String sql = sqls.get(MetaConsts.METACLASSLOAD_SQL_OPERATION_EXISTRELATIONDEFINED);
//    Object[] params = {ownerId, relationId, MetaConsts.VALUE_METADATA_STATUS_AVAILABLE};
//    if (getSqlExecutor().queryForInt(sql, params) > 0) {
//      result = true;
//    }
//
//    return result;
    
    if (this.relationCache == null) {
      relationCache = new HashMap<String, Integer>();
     
      String sql = "SELECT T0.ENTITYSPECID, T1.ID RELATIONSPECID, T0.CASCADEDELETE FROM XM_ENTITYSPEC2RELATIONSPEC T0, XM_RELATIONSPEC T1, XM_ENTITYSPEC T2 WHERE T1.ID = T0.RELATIONSPECID AND T0.STATUS = ? AND T2.ID = T0.ENTITYSPECID";
      Object[] params = {MetaConsts.VALUE_METADATA_STATUS_AVAILABLE};
      List<Map<String,Object>> maps = getSqlExecutor().query(sql, params);
  
      for (Map<String,Object> map : maps) {
        int isCascadeDelete = MetaConsts.VALUE_METADATA_BOOLEAN_FALSE;
        if (map.get("CASCADEDELETE") != null) {
         // isCascadeDelete = Integer.parseInt(map.get("ENTITYSPECID").toString());
          isCascadeDelete = Integer.parseInt(map.get("CASCADEDELETE").toString());
        }
        relationCache.put(map.get("RELATIONSPECID").toString() + "_" + map.get("ENTITYSPECID").toString(), isCascadeDelete);
      }
    }

    return relationCache.get(relationId + "_" + ownerId) != null ? true : false;
  }

  private int getCascade(Object relationId, Object ownerId, Cachable<String, String> sqls) throws SqlExecutorException {
//    String sql = sqls.get(MetaConsts.METACLASSLOAD_SQL_OPERATION_EXISTRELATIONDEFINED);
//    sql = sql.replace("COUNT(T0.ID)","T0.CASCADEDELETE");
//    sdf
//    Object[] params = {ownerId, relationId, MetaConsts.VALUE_METADATA_STATUS_AVAILABLE};
//
//    return getSqlExecutor().queryForInt(sql, params);
    if (relationCache == null) {
      relationDefined(relationId,ownerId, sqls);
    }
    return relationCache.get(relationId + "_" + ownerId);
  }

  private void addRelationToClassTableDescriptor(Cachable<MetaClass, ClassTableDescriptor> cache, String attA, String attZ, String tableName, ClassTableDescriptor descriptor, ClassRelationDescriptor relationDescriptor, DefaultMetaClass metaClassZ, ClassTableDescriptor descriptorZ, DefaultMetaClassRelation relation) {
    boolean isIndependentTable = true;
    String ownerTableName = descriptor.getTable();
    String supplieTableName = descriptorZ.getTable();
    if (tableName.equals(ownerTableName) || tableName.equals(supplieTableName)){
      isIndependentTable = false;
    }
    for (RelationDescriptor plain : descriptor.getPlainTableRelations()) {
      if (plain.getOppositeTable() != null && tableName.equals(plain.getOppositeTable().getTable())) {
        isIndependentTable = false;
      }
    }
    for (RelationDescriptor plain : descriptorZ.getPlainTableRelations()) {
      if (plain.getOppositeTable() != null && tableName.equals(plain.getOppositeTable().getTable())) {
        isIndependentTable = false;
      }
    }
      
    relationDescriptor.setIndependentTable(isIndependentTable);
    relationDescriptor.setJoinTableName(tableName);
    relationDescriptor.setShelfColumn(attA);
    relationDescriptor.setOppositeColumn(attZ);
    relationDescriptor.setOppositeTable(cache.get(metaClassZ));
    relationDescriptor.setMetaClassRelation(relation);
    descriptor.getRelationClassTables().add(relationDescriptor);
  }

  
  
  private DefaultMetaClassRelation addRelationToDefaultMetaClass(DefaultMetaClass metaClassA, DefaultMetaClass metaClassZ, Map<String,Object> relatedSpec) {
    DefaultMetaClassRelation relation = new DefaultMetaClassRelation();
    relation.setRelationMetaClass(metaClassZ);
    relation.setOwnerMetaClass(metaClassA);
    relation.setName((String) relatedSpec.get("NAME"));
    relation.setCode((String) relatedSpec.get("CODE"));
    relation.setAlias((String) relatedSpec.get("CODE"));

    
    relation.setId(((BigDecimal) relatedSpec.get("ID")).longValue());
//    relation.setVersionInstanceId(((BigDecimal) relatedSpec.get("VERSIONINSTANCE")).longValue());
    ((DefaultMetaClass) metaClassA).getMetaHiddenClassRelations().add(relation);
    metaClassA.getMetaClassRelations().add(relation);
    return relation;
  }

  private DefaultMetaClassRelation addRelationToMetaClass(DefaultMetaClass metaClassA, DefaultMetaClass metaClassZ, Map<String,Object> relatedSpec) {
    DefaultMetaClassRelation relation = new DefaultMetaClassRelation();
    relation.setRelationMetaClass(metaClassZ);
    relation.setOwnerMetaClass(metaClassA);
    relation.setName((String) relatedSpec.get("NAME"));
    relation.setCode((String) relatedSpec.get("CODE"));
    relation.setAlias((String) relatedSpec.get("CODE"));

    relation.setId(((BigDecimal) relatedSpec.get("ID")).longValue());
//    relation.setVersionInstanceId(((BigDecimal) relatedSpec.get("VERSIONINSTANCE")).longValue());
    metaClassA.getMetaClassRelations().add(relation);
    return relation;
  }

  private void makeup(String name, DefaultMetaClass metaClass, ClassTableDescriptor classTableDescriptor, Cachable<String, String> sqls) throws SqlExecutorException {
    

    initialList(metaClass, classTableDescriptor);

    Map<String, Object> entitySpec = queryEntitySpec(name, sqls);
    Object entitySpecId = entitySpec.get("ID");
    
    List<Map<String, Object>> coreTableDefine = queryBaseEntityDefine(sqls, entitySpecId);
    
    List<Map<String, Object>> sateTableDefine = querySatelliteTableDefine(sqls, entitySpecId);
    assembleMetaClassBaseInfomation(metaClass, entitySpec);
    assembleTableDescriptorBaseInfomation(classTableDescriptor, entitySpec);
    assembleTableProperties(classTableDescriptor, entitySpec, coreTableDefine, metaClass.getMetaAttributes(),(String) entitySpec.get("CORETABLENAME"), MetaConsts.VALUE_METADATA_MAINTAININDICATOR_SYSTEM);
    if(classTableDescriptor.getPrimitiveProperty()!=null){
      metaClass.setPrimitiveAttribute(classTableDescriptor.getPrimitiveProperty().getMetaAttribute());
    }
    
    if (entitySpec.get("SATELLITETABLENAME") != null && !((String) entitySpec.get("SATELLITETABLENAME")).equals("")) {
      //支持卫星表不含有ENTITYID的情况
      String shelfColumn = "ENTITYID";
      if(sateTableDefine!=null && sateTableDefine.size()>0){
        for (Map<String, Object> map : sateTableDefine) {
          if (((BigDecimal) map.get("ISPK")).intValue() == 1) {
            shelfColumn = (String) map.get("COLUMNNAME");
            break;
            }
      }
      }
      TableDescriptor subTableDescriptor = addEntitySpecTableRelation(classTableDescriptor.getPlainTableRelations(),shelfColumn, entitySpec);
      List<PropertyDescriptor> metaProerties = new ArrayList<PropertyDescriptor>();
      subTableDescriptor.setMetaProerties(metaProerties);
      assembleTableDescriptorSatelliteInfomation(subTableDescriptor, entitySpec);
      assembleTableProperties(subTableDescriptor, entitySpec, sateTableDefine, metaClass.getMetaAttributes(), (String) entitySpec.get("SATELLITETABLENAME"), MetaConsts.VALUE_METADATA_MAINTAININDICATOR_USER);
    }


    List<Map<String, Object>> componentSpecs = queryComponentSpecByEntitySpecId(sqls, entitySpecId);
    
    if (componentSpecs != null) {
      for (Map<String,Object> componentSpec : componentSpecs) {
        DefaultMetaComponent metaComponent = assembleComponentBaseInformation(componentSpec);
        DefaultMetaComponentRelation metaComponentRelation = addComponentRelation(metaComponent);
        metaComponentRelation.setAlias(metaComponent.getCode());
        metaClass.getMetaComponentRelations().add(metaComponentRelation);
        if (metaComponent.getMetaAttributes() == null) {
          List<MetaAttribute> componentAttributes = new ArrayList<MetaAttribute>();
          metaComponent.setMetaAttributes(componentAttributes);
        }
        ComponentTableDescriptor componentTableDescriptor = addComponentTableRelation(componentSpec, classTableDescriptor.getRelationComponentTables(), metaComponentRelation);
        
        if (componentTableDescriptor.getMetaProerties() == null || componentTableDescriptor.getMetaProerties().size() == 0) {
          List<Map<String, Object>> componentDefine = assembleComponentDefine(sqls, componentSpec); 
          List<PropertyDescriptor> metaProerties = new ArrayList<PropertyDescriptor>();
          componentTableDescriptor.setMetaProerties(metaProerties);
          componentTableDescriptor.setTable((String) componentSpec.get("COMPONENTTABLENAME"));
          componentTableDescriptor.setSequence((String) componentSpec.get("SEQUENCE"));
          assembleTableProperties(componentTableDescriptor, componentSpec, componentDefine, metaComponent.getMetaAttributes(), (String) componentSpec.get("COMPONENTTABLENAME"), MetaConsts.VALUE_METADATA_MAINTAININDICATOR_SYSTEM);
        }
      }
    }
  }

  private void initialList(DefaultMetaClass metaClass, ClassTableDescriptor classTableDescriptor) {
    List<MetaClassRelation> metaClassRelations = new ArrayList<MetaClassRelation>();
    List<MetaClassRelation> metaHiddenClassRelations = new ArrayList<MetaClassRelation>();
    List<MetaComponentRelation> metaComponentRelations = new ArrayList<MetaComponentRelation>();
    List<MetaAttribute> metaAttributes = new ArrayList<MetaAttribute>();
    metaClass.setMetaAttributes(metaAttributes);
    metaClass.setMetaComponentRelations(metaComponentRelations);
    metaClass.setMetaClassRelations(metaClassRelations);
    metaClass.setMetaHiddenClassRelations(metaHiddenClassRelations);
    
    List<PropertyDescriptor> metaProerties = new ArrayList<PropertyDescriptor>();
    List<ComponentRelationDescriptor> relationComponentTables = new ArrayList<ComponentRelationDescriptor>();
    List<ClassRelationDescriptor> classComponentTables = new ArrayList<ClassRelationDescriptor>();
    List<RelationDescriptor> plainTableRelations = new ArrayList<RelationDescriptor>();
    classTableDescriptor.setMetaProerties(metaProerties);
    classTableDescriptor.setPlainTableRelations(plainTableRelations);
    classTableDescriptor.setRelationComponentTables(relationComponentTables);
    classTableDescriptor.setRelationClassTables(classComponentTables);
  }

  private void assembleMetaClassBaseInfomation(DefaultMetaClass metaClass, Map<String, Object> entitySpec) {
    metaClass.setClassName((String) entitySpec.get("CLASSNAME"));
    metaClass.setCode((String) entitySpec.get("CODE"));
    metaClass.setDisplayName((String) entitySpec.get("NAME"));
    metaClass.setId(((BigDecimal) entitySpec.get("ID")).longValue());
//    metaClass.setVersionInstanceId(((BigDecimal) entitySpec.get("VERSIONINSTANCE")).longValue());
  }

  private DefaultMetaComponentRelation addComponentRelation(DefaultMetaComponent metaComponent) {
    DefaultMetaComponentRelation metaComponentRelation = new DefaultMetaComponentRelation();
    metaComponentRelation.setMetaComponent(metaComponent);
    
    return metaComponentRelation;
  }

  private ComponentTableDescriptor addComponentTableRelation(Map<String, Object> entitySpec, List<ComponentRelationDescriptor> componentTableRelations, MetaComponentRelation metaComponentRelation) {
    
    
    ComponentTableDescriptor componentTableDescriptor = componentTableDescriptorCache.get(metaComponentRelation.getRelationMetaComponent());
    
    if (componentTableDescriptor == null) {
      componentTableDescriptor = new ComponentTableDescriptor();
      componentTableDescriptorCache.put(metaComponentRelation.getRelationMetaComponent(), componentTableDescriptor);
    }
    ComponentRelationDescriptor componentRelationDescriptor = new ComponentRelationDescriptor(); 
    componentRelationDescriptor.setIndependentTable(false);
    componentRelationDescriptor.setJoinTableName((String) entitySpec.get("COMPONENTTABLENAME"));
    componentRelationDescriptor.setShelfColumn("ENTITYID");
    componentRelationDescriptor.setOppositeTable(componentTableDescriptor);
    
    componentRelationDescriptor.setOppositeColumn("ID");
    componentRelationDescriptor.setMetaComponentRelation(metaComponentRelation);
    componentTableRelations.add(componentRelationDescriptor);

    return componentTableDescriptor;
  }

  private DefaultMetaComponent assembleComponentBaseInformation(Map<String, Object> componentSpec) {
    DefaultMetaComponent metaComponent = componentCache.get((String) componentSpec.get("CODE"));
    if (metaComponent == null) {
      metaComponent = new DefaultMetaComponent();
      metaComponent.setCode((String) componentSpec.get("CODE"));
      metaComponent.setDisplayName((String) componentSpec.get("NAME"));
      metaComponent.setId(((BigDecimal) componentSpec.get("ID")).longValue());
//      metaComponent.setVersionInstanceId(((BigDecimal) componentSpec.get("VERSIONINSTANCE")).longValue());
      componentCache.put((String) componentSpec.get("CODE"), metaComponent);
    }

    return metaComponent;
  }

  private List<Map<String, Object>> assembleComponentDefine(Cachable<String, String> sqls, Map<String, Object> componentSpec) throws SqlExecutorException {
    String sql;
    Object[] componentDefineParams = {componentSpec.get("ID")};
    sql = sqls.get(MetaConsts.METACLASSLOAD_SQL_OPERATION_QUERYMETACOMPONETDEFINE);
    List<Map<String,Object>> componentDefine = getSqlExecutor().query(sql, componentDefineParams);

    return componentDefine;
  }

  private List<Map<String, Object>> queryComponentSpecByEntitySpecId(Cachable<String, String> sqls, Object entitySpecId) throws SqlExecutorException {
    

    if (this.componentSpecCache == null) {
      componentSpecCache = new HashMap<String, List<Map<String, Object>>>();
   
      String sql = "SELECT T0.*, T1.ENTITYSPECID, T1.COMPONENTSPECALIAS FROM XM_COMPONENTSPEC T0, XM_ENTITYSPEC2COMPONENTSPEC T1, XM_ENTITYSPEC T2 WHERE T1.COMPONENTSPECID = T0.ID AND T0.STATUS = 1 AND T1.STATUS = 1 AND  T2.ID = T1.ENTITYSPECID";
      //Object[] baseEntityDefineParams = {baseEntitySpecId};
      //sql = sqls.get(MetaConsts.METACLASSLOAD_SQL_OPERATION_QUERYCOREDEFINE);
      //List<Map<String,Object>> baseEntityDefine = getSqlExecutor().query(sql, baseEntityDefineParams);
      List<Map<String,Object>> maps = getSqlExecutor().query(sql, null);
  
      for (Map<String,Object> map : maps) {
        String id = map.get("ENTITYSPECID").toString();
        List<Map<String, Object>> list = componentSpecCache.get(id);
        if (list == null) {
          list = new ArrayList<Map<String, Object>>();
          componentSpecCache.put(id, list);
        }
        list.add(map);
      }
    }
    return componentSpecCache.get(entitySpecId.toString());

//    Object[] componentSpecParams = {entitySpecId, MetaConsts.VALUE_METADATA_STATUS_AVAILABLE, MetaConsts.VALUE_METADATA_STATUS_AVAILABLE};
//    String sql = sqls.get(MetaConsts.METACLASSLOAD_SQL_OPERATION_QUERYCOMPONENTPEC);
//    List<Map<String,Object>> componentSpecs = getSqlExecutor().query(sql, componentSpecParams);
//
//    return componentSpecs;
  }

  private void assembleTableDescriptorBaseInfomation(TableDescriptor classTableDescriptor, Map<String, Object> baseEntitySpec) {
    classTableDescriptor.setTable((String) baseEntitySpec.get("CORETABLENAME"));
    classTableDescriptor.setSequence((String) baseEntitySpec.get("CTSEQUENCE"));
  }

  private void assembleTableDescriptorSatelliteInfomation(TableDescriptor classTableDescriptor, Map<String, Object> baseEntitySpec) {
    classTableDescriptor.setTable((String) baseEntitySpec.get("SATELLITETABLENAME"));
    classTableDescriptor.setSequence((String) baseEntitySpec.get("STSEQUENCE"));
  }
  

  private TableDescriptor addEntitySpecTableRelation(List<RelationDescriptor> plainTableRelations,String shelfColumn, Map<String, Object> entitySpec) {
    TableDescriptor subTableDescriptor = new TableDescriptor();
    RelationDescriptor relationDescriptor = new RelationDescriptor();
    relationDescriptor.setIndependentTable(false);
    relationDescriptor.setJoinTableName((String) entitySpec.get("SATELLITETABLENAME"));
    relationDescriptor.setShelfColumn(shelfColumn);
    relationDescriptor.setOppositeTable(subTableDescriptor);
    relationDescriptor.setOppositeColumn("ID");
    plainTableRelations.add(relationDescriptor);

    
    return subTableDescriptor;
  }

  private void assembleTableProperties(TableDescriptor classTableDescriptor, Map<String, Object> entitySpec, List<Map<String, Object>> coreTableDefine, List<MetaAttribute> metaAttributes, String tableName, int dataCategory) {
//    assembleTableDescriptorBaseInfomation(classTableDescriptor, entitySpec);
    List<PropertyDescriptor> metaProerties = classTableDescriptor.getMetaProerties();
    classTableDescriptor.setMetaProerties(metaProerties);
    if (coreTableDefine != null) {
      for (Map<String, Object> baseEntityAttr : coreTableDefine) {
        DefaultMetaAttribute attribute = assembleAttribute(entitySpec, baseEntityAttr, tableName);
        attribute.setDataCategory(dataCategory);
        PropertyDescriptor propertyDescriptor = assemblePropertyDescriptor(baseEntityAttr, attribute);
        metaAttributes.add(attribute);
        metaProerties.add(propertyDescriptor);
        if (baseEntityAttr.get("ISPK") == null) {
          System.out.println("cccc");
        }
        if (baseEntityAttr.get("ISPK") != null && ((BigDecimal) baseEntityAttr.get("ISPK")).intValue() == 1) {
          classTableDescriptor.setPrimitiveProperty(propertyDescriptor);    
        }
        if (baseEntityAttr.get("ISVERSION") != null && ((BigDecimal) baseEntityAttr.get("ISVERSION")).intValue() == 1) {
          classTableDescriptor.setVersionProperty(propertyDescriptor);    
        }
      }
    }
  }

  private PropertyDescriptor assemblePropertyDescriptor(Map<String, Object> baseEntityAttr, DefaultMetaAttribute attribute) {
    PropertyDescriptor propertyDescriptor = new PropertyDescriptor();
    propertyDescriptor.setColumn((String) baseEntityAttr.get("COLUMNNAME"));
    propertyDescriptor.setMetaAttribute(attribute);
    return propertyDescriptor;
  }

  private DefaultMetaAttribute assembleAttribute(Map<String, Object> baseEntitySpec, Map<String, Object> baseEntityAttr, String tableName) {
    DefaultMetaAttribute attribute = new DefaultMetaAttribute();
    attribute.setAttributeName((String) baseEntityAttr.get("ATTRNAME"));
    attribute.setDisplayName((String) baseEntityAttr.get("ATTRDISPNAME"));
    if((baseEntityAttr.get("DATATYPE")!=null))
    attribute.setDataType(((BigDecimal) baseEntityAttr.get("DATATYPE")).intValue());
    attribute.setTableColumnName((String) baseEntityAttr.get("COLUMNNAME"));
    attribute.setId(((BigDecimal) baseEntityAttr.get("ID")).longValue());
    if(baseEntityAttr.get("DICCLASSNAME")!=null){
      attribute.setDictionaryClassID((String)baseEntityAttr.get("DICCLASSNAME"));
    }
    if(baseEntityAttr.get("DICATTRIBUTENAME")!=null){
      attribute.setDictionaryAttributeID((String)baseEntityAttr.get("DICATTRIBUTENAME"));
    }
    attribute.setTableName(tableName);
    return attribute;
  }

  private List<Map<String, Object>> queryBaseEntityDefine(Cachable<String, String> sqls, Object baseEntitySpecId) throws SqlExecutorException {
    
    if (this.queryCoreDefineCache == null) {
      queryCoreDefineCache = new HashMap<String, List<Map<String, Object>>>();
   
      String sql = "SELECT t1.* FROM XM_ENTITYSPEC T0, XM_ENTITYDESCRIPTOR T1 WHERE T0.ID = T1.ENTITYSPECID AND T0.PARENTID IS NULL";
      //Object[] baseEntityDefineParams = {baseEntitySpecId};
      //sql = sqls.get(MetaConsts.METACLASSLOAD_SQL_OPERATION_QUERYCOREDEFINE);
      //List<Map<String,Object>> baseEntityDefine = getSqlExecutor().query(sql, baseEntityDefineParams);
      List<Map<String,Object>> baseEntityDefine = getSqlExecutor().query(sql, null);
  
      for (Map<String,Object> map : baseEntityDefine) {
        String id = map.get("ENTITYSPECID").toString();
        List<Map<String, Object>> list = queryCoreDefineCache.get(id);
        if (list == null) {
          list = new ArrayList<Map<String, Object>>();
          queryCoreDefineCache.put(id, list);
        }
        list.add(map);
      }
    }
    return queryCoreDefineCache.get(baseEntitySpecId.toString());
  }

  private List<Map<String, Object>> querySatelliteTableDefine(Cachable<String, String> sqls, Object entitySpecId) throws SqlExecutorException {
//    Object[] entityDefineParams = {entitySpecId};
//    String sql = sqls.get(MetaConsts.METACLASSLOAD_SQL_OPERATION_QUERYSATELLITEDEFINE);
//    List<Map<String,Object>> entityDefine = getSqlExecutor().query(sql, entityDefineParams);
//
//    return entityDefine;
    
    if (this.querySatelliteDefineCache == null) {
      querySatelliteDefineCache = new HashMap<String, List<Map<String, Object>>>();
   
      String sql = "SELECT t1.* FROM XM_ENTITYSPEC T0, XM_ENTITYDESCRIPTOR T1 WHERE T0.ID = T1.ENTITYSPECID AND T0.PARENTID IS NOT NULL";
      //Object[] baseEntityDefineParams = {baseEntitySpecId};
      //sql = sqls.get(MetaConsts.METACLASSLOAD_SQL_OPERATION_QUERYCOREDEFINE);
      //List<Map<String,Object>> baseEntityDefine = getSqlExecutor().query(sql, baseEntityDefineParams);
      List<Map<String,Object>> baseEntityDefine = getSqlExecutor().query(sql, null);
  
      for (Map<String,Object> map : baseEntityDefine) {
        String id = map.get("ENTITYSPECID").toString();
        List<Map<String, Object>> list = querySatelliteDefineCache.get(id);
        if (list == null) {
          list = new ArrayList<Map<String, Object>>();
          querySatelliteDefineCache.put(id, list);
        }
        list.add(map);
      }
    }
    return querySatelliteDefineCache.get(entitySpecId.toString());
  }

  private Map<String, Object> queryEntitySpec(String name, Cachable<String, String> sqls) throws SqlExecutorException {
    if (entitySpecCache == null) {
      entitySpecCache = new HashMap<String, Map<String, Object>>();
      String sql = "SELECT * FROM XM_ENTITYSPEC ";
      List<Map<String,Object>> maps = getSqlExecutor().query(sql, null);
      for (Map<String,Object> map : maps) {
        String className = map.get("CLASSNAME").toString();
        entitySpecCache.put(className, map);
      }
    }
    
    return entitySpecCache.get(name);

//    Object[] entitySpecParams = {name};
//    String sql = sqls.get(MetaConsts.METACLASSLOAD_SQL_OPERATION_QUERYMETACLASSSPEC);
//    Map<String,Object> entitySpec = getSqlExecutor().queryForMap(sql, entitySpecParams);
//    return entitySpec;
  }

  private List<String> getTotalMetaClassNames(Cachable<String, String> sqls) throws SqlExecutorException {
    Object[] params = {MetaConsts.VALUE_METADATA_STATUS_AVAILABLE};
    List<Map<String,Object>> list = getSqlExecutor().query(sqls.get(MetaConsts.METACLASSLOAD_SQL_OPERATION_QUERYMETACLASSNAMES), params);
    List<String> results = new ArrayList<String>();
    for (Map<String, Object> item : list) {
      results.add((String) item.get("CLASSNAME"));
    }
    return results;
  }

  public Cachable<String, List<MetaClass>> getClassChildren() {
    return classChildren;
  }

  private Cachable<String,List<MetaClass>> classChildren;
  
  private CacheLoader<String, String> metaClassOperationSqlCacheLoader;
  private Cachable<String, DefaultMetaComponent> componentCache;
  private Cachable<String, DefaultMetaClass> classCache;
  private Cachable<MetaComponent, ComponentTableDescriptor> componentTableDescriptorCache;
  private List<String> totals;
  private Map<String, List<Map<String, Object>>> queryCoreDefineCache;
  private Map<String, List<Map<String, Object>>> querySatelliteDefineCache;
  private Map<String, List<Map<String, Object>>> componentSpecCache;
  private Map<String, Map<String, Object>> entitySpecCache;
  private Map<String, String> parentEntitySpecCache;
  private Map<String, Integer> relationCache;
}
