/**************************************************************************
 * $RCSfile: EntityPersister.java,v $  $Revision: 1.109 $  $Date: 2010/11/11 11:31:44 $
 *
 * $Log: EntityPersister.java,v $
 * Revision 1.109  2010/11/11 11:31:44  richie
 * *** empty log message ***
 *
 * Revision 1.108  2010/10/19 06:41:22  liuding
 * *** empty log message ***
 *
 * Revision 1.107  2010/08/08 06:03:58  richie
 * change interface log
 *
 * Revision 1.106  2010/08/02 14:18:43  liuding
 * *** empty log message ***
 *
 * Revision 1.105  2010/07/02 06:15:49  liuding
 * *** empty log message ***
 *
 * Revision 1.104  2010/06/25 02:18:45  liuding
 * *** empty log message ***
 *
 * Revision 1.103  2010/05/31 13:43:30  liuding
 * *** empty log message ***
 *
 * Revision 1.102  2010/05/20 14:44:50  liuding
 * *** empty log message ***
 *
 * Revision 1.101  2010/05/20 12:23:50  liuding
 * *** empty log message ***
 *
 * Revision 1.100  2010/05/19 11:50:28  liuding
 * *** empty log message ***
 *
 * Revision 1.99  2010/05/18 12:06:46  liuding
 * *** empty log message ***
 *
 * Revision 1.98  2010/05/17 14:03:53  liuding
 * *** empty log message ***
 *
 * Revision 1.97  2010/05/17 13:23:15  liuding
 * *** empty log message ***
 *
 * Revision 1.96  2010/05/16 15:22:24  liuding
 * *** empty log message ***
 *
 * Revision 1.95  2010/05/15 08:51:53  liuding
 * *** empty log message ***
 *
 * Revision 1.94  2010/01/28 02:42:25  liuding
 * *** empty log message ***
 *
 * Revision 1.93  2010/01/21 05:23:58  richie
 * no message
 *
 * Revision 1.92  2010/01/21 05:22:34  richie
 * no message
 *
 * Revision 1.91  2009/12/25 03:39:50  liuding
 * *** empty log message ***
 *
 * Revision 1.90  2009/11/20 06:51:11  richie
 * *** empty log message ***
 *
 * Revision 1.89  2009/11/07 03:45:36  richie
 * *** empty log message ***
 *
 * Revision 1.88  2009/10/17 17:47:00  liuding
 * *** empty log message ***
 *
 * Revision 1.87  2009/10/15 06:11:03  liuding
 * *** empty log message ***
 *
 * Revision 1.86  2009/09/17 09:50:51  liuding
 * *** empty log message ***
 *
 * Revision 1.85  2009/09/17 09:39:22  liuding
 * *** empty log message ***
 *
 * Revision 1.84  2009/09/17 06:06:08  liuding
 * *** empty log message ***
 *
 * Revision 1.83  2009/09/15 05:03:49  liuding
 * *** empty log message ***
 *
 * Revision 1.82  2009/09/09 09:20:05  liuding
 * *** empty log message ***
 *
 * Revision 1.81  2009/09/07 08:00:18  liuding
 * *** empty log message ***
 *
 * Revision 1.80  2009/08/20 13:46:20  liuding
 * 兼容旧的表结构
 *
 * Revision 1.79  2009/07/30 02:02:57  richie
 * *** empty log message ***
 *
 * Revision 1.78  2009/07/28 02:17:18  liuding
 * *** empty log message ***
 *
 * Revision 1.77  2009/07/21 09:38:13  liuding
 * *** empty log message ***
 *
 * Revision 1.76  2009/07/15 06:07:52  richie
 * *** empty log message ***
 *
 * Revision 1.75  2009/07/15 06:07:01  richie
 * *** empty log message ***
 *
 * Revision 1.74  2009/07/15 01:52:47  liuding
 * *** empty log message ***
 *
 * Revision 1.73  2009/07/09 11:56:02  liuding
 * *** empty log message ***
 *
 * Revision 1.72  2009/07/09 09:57:46  liuding
 * *** empty log message ***
 *
 * Revision 1.71  2009/07/09 09:01:46  liuding
 * *** empty log message ***
 *
 * Revision 1.70  2009/07/07 06:18:59  richie
 * *** empty log message ***
 *
 * Revision 1.69  2009/07/05 13:48:53  liuding
 * *** empty log message ***
 *
 * Revision 1.68  2009/07/02 04:57:04  richie
 * *** empty log message ***
 *
 * Revision 1.67  2009/07/01 07:13:16  richie
 * *** empty log message ***
 *
 * Revision 1.66  2009/06/24 06:58:05  richie
 * *** empty log message ***
 *
 * Revision 1.65  2009/06/24 02:33:07  liuding
 * *** empty log message ***
 *
 * Revision 1.64  2009/06/23 12:36:22  richie
 * *** empty log message ***
 *
 * Revision 1.63  2009/06/20 10:15:15  liuding
 * *** empty log message ***
 *
 * Revision 1.62  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.61  2009/06/16 09:14:02  liuding
 * *** empty log message ***
 *
 * Revision 1.60  2009/06/16 05:17:05  liuding
 * 中间表不需要ID
 *
 * Revision 1.59  2009/06/08 11:38:27  richie
 * *** empty log message ***
 *
 * Revision 1.58  2009/06/04 14:28:57  liuding
 * *** empty log message ***
 *
 * Revision 1.57  2009/05/27 09:11:54  richie
 * *** empty log message ***
 *
 * Revision 1.56  2009/05/26 03:37:25  liuding
 * insert 的时候 包含子，关系重复更新的问题，bugfix
 *
 * Revision 1.55  2009/05/25 08:43:03  richie
 * *** empty log message ***
 *
 * Revision 1.54  2009/05/20 02:37:15  richie
 * *** empty log message ***
 *
 * Revision 1.53  2009/05/18 11:43:02  richie
 * *** empty log message ***
 *
 * Revision 1.52  2009/05/13 01:47:39  richie
 * *** empty log message ***
 *
 * Revision 1.51  2009/05/08 02:40:25  richie
 * *** empty log message ***
 *
 * Revision 1.50  2009/05/07 10:38:47  richie
 * *** empty log message ***
 *
 * Revision 1.49  2009/04/28 03:26:11  richie
 * *** empty log message ***
 *
 * Revision 1.48  2009/04/27 03:28:40  richie
 * *** empty log message ***
 *
 * Revision 1.47  2009/04/22 10:42:43  richie
 * *** empty log message ***
 *
 * Revision 1.46  2009/04/15 03:51:15  liuq
 * *** empty log message ***
 *
 * Revision 1.45  2009/04/15 03:12:55  richie
 * *** empty log message ***
 *
 * Revision 1.44  2009/04/13 05:11:46  richie
 * *** empty log message ***
 *
 * Revision 1.43  2009/04/08 01:33:59  richie
 * *** empty log message ***
 *
 * Revision 1.42  2009/04/07 10:59:32  liuding
 * insertMetaComponentObject
 *
 * Revision 1.41  2009/04/07 05:40:18  shenwq
 * *** empty log message ***
 *
 * Revision 1.40  2009/04/03 07:54:48  richie
 * *** empty log message ***
 *
 * Revision 1.39  2009/04/03 04:22:38  liuding
 * *** empty log message ***
 *
 * Revision 1.38  2009/04/01 03:13:45  richie
 * *** empty log message ***
 *
 * Revision 1.37  2009/03/30 03:20:21  richie
 * *** empty log message ***
 *
 * Revision 1.36  2009/03/23 09:12:58  richie
 * *** empty log message ***
 *
 * Revision 1.35  2009/03/23 01:31:01  richie
 * *** empty log message ***
 *
 * Revision 1.34  2009/03/13 08:17:19  shenwq
 * 替金轲commit
 *
 * Revision 1.33  2009/03/09 10:37:00  richie
 * *** empty log message ***
 *
 * Revision 1.32  2009/03/09 09:33:40  richie
 * *** empty log message ***
 *
 * Revision 1.31  2009/03/09 08:53:50  richie
 * *** empty log message ***
 *
 * Revision 1.30  2009/03/06 05:50:52  richie
 * *** empty log message ***
 *
 * Revision 1.29  2009/03/06 05:19:33  richie
 * *** empty log message ***
 *
 * Revision 1.28  2009/03/06 03:12:10  richie
 * *** empty log message ***
 *
 * Revision 1.27  2009/03/04 09:56:08  richie
 * *** empty log message ***
 *
 * Revision 1.26  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.25  2009/02/27 03:33:10  richie
 * *** empty log message ***
 *
 * Revision 1.24  2009/02/27 02:02:00  richie
 * *** empty log message ***
 *
 * Revision 1.23  2009/02/26 06:16:46  richie
 * *** empty log message ***
 *
 * Revision 1.22  2009/02/23 07:26:47  richie
 * *** empty log message ***
 *
 * Revision 1.21  2009/02/19 10:03:54  richie
 * *** empty log message ***
 *
 * Revision 1.20  2009/02/19 06:21:09  richie
 * *** empty log message ***
 *
 * Revision 1.19  2009/02/18 08:01:54  richie
 * *** empty log message ***
 *
 * Revision 1.18  2009/02/12 09:19:57  liuding
 * *** empty log message ***
 *
 * Revision 1.17  2009/02/12 01:52:23  shenwq
 * setValue时对数据字典设int值
 *
 * Revision 1.16  2009/02/10 03:27:34  liuding
 * *** empty log message ***
 *
 * Revision 1.15  2009/02/10 03:21:53  richie
 * *** empty log message ***
 *
 * Revision 1.14  2009/02/04 03:25:23  liuding
 * *** empty log message ***
 *
 * Revision 1.13  2009/01/19 02:39:18  liuding
 * *** empty log message ***
 *
 * Revision 1.12  2009/01/05 03:33:01  richie
 * change QueryResult name to SqlQueryResult
 *
 * Revision 1.11  2009/01/05 03:18:34  richie
 * *** empty log message ***
 *
 * Revision 1.10  2009/01/05 02:34:47  richie
 * change for support spring configuration
 *
 * Revision 1.9  2009/01/04 01:21:03  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.engine.persister.entity;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import oracle.jdbc.rowset.OracleSerialClob;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.dialect.Dialect;

import com.gxlu.meta.MetaAttribute;
import com.gxlu.meta.MetaClass;
import com.gxlu.meta.MetaClassManager;
import com.gxlu.meta.MetaClassRelation;
import com.gxlu.meta.MetaComponent;
import com.gxlu.meta.MetaComponentObject;
import com.gxlu.meta.MetaComponentRelation;
import com.gxlu.meta.MetaData;
import com.gxlu.meta.MetaObject;
import com.gxlu.meta.MetaObjectQueryResult;
import com.gxlu.meta.QueryAssemble;
import com.gxlu.meta.QueryCriteria;
import com.gxlu.meta.QueryExpressionUtil;
import com.gxlu.meta.cfg.DefaultMetaClass;
import com.gxlu.meta.cfg.DefaultMetaComponentObject;
import com.gxlu.meta.cfg.DefaultMetaObject;
import com.gxlu.meta.cfg.dbdescriptor.ClassRelationDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ClassTableDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ComponentRelationDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.ComponentTableDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.PropertyDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.RelationDescriptor;
import com.gxlu.meta.cfg.dbdescriptor.TableDescriptor;
import com.gxlu.meta.criterion.Criterion;
import com.gxlu.meta.engine.jdbc.SqlExecutor;
import com.gxlu.meta.engine.jdbc.SqlExecutorException;
import com.gxlu.meta.engine.jdbc.SqlQueryResult;
import com.gxlu.meta.help.MetaObjectHelper;
import com.gxlu.meta.tools.CollectionUtil;
import com.gxlu.meta.tools.MetaConsts;

/**
 * @author lethe & K
 */
@SuppressWarnings("deprecation")
public class EntityPersister {

	private static Logger logger = Logger.getLogger(EntityPersister.class);

	private static EntityPersister instance;

	private MetaClassManager metaClassManager = null;

	private SqlExecutor executor = null;

	private Dialect oracle9dialect = new org.hibernate.dialect.Oracle9Dialect();

	private Map<String, String> sqlDeleteStrings = new HashMap<String, String>();

	private Map<String, String> sqlInsertStrings = new HashMap<String, String>();

	private Map<String, String> sqlUpdateStrings = new HashMap<String, String>();

	private Map<String, String> sqlComponentDeleteStrings = new HashMap<String, String>();

	private Map<String, String> sqlComponentInsertStrings = new HashMap<String, String>();

	private Map<String, String> sqlComponentUpdateStrings = new HashMap<String, String>();

	private Map<String, LinkedList<PropertyDescriptor>> entityPropertyList = new HashMap<String, LinkedList<PropertyDescriptor>>();

	private Map<String, LinkedList<PropertyDescriptor>> componentPropertyList = new HashMap<String, LinkedList<PropertyDescriptor>>();

	private Map<String, LinkedList<ClassRelationDescriptor>> classRelations = new HashMap<String, LinkedList<ClassRelationDescriptor>>();

	private Map<String, LinkedList<ComponentRelationDescriptor>> componentRelations = new HashMap<String, LinkedList<ComponentRelationDescriptor>>();

	private String srid = "4214";
	
	public EntityPersister() {
	}

	public static EntityPersister getInstance() {
		if (instance == null) {
			instance = new EntityPersister();
		}

		return instance;
	}

	/**
	 * 返回数据库实体表中的属性列表
	 * 
	 * @param metaClass
	 * @return
	 */
	private LinkedList<PropertyDescriptor> getPropertyColumnNames(MetaClass metaClass) {
		LinkedList<PropertyDescriptor> ret = entityPropertyList.get(metaClass.getClassName());
		if (ret == null) {
			ClassTableDescriptor desc = metaClassManager.getClassTableDescriptor(metaClass);
			List<PropertyDescriptor> descProperty = desc.getMetaProerties();
			ret = new LinkedList<PropertyDescriptor>();

			for (int i = 0; i < descProperty.size(); i++) {
				PropertyDescriptor item = descProperty.get(i);
				ret.add(i, item);
			}
			entityPropertyList.put(metaClass.getClassName(), ret);
		}
		return ret;
	}

	/**
	 * 返回数据库组件表中的属性列表
	 * 
	 * @param metaComponent
	 * @return
	 */
	private LinkedList<PropertyDescriptor> getComponentPropertyColumnNames(MetaComponent metaComponent) {
		LinkedList<PropertyDescriptor> ret = componentPropertyList.get(metaComponent.getCode());
		if (ret == null) {
			ComponentTableDescriptor desc = metaClassManager.getComponentTableDescriptor(metaComponent);
			List<PropertyDescriptor> descProperty = desc.getMetaProerties();
			ret = new LinkedList<PropertyDescriptor>();
			for (int i = 0; i < descProperty.size(); i++) {
				ret.add(i, descProperty.get(i));
			}
			componentPropertyList.put(metaComponent.getCode(), ret);
		}
		return ret;
	}

	private String getComponentInsertSql(MetaComponent metaComponent) {
		String sql = sqlComponentInsertStrings.get(metaComponent.getCode());
		if (sql == null) {
			ComponentTableDescriptor desc = metaClassManager.getComponentTableDescriptor(metaComponent);
			List<PropertyDescriptor> descProperty = getComponentPropertyColumnNames(metaComponent);
			String tablename = desc.getTable();
			Insert insert = new Insert(oracle9dialect).setTableName(tablename);
			int columnSize = descProperty.size();
			for (int i = 0; i < columnSize; i++) {
				insert.addColumn(descProperty.get(i).getColumn());
			}
			sql = insert.toStatementString();
			sqlComponentInsertStrings.put(metaComponent.getCode(), sql);
		}
		return sql;
	}

	private String getEntityInsertSql(MetaClass metaClass) {
		String sql = sqlInsertStrings.get(metaClass.getClassName());
		if (sql == null) {
			ClassTableDescriptor desc = metaClassManager.getClassTableDescriptor(metaClass);
			List<PropertyDescriptor> descProperty = getPropertyColumnNames(metaClass);
			String tablename = desc.getTable();
			Insert insert = new Insert(oracle9dialect).setTableName(tablename);
			int columnSize = descProperty.size();
			if("com.gxlu.ngrm.equipment.ManagedElement".equals(metaClass.getClassName())
					|| MetaObjectHelper.isChildren(metaClass, MetaObjectHelper.getMetaClass("com.gxlu.ngrm.equipment.ManagedElement"))
				||"com.gxlu.ngrm.area.Polygon".equals(metaClass.getClassName())
					|| MetaObjectHelper.isChildren(metaClass, MetaObjectHelper.getMetaClass("com.gxlu.ngrm.area.Polygon")))
			{
				for (int i = 0; i < columnSize; i++) 
				{
					String col=descProperty.get(i).getColumn();
					if(!("SHAPE".equals(col)||"SHAPE_ID".equals(col)))
						insert.addColumn(descProperty.get(i).getColumn());
				}
				insert.addColumn("SHAPE");
				insert.addColumn("SHAPE_ID");
			}
			else
			{
				for (int i = 0; i < columnSize; i++) 
				{
					String gc=descProperty.get(i).getColumn();
					if(gc.equals("CHECKPOINT"))
						insert.addColumn("[CHECKPOINT]");
					else
						insert.addColumn(gc);
				}
			}
			sql = insert.toStatementString();
			sqlInsertStrings.put(metaClass.getClassName(), sql);
		}
		return sql;
	}

	// private String getEntityRelationSql(MetaObject
	// metaObject,MetaClassRelation relation){
	// ClassRelationDescriptor relationDesc =
	// descFactory.getClassRelationDescriptor(relation);
	//		
	// ClassTableDescriptor mainTableDesc =
	// descFactory.getClassTableDescriptor(metaObject.getMetaClass());
	//		
	// List<MetaObject> relateObjects = metaObject.getRelateObject(relation);
	//		
	//		
	// if(!relationDesc.isIndependentTable()){
	// String tableName = relationDesc.getJoinTableName();
	// Update update = new Update(oracle9dialect).setTableName(tableName);
	//			
	// if(tableName.equals(mainTableDesc.getTable())){
	// if(relateObjects.size()>1){
	// //TODO 异常
	// }
	// MetaObject relateObject = relateObjects.get(0);
	//				
	// ClassTableDescriptor relateTableDesc =
	// descFactory.getClassTableDescriptor(relateObject.getMetaClass());
	// update.addColumn(relateTableDesc.getPrimitiveProperty().getColumn());
	// update.setVersionColumnName( mainTableDesc.getVersion() );
	// update.addWhereColumn( mainTableDesc.getPrimitiveProperty().getColumn());
	//				
	// String sql = update.toStatementString();
	//				
	// }
	// }
	// return null;
	// }

	private void updateComponentRelation(MetaObject metaObject, MetaComponentRelation relation)
			throws SqlExecutorException {
		ComponentRelationDescriptor componentRelationDesc = metaClassManager.getComponentRelationDescriptor(relation);
		ComponentTableDescriptor componentTable = metaClassManager.getComponentTableDescriptor(relation
				.getRelationMetaComponent());
		ClassTableDescriptor mainTableDesc = metaClassManager.getClassTableDescriptor(metaObject.getMetaClass());

		String joinTableName = componentRelationDesc.getJoinTableName();
		Update update = new Update(oracle9dialect).setTableName(joinTableName);
		if (joinTableName.equals(mainTableDesc.getTable())) {
			update.addColumn(componentRelationDesc.getOppositeColumn());
			update.addWhereColumn(mainTableDesc.getPrimitiveProperty().getColumn());
			String updateSql = update.toStatementString();
			Object[] ids = new Object[2];
			MetaComponentObject componentObject = metaObject.getRelateComponentObject(relation);
			String componentAttrName = componentTable.getPrimitiveProperty().getMetaAttribute().getAttributeName();
			Object oppositeId = componentObject.getValue(componentAttrName);
			Object shelfID = metaObject.getValue(mainTableDesc.getPrimitiveProperty().getMetaAttribute()
					.getAttributeName());
			ids[0] = oppositeId;
			ids[1] = shelfID;
			executor.update(updateSql, ids);
		} else {
			update.addColumn(componentRelationDesc.getOppositeColumn());
			update.addWhereColumn(componentTable.getPrimitiveProperty().getColumn());
			String updateSql = update.toStatementString();
			Object[] ids = new Object[2];
			MetaComponentObject componentObject = metaObject.getRelateComponentObject(relation);
			String componentAttrName = componentTable.getPrimitiveProperty().getMetaAttribute().getAttributeName();
			Object oppositeId = componentObject.getValue(componentAttrName);
			Object shelfID = metaObject.getValue(mainTableDesc.getPrimitiveProperty().getMetaAttribute()
					.getAttributeName());
			ids[0] = shelfID;
			ids[1] = oppositeId;
			executor.update(updateSql, ids);
		}
	}

	private boolean isRelationTableRely(String joinTableName, ClassTableDescriptor descriptor) {
		boolean result = false;
		if (isCoretableRely(joinTableName, descriptor)) {
			return true;
		}
		if (isExtendTableRelay(joinTableName, descriptor)) {
			return true;
		}

		return result;
	}

	private boolean isExtendTableRelay(String joinTableName, ClassTableDescriptor descriptor) {
		boolean result = false;
		for (RelationDescriptor plain : descriptor.getPlainTableRelations()) {
			if (plain.getOppositeTable() != null && joinTableName.equals(plain.getOppositeTable().getTable())) {
				result = true;
				break;
			}
		}
		return result;
	}

	private boolean isCoretableRely(String joinTableName, ClassTableDescriptor descriptor) {
		boolean result = false;
		if (joinTableName.equals(descriptor.getTable())) {
			result = true;
		}

		return result;
	}

	/**
	 * 更新实体关系
	 * 
	 * @param metaObject
	 *            需要更新的实体
	 * @param relation
	 *            关系描述
	 * @throws SqlExecutorException
	 */
	private void updateEntityRelation(MetaObject metaObject, MetaClassRelation relation) throws SqlExecutorException {
		ClassTableDescriptor relationEntityDesc = metaClassManager.getClassTableDescriptor(relation
				.getRelationMetaClass());
		ClassTableDescriptor mainEntityDesc = metaClassManager.getClassTableDescriptor(metaObject.getMetaClass());
		ClassRelationDescriptor relationDesc = metaClassManager.getClassRelationDescriptor(relation);

		int multiple = relation.getRelationType();
		if (!relationDesc.isIndependentTable()) {
			// 不是中间表
			String joinTableName = relationDesc.getJoinTableName();
			Update update = new Update(oracle9dialect).setTableName(joinTableName);
			// 自关联的关系
			if (isRelationTableRely(joinTableName, mainEntityDesc)
					&& isRelationTableRely(joinTableName, relationEntityDesc)) {
				if (isRelationTableRely(joinTableName, mainEntityDesc)
						&& multiple == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_N2ONE) {
					update.addColumn(relationDesc.getOppositeColumn());
					// ---------------更新外键
					List<MetaAttribute> metaAttributeList = metaObject.getMetaClass().getMetaAttributes();
					String foreignKey = null;
					for (MetaAttribute metaAttribute : metaAttributeList) {
						if (relationDesc.getOppositeColumn().equals(metaAttribute.getTableColumnName())) {
							foreignKey = metaAttribute.getAttributeName();
						}
					}
					// --------------- 更新外键

					if (isCoretableRely(joinTableName, mainEntityDesc)) {
						update.addWhereColumn(mainEntityDesc.getPrimitiveProperty().getColumn());
					} else if (isExtendTableRelay(joinTableName, mainEntityDesc)) {
						update.addWhereColumn(getPlainTableIDColumnName(mainEntityDesc, joinTableName));
					}
					String updateSql = update.toStatementString();
					Object[] ids = new Object[2];
					List<MetaObject> metaObjects = metaObject.getRelateObject(relation);
					if (metaObjects != null && metaObjects.size() > 0) {
						MetaObject temp = metaObjects.get(0);
						String objectAttrName = relationEntityDesc.getPrimitiveProperty().getMetaAttribute()
								.getAttributeName();
						Object oppositeId = temp.getValue(objectAttrName);
						metaObject.setValue(foreignKey, oppositeId);// ---------------更新外键
						Object shelfID = metaObject.getValue(mainEntityDesc.getPrimitiveProperty().getMetaAttribute()
								.getAttributeName());
						ids[0] = oppositeId;
						ids[1] = shelfID;
						executor.update(updateSql, ids);
					}
				} else if (isRelationTableRely(joinTableName, relationEntityDesc)
						&& multiple == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_ONE2N) {
					update.addColumn(relationDesc.getShelfColumn());
					if (isCoretableRely(joinTableName, relationEntityDesc)) {
						update.addWhereColumn(relationEntityDesc.getPrimitiveProperty().getColumn());
					} else if (isExtendTableRelay(joinTableName, relationEntityDesc)) {
						update.addWhereColumn(getPlainTableIDColumnName(relationEntityDesc, joinTableName));
					}

					String updateSql = update.toStatementString();
					Object[] ids = new Object[2];
					List<MetaObject> metaObjects = metaObject.getRelateObject(relation);
					if (metaObjects != null && metaObjects.size() > 0) {
						for (MetaObject object : metaObjects) {
							String componentAttrName = relationEntityDesc.getPrimitiveProperty().getMetaAttribute()
									.getAttributeName();
							Object oppositeId = object.getValue(componentAttrName);
							Object shelfID = metaObject.getValue(mainEntityDesc.getPrimitiveProperty()
									.getMetaAttribute().getAttributeName());
							ids[0] = shelfID;
							ids[1] = oppositeId;
							executor.update(updateSql, ids);
						}
					}
				}
			} else if (isRelationTableRely(joinTableName, mainEntityDesc)) {
				update.addColumn(relationDesc.getOppositeColumn());
				// ---------------更新外键
				List<MetaAttribute> metaAttributeList = metaObject.getMetaClass().getMetaAttributes();
				String foreignKey = null;
				for (MetaAttribute metaAttribute : metaAttributeList) {
					if (relationDesc.getOppositeColumn().equals(metaAttribute.getTableColumnName())) {
						foreignKey = metaAttribute.getAttributeName();
					}
				}
				// --------------- 更新外键
				if (isCoretableRely(joinTableName, mainEntityDesc)) {
					update.addWhereColumn(mainEntityDesc.getPrimitiveProperty().getColumn());
				} else if (isExtendTableRelay(joinTableName, mainEntityDesc)) {
					update.addWhereColumn(getPlainTableIDColumnName(mainEntityDesc, joinTableName));
				}
				String updateSql = update.toStatementString();
				Object[] ids = new Object[2];
				List<MetaObject> metaObjects = metaObject.getRelateObject(relation);
				if (metaObjects != null && metaObjects.size() > 0) {
					MetaObject temp = metaObjects.get(0);
					String objectAttrName = relationEntityDesc.getPrimitiveProperty().getMetaAttribute()
							.getAttributeName();
					Object oppositeId =temp==null?null: temp.getValue(objectAttrName);
					metaObject.setValue(foreignKey, oppositeId);// ---------------更新外键
					Object shelfID = metaObject.getValue(mainEntityDesc.getPrimitiveProperty().getMetaAttribute()
							.getAttributeName());
					ids[0] = oppositeId;
					ids[1] = shelfID;
					executor.update(updateSql, ids);
				}
			} else {
				// update.addColumn(relationDesc.getOppositeColumn());
				update.addColumn(relationDesc.getShelfColumn());
				if (isCoretableRely(joinTableName, relationEntityDesc)) {
					update.addWhereColumn(relationEntityDesc.getPrimitiveProperty().getColumn());
				} else if (isExtendTableRelay(joinTableName, relationEntityDesc)) {
					update.addWhereColumn(getPlainTableIDColumnName(relationEntityDesc, joinTableName));
				}
				String updateSql = update.toStatementString();
				Object[] ids = new Object[2];
				List<MetaObject> metaObjects = metaObject.getRelateObject(relation);
				if (metaObjects != null && metaObjects.size() > 0) {
					for (MetaObject object : metaObjects) {
						String componentAttrName = relationEntityDesc.getPrimitiveProperty().getMetaAttribute()
								.getAttributeName();
						Object oppositeId = object.getValue(componentAttrName);
						Object shelfID = metaObject.getValue(mainEntityDesc.getPrimitiveProperty().getMetaAttribute()
								.getAttributeName());
						ids[0] = shelfID;
						ids[1] = oppositeId;
						executor.update(updateSql, ids);
					}
				}
			}
		} else {
			// 对于中间表
			String joinTableName = relationDesc.getJoinTableName();
			Insert insert = new Insert(oracle9dialect).setTableName(joinTableName);
			insert.addColumn(relationDesc.getOppositeColumn());
			insert.addColumn(relationDesc.getShelfColumn());
			// TODO VersionINSTANCE
			// insert.addColumn("ID");
			// insert.addColumn("RELATIONSPECID");

			String sql = insert.toStatementString();
			Object[] ids = new Object[2];
			List<MetaObject> metaObjects = metaObject.getRelateObject(relation);

			if (metaObjects != null && metaObjects.size() > 0) {
				for (MetaObject object : metaObjects) {
					String componentAttrName = relationEntityDesc.getPrimitiveProperty().getMetaAttribute()
							.getAttributeName();
					Object oppositeId = object.getValue(componentAttrName);
					Object shelfID = metaObject.getValue(mainEntityDesc.getPrimitiveProperty().getMetaAttribute()
							.getAttributeName());
					// ids[3] = relation.getId();
					// ids[2] = generateId("S_" + joinTableName);
					ids[1] = shelfID;
					ids[0] = oppositeId;
					executor.update(sql, ids);
				}
			}
		}

	}

	private Long generateId(String sequenceName) throws SqlExecutorException {
		String sql = oracle9dialect.getSequenceNextValString(sequenceName);
		long sequence = executor.queryForLong(sql, null);
		return new Long(sequence);
	}

	private void insertMetaObject(MetaObject metaObject, MetaClassRelation mainClassRelation, MetaObject mainObject)
			throws MetaPlatformPersisterException, SqlExecutorException {
		boolean isNeedInsert;
		isNeedInsert = isNeedInsert(metaObject, mainObject);
		MetaClass metaClass = metaObject.getMetaClass();
		ClassTableDescriptor tableDesc = metaClassManager.getClassTableDescriptor(metaClass);

		if (isNeedInsert) {
			// add create date
			if (metaObject.getMetaClass().findMetaAttribute("CREATEDATE") != null) {
				metaObject.setValue("CREATEDATE", new Date());
			}
			String insertSql = getEntityInsertSql(metaObject.getMetaClass());

			// 设置Version
			String versionAttrName = tableDesc.getVersionProperty().getMetaAttribute().getAttributeName();
			metaObject.setValue(versionAttrName, 0);

			// 设置ID
			Long id = generateId(tableDesc.getSequence());
			String idAttrName = tableDesc.getPrimitiveProperty().getMetaAttribute().getAttributeName();
			metaObject.setValue(idAttrName, id);

			Object[] values = getMetaObjectValues(metaObject);
			
			if(MetaObjectHelper.isInstance(metaObject, MetaObjectHelper.getMetaClass("com.gxlu.ngrm.equipment.ManagedElement"))){
				
				insertSql = insertSql.substring(0, insertSql.length()-5) ;
				String value = null;
				if(metaObject.getValue("GEOX")!=null   && metaObject.getValue("GEOY")!=null  && !"".equals(metaObject.getValue("GEOX")) &&  !"".equals(metaObject.getValue("GEOY"))){
					//cyx add this condition :&& !"".equals(metaObject.getValue("GEOX")) &&  !"".equals(metaObject.getValue("GEOY")
					//cyx comment: 杩欐牱涔熺畻娌″?硷紝鎷兼帴SQL浼氭姤閿欑殑銆?
					value = getPointSdoString(metaObject);
				}else{
					value = "null";
				}
				value =  value + "," + id;
				insertSql = insertSql+value + ")";
			}
			
			if(MetaObjectHelper.isInstance(metaObject, MetaObjectHelper.getMetaClass("com.gxlu.ngrm.area.Polygon"))){
				
				insertSql = insertSql.substring(0, insertSql.length()-5) ;
				String value = null;
				if(metaObject.getValue("GEOPOLYGON")!=null){
					value = getPolygonSdoString(metaObject.getString("GEOPOLYGON"));
				}else{
					value = "null";
				}
				value =  value + "," + id;
				insertSql = insertSql+value + ")";
			}			
			
			executor.update(insertSql, values);

			// 对于组件的持久化
			List<MetaComponentRelation> componentRelations = metaClass.getMetaComponentRelations();
			if (componentRelations != null && componentRelations.size() > 0) {
				for (MetaComponentRelation relation : componentRelations) {
					MetaComponentObject componentObject = metaObject.getRelateComponentObject(relation);
					if (componentObject != null) {
						insertMetaComponentObject(metaObject, relation, componentObject);
					}
				}
			}

			// 对于卫星表的持久化
			List<RelationDescriptor> plainList = tableDesc.getPlainTableRelations();
			for (RelationDescriptor descriptor : plainList) 
			{
				TableDescriptor plainTable = descriptor.getOppositeTable();
				String plainTableEntityID = getPlainTableIDColumnName(plainTable);
				List<PropertyDescriptor> descPropertyOri = plainTable.getMetaProerties();
				List<PropertyDescriptor> descProperty=new ArrayList<PropertyDescriptor>();
				for(PropertyDescriptor dp:descPropertyOri)
				{
					if(!descProperty.contains(dp))
						descProperty.add(dp);
				}
				String tablename = plainTable.getTable();
				Insert insert = new Insert(oracle9dialect).setTableName(tablename);
				int columnSize = descProperty.size();
				Object[] plainValues =null;
				if(//plainTable.getTable().equals("XS_SITE")||   add by xlm site的坐标已经重新写了入库方法
				   plainTable.getTable().equals("XS_GISCABLESECTION")|| 
				   plainTable.getTable().equals("XS_GISECABLESECTION")||
				   plainTable.getTable().equals("XS_GISDUCTSEG")|| 
				   plainTable.getTable().equals("XS_GISSLINGSEG"))
				{
					plainValues=new Object[columnSize];
					for (int i = 0,j=0; i < columnSize;j++) 
					{
						PropertyDescriptor pd=descProperty.get(j);
						String col=pd.getColumn();
						if(!("SHAPE".equals(col)||"SHAPE_ID".equals(col)))
						{
							String pc=pd.getColumn();
//							if(pc.equals("CHECKPOINT"))
//							{
//								insert.addColumn("[CHECKPOINT]");
//							}
//							else
								insert.addColumn(pd.getColumn());
							if (pd.getColumn().equals(plainTableEntityID)) 
								plainValues[i] = id; 
							else
								plainValues[i] = metaObject.getValue(pd.getMetaAttribute().getAttributeName());
							i++;
						}
					}
					insert.addColumn("SHAPE");
					insert.addColumn("SHAPE_ID");
				}					
				else
				{
					plainValues= new Object[columnSize];
					for (int i = 0; i < columnSize; i++) 
					{
						insert.addColumn(descProperty.get(i).getColumn());
						if (descProperty.get(i).getColumn().equals(plainTableEntityID)) 
							plainValues[i] = id; 
						else
							plainValues[i] = metaObject.getValue(descProperty.get(i).getMetaAttribute().getAttributeName());
					}
				}
				String sql = insert.toStatementString();
				
				String value = null;
				
				if(//plainTable.getTable().equals("XS_SITE")||   add by xlm site的坐标已经重新写了入库方法
				   plainTable.getTable().equals("XS_GISCABLESECTION")||
				   plainTable.getTable().equals("XS_GISECABLESECTION")||
				   plainTable.getTable().equals("XS_GISDUCTSEG")|| 
				   plainTable.getTable().equals("XS_GISSLINGSEG"))
				{
					if(plainTable.getTable().equals("XS_SITE"))
					{
						if(metaObject.getValue("GEOX")!=null && metaObject.getValue("GEOY")!=null )
						{
							value = getPointSdoString(metaObject);
						}
					}
					else if("XS_GISDUCTSEG".equals(plainTable.getTable())||"XS_GISSLINGSEG".equals(plainTable.getTable()))
					{
						if(metaObject.getValue("AGEOX")!=null && metaObject.getValue("AGEOY")!=null )
						{
							value = getLineSdoString(metaObject);
						}
					}
					else if(plainTable.getTable().equals("XS_GISCABLESECTION") || plainTable.getTable().equals("XS_GISECABLESECTION"))
					{
						if(metaObject.getValue("GROUPGEO")!=null)
						{
							value = getLineSdoStringDirect(metaObject.getValue("GROUPGEO").toString());
						}
						else if(metaObject.getValue("AGEOX")!=null && metaObject.getValue("AGEOY")!=null )
						{
							value = getLineSdoString(metaObject);
						}
					}					
					sql = sql.substring(0, sql.length()-5) ;
					value =  value + "," + id;
						sql = sql+value + ")";
				}	
				executor.update(sql, plainValues);
			}
		}

		// 对于关联对象的持久化
		List<MetaClassRelation> objectRelations = metaClass.getMetaClassRelations();
		if (objectRelations != null && objectRelations.size() > 0) {
			for (MetaClassRelation relation : objectRelations) {
				// 避免重复持久化
				if (!relation.isSameRelationSpec(mainClassRelation)) {
					List<MetaObject> relationObjects = metaObject.getRelateObject(relation);
					if (relationObjects != null && relationObjects.size() > 0) {
						if (CollectionUtil.notContainInList(metaObject.getFeches(), relation.getAlias())) {
							for (MetaObject object : relationObjects) {
								if (object.getPersistType() == MetaObject.PERSIST_TYPE_ADD) {
									insertMetaObject(object, relation, metaObject);
								}
							}
							// insert 所有的子以后再更新关系，此方法只能调用一次，会更新于所有子的关系
							updateEntityRelation(metaObject, relation);
						}
					}
				}
			}
		}
	}

	private void forGisTempMethod(MetaData metaObject, MetaData usedObject,TableDescriptor descriptor,Update update, List<Object> nodes) {
		
		if(descriptor.getTable().equals("XS_SITE")|| descriptor.getTable().equals("XB_MANAGEDELEMENT"))
		{
			boolean isChangedX = isAttributeChanged(metaObject, usedObject, "GEOX");
			boolean isChangedY = isAttributeChanged(metaObject, usedObject, "GEOY");
			if(isChangedX || isChangedY)
			{
				String shape =getPointSdoString(metaObject);
		//		update.addColumn("SHAPE",shape);
//				nodes.add(shape);
			}
		}
		
		if("XS_GISDUCTSEG".equals(descriptor.getTable())
				||"XS_GISSLINGSEG".equals(descriptor.getTable())){
			boolean isChangedAX = isAttributeChanged(metaObject, usedObject, "AGEOX");
			boolean isChangedAY = isAttributeChanged(metaObject, usedObject, "AGEOY");
			
			boolean isChangedZX = isAttributeChanged(metaObject, usedObject, "ZGEOX");
			boolean isChangedZY = isAttributeChanged(metaObject, usedObject, "ZGEOY");
			if(isChangedAX || isChangedAY || isChangedZX || isChangedZY){
				String shape = getLineSdoString(metaObject);
				update.addColumn("SHAPE",shape);
//				nodes.add(shape);
			}
		}else if(descriptor.getTable().equals("XS_GISCABLESECTION") || descriptor.getTable().equals("XS_GISECABLESECTION")){
			if( metaObject.getValue("GROUPGEO")!=null ){
				boolean isChanged = isAttributeChanged(metaObject, usedObject, "GROUPGEO");
				if(isChanged){
					String shape = getLineSdoStringDirect(metaObject.getValue("GROUPGEO").toString());
					update.addColumn("SHAPE",shape);
				}
			}else{
				boolean isChangedAX = isAttributeChanged(metaObject, usedObject, "AGEOX");
				boolean isChangedAY = isAttributeChanged(metaObject, usedObject, "AGEOY");
				
				boolean isChangedZX = isAttributeChanged(metaObject, usedObject, "ZGEOX");
				boolean isChangedZY = isAttributeChanged(metaObject, usedObject, "ZGEOY");
				if(isChangedAX || isChangedAY || isChangedZX || isChangedZY){
					String shape = getLineSdoString(metaObject);
					update.addColumn("SHAPE",shape);
//					nodes.add(shape);
				}
			}
		}
		
		if(descriptor.getTable().equals("XB_POLYGON")){
			boolean isChangedAX = isAttributeChanged(metaObject, usedObject, "GEOPOLYGON");
			if(isChangedAX && metaObject.getString("GEOPOLYGON")!=null){
				String shape = getPolygonSdoString(metaObject.getString("GEOPOLYGON"));
				update.addColumn("SHAPE",shape);
//				nodes.add(shape);
			}
		}
		
	}

	private String getPointSdoString(MetaData metaObject) 
	{   
	    //cyx added this condition:||StringUtils.isBlank(metaObject.getValue("GEOX").toString())  || StringUtils.isBlank(metaObject.getValue("GEOY").toString())
		if(metaObject.getValue("GEOX")==null||metaObject.getValue("GEOY")==null 
				||StringUtils.isBlank(metaObject.getValue("GEOX").toString())  || StringUtils.isBlank(metaObject.getValue("GEOY").toString()))
			return "null";
		String spatialString = "MDSYS.SDO_GEOMETRY(2001, "+srid+", null, MDSYS.SDO_ELEM_INFO_ARRAY(1,1,1), " + "MDSYS.SDO_ORDINATE_ARRAY("
				+ metaObject.getValue("GEOX")+","+ metaObject.getValue("GEOY") + "))";
//		String spatialString = "geometry::STGeomFromText('POINT ("+metaObject.getValue("GEOX")+" "+metaObject.getValue("GEOY")+")', "+srid+")";
		return spatialString;
	}

	private String getLineSdoString(MetaData metaObject) {
		//added by cyx (begin)
		if( StringUtils.isBlank(metaObject.getValue("ZGEOX").toString()) 
				|| StringUtils.isBlank(metaObject.getValue("AGEOY").toString()) 
				||  StringUtils.isBlank(metaObject.getValue("ZGEOX").toString()))
			return "null";
		//added by cyx (end) 
		String spatialString = "MDSYS.SDO_GEOMETRY(2002,"+srid+",NULL,MDSYS.SDO_ELEM_INFO_ARRAY(1,2,1), " + "MDSYS.SDO_ORDINATE_ARRAY("
				+ metaObject.getValue("AGEOX")+","+ metaObject.getValue("AGEOY") +","+metaObject.getValue("ZGEOX")+","+ metaObject.getValue("ZGEOY")+ "))";
//		String spatialString = "geometry::STGeomFromText('LINESTRING ("+metaObject.getValue("AGEOX")+" "+metaObject.getValue("AGEOY")
//		+","+metaObject.getValue("ZGEOX")+" "+ metaObject.getValue("ZGEOY")+")', "+srid+")";
		return spatialString;
	}
	
	private String getLineSdoStringDirect(String line) {
		//added by cyx (begin)
		if( StringUtils.isBlank(line ))
			return "null";
		//added by cyx end 
		String spatialString = "MDSYS.SDO_GEOMETRY(2002,"+srid+",NULL,MDSYS.SDO_ELEM_INFO_ARRAY(1,2,1), " + "MDSYS.SDO_ORDINATE_ARRAY("
				+ line+ "))";
//		String spatialString = "geometry::STGeomFromText('LINESTRING ("+line+")', "+srid+")";
		return spatialString;
	}

	private String getPolygonSdoString(String spatial) {
		String spatialString = "MDSYS.SDO_GEOMETRY(2003,"+srid+",NULL,MDSYS.SDO_ELEM_INFO_ARRAY(1,2003,1), " + "MDSYS.SDO_ORDINATE_ARRAY("
				+ spatial + "))";
//		String spatialString = "geometry::STGeomFromText('POLYGON  ("+spatial+")', "+srid+")";
		return spatialString;
	}

	private boolean isNeedInsert(MetaObject metaObject, MetaObject mainObject) throws MetaPlatformPersisterException,
			SqlExecutorException {
		boolean isNeedInsert = false;
		if (mainObject == null) {
			isNeedInsert = true;
		} else {
			MetaObject oldObject = isAlreadyPersisted(metaObject);
			if (oldObject == null) {
				isNeedInsert = true;
			}
		}
		return isNeedInsert;
	}

	private MetaObject isAlreadyPersisted(MetaObject metaObject) throws MetaPlatformPersisterException,
			SqlExecutorException {
		if(metaObject==null)return null;
		ClassTableDescriptor tableDesc = metaClassManager.getClassTableDescriptor(metaObject.getMetaClass());
		String key = tableDesc.getPrimitiveProperty().getMetaAttribute().getAttributeName();
		MetaObject oldObject = findMetaObjectById(metaObject.getMetaClass(), key, metaObject.getValue(key), metaObject
				.getFeches());
		return oldObject;
	}

	private void insertMetaClassRelation(MetaObject metaObject) throws SqlExecutorException {
		List<MetaClassRelation> objectRelations = metaObject.getMetaClass().getMetaClassRelations();
		if (objectRelations != null && objectRelations.size() > 0) {
			for (MetaClassRelation relation : objectRelations) {
				// 避免重复持久化
				List<MetaObject> relationObjects = metaObject.getRelateObject(relation);
				if (relationObjects != null && relationObjects.size() > 0) {
					// for(MetaObject object : relationObjects) {
					updateEntityRelation(metaObject, relation);
					// insertMetaObject(object, relation, metaObject);
					// }
				}
			}
		}
	}

	private void insertMetaComponentObject(MetaObject metaObject, MetaComponentRelation relation,
			MetaComponentObject componentObject) throws SqlExecutorException {
		MetaComponent component = componentObject.getMetaComponent();
		ComponentTableDescriptor comDesc = metaClassManager.getComponentTableDescriptor(component);
		ClassTableDescriptor mainTableDesc = metaClassManager.getClassTableDescriptor(metaObject.getMetaClass());
		String componentSql = getComponentInsertSql(component);

		// 设置ID
		Long idComponent = generateId(comDesc.getSequence());
		String idComAttrName = comDesc.getPrimitiveProperty().getMetaAttribute().getAttributeName();
		componentObject.setValue(idComAttrName, idComponent);

		// 设置组件关联的实体ID
		componentObject.setValue("ENTITYID", metaObject.getValue(mainTableDesc.getPrimitiveProperty()
				.getMetaAttribute().getAttributeName()));
		componentObject.setValue("ENTITYSPECID", metaObject.getMetaClass().getId());

		Object[] componentValues = getMetaComponentValues(componentObject);
		executor.update(componentSql, componentValues);
		// updateComponentRelation(metaObject, relation);
	}

	private Object[] getMetaObjectValues(MetaObject metaObject) {
		MetaClass metaClass = metaObject.getMetaClass();
		LinkedList<PropertyDescriptor> attrListOri = getPropertyColumnNames(metaClass);
		LinkedList<PropertyDescriptor> attrList=null;
		if(MetaObjectHelper.isInstance(metaObject, MetaObjectHelper.getMetaClass("com.gxlu.ngrm.equipment.ManagedElement"))||
			MetaObjectHelper.isInstance(metaObject, MetaObjectHelper.getMetaClass("com.gxlu.ngrm.area.Polygon")))
		{
			attrList=new LinkedList<PropertyDescriptor>();
			for(PropertyDescriptor pd:attrListOri)
			{
				String col=pd.getColumn();
				if(!("SHAPE".equals(col)||"SHAPE_ID".equals(col)))
				{
					if(!attrList.contains(pd))
						attrList.add(pd);
				}
			}
		}
		else
			attrList=attrListOri;
		Object[] values = new Object[attrList.size()];
		for (int i = 0; i < values.length; i++) 
		{
			MetaAttribute ma=attrList.get(i).getMetaAttribute();
			Object tmpvalue=metaObject.getValue(ma.getAttributeName());
			if(tmpvalue==null)
				values[i]=null;
			else if(tmpvalue instanceof String&&((String)tmpvalue).equals(""))
				values[i]=null;
			else
			{
				if(ma.getDataType()==MetaConsts.VALUE_METADATA_DATATYPE_BYTE)
				{
					values[i]=Byte.parseByte(tmpvalue.toString());
				}
				else if(ma.getDataType()==MetaConsts.VALUE_METADATA_DATATYPE_DICTIONARY)
				{
					values[i]=Integer.parseInt(tmpvalue.toString());
				}
				else if(ma.getDataType()==MetaConsts.VALUE_METADATA_DATATYPE_INT)
				{
					values[i]=Integer.parseInt(tmpvalue.toString());
				}
				else if(ma.getDataType()==MetaConsts.VALUE_METADATA_DATATYPE_LONG)
				{
				  values[i]=Long.parseLong(tmpvalue.toString());
				}
				else if(ma.getDataType()==MetaConsts.VALUE_METADATA_DATATYPE_FLOAT)
				{
					values[i]=Float.parseFloat(tmpvalue.toString());
				}
				else
					values[i]=tmpvalue;
			}
		}
		return values;
	}

	private Object[] getMetaComponentValues(MetaComponentObject metaComponentObject) {
		MetaComponent metaComponent = metaComponentObject.getMetaComponent();
		LinkedList<PropertyDescriptor> attrList = getComponentPropertyColumnNames(metaComponent);
		Object[] values = new Object[attrList.size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = metaComponentObject.getValue(attrList.get(i).getMetaAttribute().getAttributeName());
		}
		return values;
	}

	public void update(MetaObject metaObject) throws MetaPlatformPersisterException, SqlExecutorException {
		ClassTableDescriptor descriptor = metaClassManager.getClassTableDescriptor(metaObject.getMetaClass());
		String key = descriptor.getPrimitiveProperty().getMetaAttribute().getAttributeName();
		MetaObject oldObject = findMetaObjectById(metaObject.getMetaClass(), key, metaObject.getValue(key), metaObject
				.getFeches());
		String versionColumnName = descriptor.getVersionProperty().getMetaAttribute().getAttributeName();
		if(oldObject.getInt(versionColumnName) == null){
			oldObject.setValue(versionColumnName, 0);
			if(metaObject.getInt(versionColumnName) == null){
				//如果界面改了version，当做报错处理
				metaObject.setValue(versionColumnName, 0);
			}
		}
		if (oldObject.getInt(versionColumnName) > metaObject.getInt(versionColumnName)) {
			String classname = metaObject.getMetaClass().getClassName();
			int newVersion = metaObject.getInt(versionColumnName);
			int oldVersion = oldObject.getInt(versionColumnName);
			String message = constructVersionConflictMessage(classname, metaObject.getValue(key).toString(),
					oldVersion, newVersion);
			throw new MetaPlatformPersisterException(message);
		} else {
			if (metaObject.getMetaClass().findMetaAttribute("MODIFYDATE") != null) {
				metaObject.setValue("MODIFYDATE", new Date());
			}
			updateMetaObject(metaObject, oldObject);
		}
	}

	private String constructVersionConflictMessage(String classname, String objectId, int oldVersion, int newVersion) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Can not execute update ");
		buffer.append(" MetaObject Info: MetaClass_Name[").append(classname).append("]");
		buffer.append(" MetaObject_ID[");
		buffer.append(objectId);
		buffer.append("]");
		buffer.append("Cause update version:").append(newVersion);
		buffer.append(" is less than old version:").append(oldVersion);
		String message = buffer.toString();
		return message;
	}

	private MetaObject findMetaObjectById(MetaClass metaClass, String key, Object value, String[] feches)
			throws MetaPlatformPersisterException, SqlExecutorException {
		QueryCriteria criteria = new QueryCriteria(metaClass);
		criteria.setBegin(0);
		criteria.setEnd(1);
		criteria.addExpression(QueryExpressionUtil.equals(key, value));
		if (feches != null) {
			for (String fech : feches) {
				criteria.addChildrenFetch(fech);
			}
		}
		MetaObjectQueryResult result = query(criteria);
		if (result.getList() != null && result.getList().size() == 1) {
			return result.getList().get(0);
		}

		return null;
	}

	public void insert(MetaObject metaObject) throws MetaPlatformPersisterException, SqlExecutorException {
		if (metaObject!=null)insertMetaObject(metaObject, null, null);
	}

	private void updateMetaObject(MetaObject metaObject, MetaObject usedObject) throws MetaPlatformPersisterException,
			SqlExecutorException {
		MetaClass metaClass = metaObject.getMetaClass();
		ClassTableDescriptor descriptor = metaClassManager.getClassTableDescriptor(metaClass);

		// update metaobject core table
		Object idValue = metaObject.getValue(descriptor.getPrimitiveProperty().getMetaAttribute().getAttributeName());
		int version = updateCommonTableValues(metaObject, usedObject, descriptor, idValue);
		if (version == -1) {
			String versionColumnName = descriptor.getVersionProperty().getMetaAttribute().getAttributeName();
			String key = descriptor.getPrimitiveProperty().getMetaAttribute().getAttributeName();
			MetaObject oldObject = findMetaObjectById(metaObject.getMetaClass(), key, metaObject.getValue(key), null);
			String classname = metaObject.getMetaClass().getClassName();
			int newVersion = metaObject.getInt(versionColumnName);
			int oldVersion = oldObject.getInt(versionColumnName);
			String message = constructVersionConflictMessage(classname, metaObject.getValue(key).toString(),
					oldVersion, newVersion);
			throw new MetaPlatformPersisterException(message);
		}

		// update metaobject sa tables
		for (RelationDescriptor relationDescriptor : descriptor.getPlainTableRelations()) {
			TableDescriptor tableDescriptor = relationDescriptor.getOppositeTable();
			tableDescriptor.setPrimitiveProperty(getPlainTableID(tableDescriptor, metaClass));

			updateCommonTableValues(metaObject, usedObject, tableDescriptor, idValue);
		}

		// update component
		for (ComponentRelationDescriptor componentRelationDescriptor : descriptor.getRelationComponentTables()) {
			MetaComponentRelation metaComponentRelation = componentRelationDescriptor.getMetaComponentRelation();
			MetaComponentObject metaComponentObjectNew = metaObject.getRelateComponentObject(metaComponentRelation);
			MetaComponentObject metaComponentObjectOld = usedObject.getRelateComponentObject(metaComponentRelation);
			if (metaComponentObjectNew != null && metaComponentObjectOld != null) {
				TableDescriptor tableDescriptor = componentRelationDescriptor.getOppositeTable();
				Object componentIdValue = metaComponentObjectOld.getValue(tableDescriptor.getPrimitiveProperty()
						.getMetaAttribute().getAttributeName());
				updateCommonTableValues(metaComponentObjectNew, metaComponentObjectOld, tableDescriptor,
						componentIdValue);
			}
			if (metaComponentObjectNew == null && metaComponentObjectOld != null) {
				deleteComponent(metaComponentObjectOld, componentRelationDescriptor.getOppositeTable());
			}
			if (metaComponentObjectNew != null && metaComponentObjectOld == null) {
				insertMetaComponentObject(metaObject, metaComponentRelation, metaComponentObjectNew);
			}
		}

		for (ClassRelationDescriptor classRelationDescriptor : descriptor.getRelationClassTables()) {
			MetaClassRelation metaClassRelation = classRelationDescriptor.getMetaClassRelation();

			if (CollectionUtil.notContainInList(metaObject.getFeches(), metaClassRelation.getAlias())) {
				deleteEntityRelation(usedObject, metaClassRelation);

				List<MetaClassRelation> objectRelations = metaClass.getMetaClassRelations();

				List<MetaObject> metaObjects = metaObject.getRelateObject(metaClassRelation);
				if (metaObjects != null && metaObjects.size() > 0) {
					for (MetaObject temp : metaObjects) {
						MetaObject oldObject = isAlreadyPersisted(temp);
						if (oldObject == null) {
							insert(temp);
						} else if (metaClassRelation.getCascade() == MetaConsts.VALUE_METADATA_BOOLEAN_TRUE) {
							update(temp);
						}
					}
				}
				updateEntityRelation(metaObject, metaClassRelation);
			}
		}
		metaObject.setValue(descriptor.getVersionProperty().getMetaAttribute().getTableColumnName(), version);
	}

	private int updateCommonTableValues(MetaData metaObject, MetaData usedObject, TableDescriptor descriptor,
			Object idValue) throws SqlExecutorException {

		int version = 0;
		String tableName = descriptor.getTable();
		List<PropertyDescriptor> properties = descriptor.getMetaProerties();
		String primitiveKey = descriptor.getPrimitiveProperty().getColumn();
		Update update = new Update(oracle9dialect);
		update.setTableName(tableName);
		update.addWhereColumn(primitiveKey);

		List<Object> nodes = new ArrayList<Object>();
		for (PropertyDescriptor property : properties) {
			if (isAttributeChanged(metaObject, usedObject, property.getMetaAttribute())) {
				if (descriptor.getVersionProperty() != null && descriptor.getVersionProperty() == property) {
					continue;
				}
				if (property.getColumn().equals(primitiveKey)) {
					continue;
				}
				update.addColumn(property.getMetaAttribute().getTableColumnName());
				nodes.add(metaObject.getValue(property.getMetaAttribute().getAttributeName()));
			}
		}
		if (descriptor.getVersionProperty() != null) {
			version = metaObject.getInt(descriptor.getVersionProperty().getMetaAttribute().getAttributeName()) + 1;
			update.addColumn(descriptor.getVersionProperty().getMetaAttribute().getTableColumnName());
			nodes.add(version);
		}

		if (nodes.size() > 0) {
			nodes.add(idValue);

			if (descriptor.getVersionProperty() != null) {
				update.addWhereColumn(descriptor.getVersionProperty().getColumn());
				nodes.add(version - 1);
			}
			
			forGisTempMethod(metaObject, usedObject, descriptor, update, nodes);

			String sql = update.toStatementString();

			int updateCount = executor.update(sql, nodes.toArray());
			if (descriptor.getVersionProperty() != null && updateCount < 1) {
				version = -1;
			}
		}

		return version;
	}

	private boolean isAttributeChanged(MetaData metaData, MetaData usedData, MetaAttribute attribute) {
		boolean isChanged = false;
		Object newValue = metaData.getValue(attribute.getAttributeName());
		Object oldValue = usedData.getValue(attribute.getAttributeName());
		if (newValue != null && oldValue != null && !newValue.equals(oldValue)) {
			isChanged = true;
		}
		if (newValue != null && oldValue == null) {
			isChanged = true;
		}
		if (newValue == null && oldValue != null) {
			isChanged = true;
		}

		return isChanged;
	}
	
	private boolean isAttributeChanged(MetaData metaData, MetaData usedData, String attributeName) {
		boolean isChanged = false;
		Object newValue = metaData.getValue(attributeName);
		Object oldValue = usedData.getValue(attributeName);
		if (newValue != null && oldValue != null && !newValue.equals(oldValue)) {
			isChanged = true;
		}
		if (newValue != null && oldValue == null) {
			isChanged = true;
		}
		if (newValue == null && oldValue != null) {
			isChanged = true;
		}

		return isChanged;
	}

	private List<MetaAttribute> findChangedAttribute(MetaData metaData, MetaData usedData) {
		List<MetaAttribute> attributes = new ArrayList<MetaAttribute>();
		for (MetaAttribute attribute : usedData.getAttributeList()) {
			Object newValue = metaData.getValue(attribute.getAttributeName());
			Object oldValue = usedData.getValue(attribute.getAttributeName());
			if (newValue != null && oldValue != null && !newValue.equals(oldValue)) {
				attributes.add(attribute);
			}
			if (newValue != null && oldValue == null) {
				attributes.add(attribute);
			}
			if (newValue == null && oldValue != null) {
				attributes.add(attribute);
			}
		}

		return attributes;
	}

	public MetaObjectQueryResult query(QueryCriteria queryCriteria) throws MetaPlatformPersisterException,
			SqlExecutorException {
		Query query = new Query(oracle9dialect);
		MetaClass metaClass = queryCriteria.getMetaClass();
		ClassTableDescriptor descriptor = metaClassManager.getClassTableDescriptor(metaClass);

		Vector<String> orderByAscs = new Vector<String>();
		Vector<String> orderByDescs = new Vector<String>();
		Map<MetaAttribute, String> dbNameMap = new HashMap<MetaAttribute, String>();
		String coreTableName = query.setCoreTable(descriptor.getTable(), descriptor.getPrimitiveProperty().getColumn(),
				filterColumnNames(descriptor.getMetaProerties(), (NamingMetaObjectParseStrategy) queryCriteria
						.getNamingParseStrategy()));
		loadDbNameMap(descriptor.getMetaProerties(), dbNameMap, coreTableName);
		// 待查询table别名
		String coreTableAliasName = ((NamingMetaObjectParseStrategy) queryCriteria.getNamingParseStrategy())
				.getTableAliasName(descriptor.getTable());
		addTableConditions(queryCriteria, query, metaClass, coreTableAliasName, descriptor.getTable(), orderByAscs,
				orderByDescs, true);

		addPlainTableJoinRelation(queryCriteria, metaClass, query, descriptor, dbNameMap, coreTableAliasName,
				orderByAscs, orderByDescs);
		addComponentJoinRelation(queryCriteria, metaClass, query, descriptor, dbNameMap, coreTableAliasName,
				orderByAscs, orderByDescs);
		addMetaClassRelationConditions(queryCriteria, query, descriptor, dbNameMap, coreTableAliasName, orderByAscs,
				orderByDescs);

		Object[] params = query.toQueryParams();
		String[] orders = assembleOrderColumn(orderByAscs, orderByDescs);
		String[] isAsc = assembleOrderAsc(orderByAscs, orderByDescs);
		String sql = query.toQueryStatementString(orders);

		if (queryCriteria.getOrderByAscs() != null && queryCriteria.getOrderByAscs().size() > 0) {
			for (String ascsTemp : queryCriteria.getOrderByAscs()) {
				if (ascsTemp.equalsIgnoreCase("dbms_random.value")) {
					if (orders.length > 0) {
						String[] isAscTemp = new String[isAsc.length + 1];
						for (int i = 0; i < isAscTemp.length; i++) {
							if (i == (isAscTemp.length - 1)) {
								isAscTemp[i] = "";
							} else {
								isAscTemp[i] = isAsc[i];
							}
						}
						isAsc = isAscTemp;

						String[] ordersTemp = new String[orders.length + 1];
						for (int i = 0; i < ordersTemp.length; i++) {
							if (i == (ordersTemp.length - 1)) {
								ordersTemp[i] = ascsTemp;
							} else {
								ordersTemp[i] = orders[i];
							}
						}
						orders = ordersTemp;
					} else {
						isAsc = new String[] { "" };
						orders = new String[] { ascsTemp };
					}
				}
			}
		}

		int endPoint = queryCriteria.getDefaultPageCount();
		if (queryCriteria.getEnd() > 0) {
			endPoint = queryCriteria.getEnd();
		}
		String executedSql = executor.getExecutedSql(sql, params);
		SqlQueryResult result;
		if (queryCriteria.isOnlyQueryCount()) {
			StringBuffer buffer = new StringBuffer();

			buffer.append("select Count(*) from (");
			buffer.append(sql);
			buffer.append(" )  ");

			executedSql = executor.getExecutedSql(buffer.toString(), params);
			if (!queryCriteria.isNotExecute()) {
				result = new SqlQueryResult(executor.queryForInt(buffer.toString(), params), null);
			} else {
				result = new SqlQueryResult(0, null);
			}
		} else if (queryCriteria.getEnd() == 0 && (orders == null || orders.length == 0)) {
			if (!queryCriteria.isNotExecute()) {
				List<Map<String, Object>> list = executor.query(sql, params);
				result = new SqlQueryResult(list.size(), list);
			} else {
				result = new SqlQueryResult(0, null);
			}
		} else {
			if (!queryCriteria.isNotExecute()) {
				if (queryCriteria.isIgnoreQueryCount()) {
					result = executor.query(sql, params, queryCriteria.getBegin(), endPoint - queryCriteria.getBegin()
							+ 1, orders, isAsc, false);
				} else {
					result = executor.query(sql, params, queryCriteria.getBegin(), endPoint - queryCriteria.getBegin()
							+ 1, orders, isAsc);
				}
			} else {
				result = new SqlQueryResult(0, null);
			}
		}
		MetaObjectQueryResult finalResult = assembleMetaObjectBatch(queryCriteria, metaClass, result, null, null, null);

		finalResult.setSql(executedSql);

		return finalResult;
	}

	private void addTableConditions(QueryCriteria queryCriteria, Query query, MetaClass metaClass,
			String coreTableName, String targetTableName, Vector<String> orderByAscs, Vector<String> orderByDescs,
			boolean isNeedOrder) throws MetaPlatformPersisterException {
		for (Criterion expression : queryCriteria.getExpressions()) {

			Object[] keys = expression.getKeys();
			boolean needAdd = false;
			String aliasSql = expression.getAliasSql();
			for (Object key : keys) {
				MetaAttribute attribute = metaClass.findMetaAttribute((String) key);
				if (attribute == null) {
					logger.error("XM_ENTITYDESCRIPTOR could not find attribute : " + key + "  in entity : "
							+ metaClass.getClassName());
					throw new MetaPlatformPersisterException("XM_ENTITYDESCRIPTOR could not find attribute : " + key
							+ "  in entity : " + metaClass.getClassName());
				}

				if (attribute.getTableName().equals(targetTableName)) {
					String columnName = attribute.getTableColumnName();
					String replace = makeUpReplace(coreTableName, columnName);
                    if (!aliasSql.contains(replace)) {
                        // aliasSql = aliasSql.replaceAll((String) key,
                        // replace);
                        // modified by zhengjian on 11-05-08
                        aliasSql = aliasSql
                                .replaceAll(
                                        "([^_A-Za-z0-9.])" + key
                                                + "([^_A-Za-z0-9])",
                                        "$1" + replace + "$2")
                                .replaceAll("^" + key + "([^_A-Za-z0-9])",
                                        replace + "$1")
                                .replaceAll("([^_A-Za-z0-9.])" + key + "$",
                                        "$1" + replace);
                    }
					needAdd = true;
				}
			}
			if (needAdd) {
				query.addCondition(aliasSql, expression.getParams());
			}

			// MetaAttribute attribute =
			// metaClass.findMetaAttribute(expression.getKey());
			// if(attribute.getTableName().equals(targetTableName)) {
			// Object [] params = expression.getParams();
			// String aliasSql = expression.getAliasSql();
			// String columnName = attribute.getTableColumnName();
			// String replace = makeUpReplace(coreTableName, columnName);
			//
			// aliasSql = aliasSql.replaceAll(expression.getKey(), replace);
			// if(expression.getAnotherKeys() != null &&
			// !expression.getAnotherKey().equals(expression.getKey())) {
			// MetaAttribute anotherAttribute =
			// metaClass.findMetaAttribute(expression.getAnotherKey());
			// String anotherColumnName = anotherAttribute.getTableColumnName();
			// String anotherReplace = makeUpReplace(coreTableName,
			// anotherColumnName);
			// aliasSql = aliasSql.replaceAll(expression.getAnotherKey(),
			// anotherReplace);
			// }
			// query.addCondition(aliasSql, params);
			// }
		}

		if (isNeedOrder) {
			addMetaClassOrder(queryCriteria, metaClass, coreTableName, targetTableName, orderByAscs, orderByDescs);
		}
	}

	private void addMetaClassOrder(QueryCriteria queryCriteria, MetaClass metaClass, String coreTableName,
			String targetTableName, Vector<String> orderByAscs, Vector<String> orderByDescs) {
		for (String columnWithTable : queryCriteria.getOrderByAscs()) {
			MetaAttribute attribute = metaClass.findMetaAttribute(columnWithTable);
			if (attribute != null && attribute.getTableName().equals(targetTableName)) {
				orderByAscs.add(coreTableName + "." + columnWithTable);
			}
		}

		for (String columnWithTable : queryCriteria.getOrderByDescs()) {
			MetaAttribute attribute = metaClass.findMetaAttribute(columnWithTable);
			if (attribute != null && attribute.getTableName().equals(targetTableName)) {
				orderByDescs.add(coreTableName + "." + columnWithTable);
			}
		}
	}

	private void addComponentTableConditions(QueryCriteria queryCriteria, Query query, MetaComponent metaComponent,
			String coreTableName, String targetTableName, Vector<String> orderByAscs, Vector<String> orderByDescs) {
		for (Criterion expression : queryCriteria.getExpressions()) {

			Object[] keys = expression.getKeys();

			String aliasSql = expression.getAliasSql();
			boolean needAdd = false;
			for (Object key : keys) {

				MetaAttribute attribute = metaComponent.findMetaAttribute((String) key);
				if (attribute.getTableName().equals(targetTableName)) {
					String columnName = attribute.getTableColumnName();
					String replace = makeUpReplace(coreTableName, columnName);
					if (!aliasSql.contains(replace)) {
//						aliasSql = aliasSql.replaceAll((String) key, replace);
                        // modified by zhengjian on 11-05-08
                        aliasSql = aliasSql
                                .replaceAll(
                                        "([^_A-Za-z0-9.])" + key
                                                + "([^_A-Za-z0-9])",
                                        "$1" + replace + "$2")
                                .replaceAll("^" + key + "([^_A-Za-z0-9])",
                                        replace + "$1")
                                .replaceAll("([^_A-Za-z0-9.])" + key + "$",
                                        "$1" + replace);
					}
					needAdd = true;
				}
			}
			if (needAdd) {
				query.addCondition(aliasSql, expression.getParams());
			}

			/**
			 * MetaAttribute attribute =
			 * metaComponent.findMetaAttribute(expression.getKey());
			 * if(attribute.getTableName().equals(targetTableName)) { Object []
			 * params = expression.getParams(); String aliasSql =
			 * expression.getAliasSql(); String columnName =
			 * attribute.getTableColumnName(); String replace =
			 * makeUpReplace(coreTableName, columnName);
			 * 
			 * aliasSql = aliasSql.replaceAll(expression.getKey(), replace);
			 * if(expression.getAnotherKey() != null) { MetaAttribute
			 * anotherAttribute =
			 * metaComponent.findMetaAttribute(expression.getAnotherKey());
			 * String anotherColumnName = anotherAttribute.getTableColumnName();
			 * String anotherReplace = makeUpReplace(coreTableName,
			 * anotherColumnName); aliasSql =
			 * aliasSql.replaceAll(expression.getAnotherKey(), anotherReplace); }
			 * query.addCondition(aliasSql, params); }
			 */
		}

		addMetaComponentOrder(queryCriteria, metaComponent, coreTableName, targetTableName, orderByAscs, orderByDescs);
	}

	private void addMetaComponentOrder(QueryCriteria queryCriteria, MetaComponent metaClass, String coreTableName,
			String targetTableName, Vector<String> orderByAscs, Vector<String> orderByDescs) {
		for (String columnWithTable : queryCriteria.getOrderByAscs()) {
			MetaAttribute attribute = metaClass.findMetaAttribute(columnWithTable);
			if (attribute != null && attribute.getTableName().equals(targetTableName)) {
				orderByAscs.add(coreTableName + "." + columnWithTable);
			}
		}

		for (String columnWithTable : queryCriteria.getOrderByDescs()) {
			MetaAttribute attribute = metaClass.findMetaAttribute(columnWithTable);
			if (attribute != null && attribute.getTableName().equals(targetTableName)) {
				orderByAscs.add(coreTableName + "." + columnWithTable);
			}
		}
	}

	private String getColumnAliasName(NamingMetaObjectParseStrategy nmps, MetaAttribute attribute) {
		String tableAlias = nmps.getTableAliasName(attribute.getTableName());
		if (nmps.getColumnAliasName(attribute.getAttributeName()).length() + tableAlias.length() >= 30) {
			return tableAlias + "_"
					+ nmps.getColumnAliasName(attribute.getAttributeName()).substring(0, 29 - tableAlias.length());
		} else {
			return tableAlias + "_" + nmps.getColumnAliasName(attribute.getAttributeName());
		}
	}

	private MetaObjectQueryResult assembleMetaObject(QueryCriteria queryCriteria, Query select, MetaClass metaClass,
			SqlQueryResult result, Map<MetaAttribute, String> dbNameMap) throws MetaPlatformPersisterException,
			SqlExecutorException {
		List<MetaObject> list = new ArrayList<MetaObject>();
		MetaObjectQueryResult finalResult = new MetaObjectQueryResult(result.getTotal(), list);

		NamingMetaObjectParseStrategy nmps = (NamingMetaObjectParseStrategy) queryCriteria.getNamingParseStrategy();
		if (result != null && result.getList() != null) {
			for (Map<String, Object> item : result.getList()) {
				DefaultMetaObject metaObject = new DefaultMetaObject(metaClass);

				for (MetaAttribute attribute : metaClass.getMetaAttributes()) {
					// 属性对应SQL中的别名
					String name = getColumnAliasName(((NamingMetaObjectParseStrategy) queryCriteria
							.getNamingParseStrategy()), attribute);
					Object value = item.get(name);
					setValue(metaObject, attribute, value);
				}
				if (queryCriteria.isLoadSubClass() && metaObject.getValue("METACATEGORY") != null
						&& !metaObject.getValue("METACATEGORY").equals("")) {
					MetaClass subMetaClass = metaClassManager.getMetaClass(metaObject.getString("METACATEGORY"));
					if (subMetaClass != null) {
						metaObject.setMetaClass(subMetaClass);
					}
				}
				for (MetaComponentRelation metaComponentRelation : metaClass.getMetaComponentRelations()) {
					MetaComponentObject metaComponentObject = new DefaultMetaComponentObject(metaComponentRelation
							.getRelationMetaComponent());
					Boolean needInsert = false;
					for (MetaAttribute attribute : metaComponentRelation.getRelationMetaComponent().getMetaAttributes()) {
						String name = getColumnAliasName(((NamingMetaObjectParseStrategy) nmps
								.getRelationNamingParseStrategy(metaComponentRelation.getAlias())), attribute);
						Object value = item.get(name);
						if (value != null) {
							setValue(metaComponentObject, attribute, value);
							needInsert = true;
						}
					}
					if (needInsert) {
						metaObject.setComponentObject(metaComponentObject, metaComponentRelation);
					}
				}

				String category = null;
				if (queryCriteria.isLoadSubClass()) {
					category = metaObject.getString("METACATEGORY");
				} else {
					category = metaClass.getClassName();
				}
				// System.out.println(category);
				if (category != null && !category.equals(metaClass.getClassName())) {
					MetaClass subMetaClass = metaClassManager.getMetaClass(category);
					if (subMetaClass != null && subMetaClass.getParentMetaClass() == metaClass) {
						QueryCriteria subQueryCriteria = new QueryCriteria(subMetaClass);
						String idColumnName = metaClassManager.getClassTableDescriptor(subMetaClass)
								.getPrimitiveProperty().getMetaAttribute().getAttributeName();
						subQueryCriteria.addExpression(QueryExpressionUtil.equals(idColumnName, metaObject
								.getValue(idColumnName)));
						subQueryCriteria.setEnd(0);
						MetaObjectQueryResult subResult = query(subQueryCriteria);
						if (subResult.getList() != null && subResult.getList().size() > 0) {
							metaObject = (DefaultMetaObject) subResult.getList().get(0);
						}
					}
				}
				list.add(metaObject);
				QueryAssemble queryAssemble = queryCriteria.getQueryAssemble();
				if (queryAssemble != null) {
					for (QueryAssemble subQueryAssemble : queryAssemble.getChilds()) {
						// 拼装编码中N:1的只查询一次，1：N还需再查询一次
						String relationCode = subQueryAssemble.getCurrentRelationCode();
						metaObject.addFech(relationCode);
						MetaClassRelation related = metaClass.findMetaClassRelation(relationCode);
						MetaClass relatedClass = related.getRelationMetaClass();
						QueryCriteria relatedCriteria = new QueryCriteria(relatedClass);
						relatedCriteria.setQueryAssemble(subQueryAssemble);
						// Next Relation from relatedClass
						MetaClassRelation relation = null;
						relation = findRelationByFetch(relationCode, relatedClass);
						if (relation == null) {
							throw new RuntimeException("Can Not Find MetaClassRelation For Code : " + relationCode);
						}
						ClassTableDescriptor mainDes = metaClassManager.getClassTableDescriptor(metaClass);
						ClassRelationDescriptor relationDesc = this.metaClassManager
								.getClassRelationDescriptor(relatedClass.findMetaClassRelation(related.getAlias()));
						if (queryCriteria.isSimpleLoadRelation()
								&& related.getRelationType() == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_N2ONE
								&& relationDesc.getJoinTableName().equals(mainDes.getTable())) {
							// 支持多层嵌套
							initSimpleRelatedObject(nmps, item, metaObject, queryAssemble, relationCode, metaClass);
						} else {
							String primitiveKey = metaClassManager.getClassRelationDescriptor(relation)
									.getOppositeTable().getPrimitiveProperty().getMetaAttribute().getAttributeName();
							relatedCriteria.addRelationQueryCriteria(relation).addExpression(
									QueryExpressionUtil.equals(primitiveKey, metaObject.getValue(primitiveKey)));
							MetaObjectQueryResult relatedResult = query(relatedCriteria);
							metaObject.setRelateObject(relatedResult.getList(), related);
						}
					}
				} else {
					// 持久化的时候用到Feches
					metaObject.setFeches(queryCriteria.getChildrenFetchs().toArray(
							new String[queryCriteria.getChildrenFetchs().size()]));
					Vector<String> fetches = queryCriteria.getChildrenFetchs();
					for (String fetch : fetches) {
						MetaClassRelation related = metaClass.findMetaClassRelation(fetch);
						MetaClass relatedClass = related.getRelationMetaClass();
						QueryCriteria relatedCriteria = new QueryCriteria(relatedClass);

						MetaClassRelation relation = null;
						relation = findRelationByFetch(fetch, relatedClass);

						if (relation == null)
							throw new RuntimeException("Can Not Find MetaClassRelation For Code : " + fetch);

						for (QueryCriteria criteria : queryCriteria.getRelationQueryCriterias()) {
							if (criteria.getMetaClassRelation() == related) {
								relatedCriteria.getExpressions().addAll(criteria.getExpressions());
								relatedCriteria.setLoadSubClass(criteria.isLoadSubClass());
								relatedCriteria.setSimpleLoadRelation(criteria.isSimpleLoadRelation());
								for (String subFech : criteria.getChildrenFetchs())
									relatedCriteria.addChildrenFetch(subFech);
								for (String orderField : criteria.getOrderByAscs())
									relatedCriteria.addOrderByAsc(orderField);
								for (String orderField : criteria.getOrderByDescs())
									relatedCriteria.addOrderByDesc(orderField);
								break;
							}
						}
						ClassTableDescriptor mainDes = metaClassManager.getClassTableDescriptor(metaClass);
						ClassRelationDescriptor relationDesc = this.metaClassManager
								.getClassRelationDescriptor(relatedClass.findMetaClassRelation(related.getAlias()));
						if (queryCriteria.isSimpleLoadRelation()
								&& related.getRelationType() == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_N2ONE
								&& relationDesc.getJoinTableName().equals(mainDes.getTable())) {
							// &&
							// relatedCriteria.getChildrenFetchs().size()<=0){
							// 支持多层嵌套
							initSimpleRelatedObject(item, metaObject, queryCriteria, fetch);
						} else {
							String primitiveKey = metaClassManager.getClassRelationDescriptor(relation)
									.getOppositeTable().getPrimitiveProperty().getMetaAttribute().getAttributeName();
							relatedCriteria.addRelationQueryCriteria(relation).addExpression(
									QueryExpressionUtil.equals(primitiveKey, metaObject.getValue(primitiveKey)));

							MetaObjectQueryResult relatedResult = query(relatedCriteria);
							metaObject.setRelateObject(relatedResult.getList(), related);
						}
					}
				}
			}
		}
		return finalResult;
	}

	private MetaObjectQueryResult assembleMetaObjectBatch(QueryCriteria queryCriteria, MetaClass metaClass,
			SqlQueryResult result, MetaClassRelation related, MetaClass prevMetaClass,
			List<MetaObject> prevMetaObjectList) throws MetaPlatformPersisterException, SqlExecutorException {
		List<MetaObject> list = new ArrayList<MetaObject>();
		MetaObjectQueryResult finalResult = new MetaObjectQueryResult(result.getTotal(), list);

		if (result.getList() != null && result.getList().size() > 0) {
			long total = result.getList().size();
			long count = total / 1000;
			if (total % 1000 > 0) {
				count++;
			}
			if (count == 1) {
				assembleMetaObjectOneGroup(queryCriteria, metaClass, result.getList(), related, prevMetaClass,
						prevMetaObjectList, list);
			} else if (count > 1) {
				for (int i = 0; i < count; i++) {
					List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
					List<MetaObject> oneGroup = new ArrayList<MetaObject>();
					int start = i * 1000;
					long end = (i == (count - 1)) ? (start + ((total % 1000)==0?1000:(total % 1000))) : (start + 1000);
					for (int j = start; j < end; j++) {
						if (result.getList().get(j) != null) {
							itemList.add(result.getList().get(j));
						}
					}
					assembleMetaObjectOneGroup(queryCriteria, metaClass, itemList, related, prevMetaClass,
							prevMetaObjectList, oneGroup);
					list.addAll(oneGroup);
				}
			}
		} else {
			// 兼容没有做非空判断的老代码
			if (prevMetaObjectList != null && related != null) {
				for (MetaObject metaObject : prevMetaObjectList) {
					metaObject.setRelateObject(new ArrayList<MetaObject>(), related.getAlias());
				}
			}
		}
		return finalResult;
	}

	private void assembleMetaObjectOneGroup(QueryCriteria queryCriteria, MetaClass metaClass,
			List<Map<String, Object>> itemList, MetaClassRelation related, MetaClass prevMetaClass,
			List<MetaObject> prevMetaObjectList, List<MetaObject> selfList) throws MetaPlatformPersisterException,
			SqlExecutorException {
		NamingMetaObjectParseStrategy nmps = (NamingMetaObjectParseStrategy) queryCriteria.getNamingParseStrategy();
		Map<Object, List<MetaObject>> prevMetaObjectMappingList = new HashMap<Object, List<MetaObject>>();
		String prevRelationCode = null;
		if (itemList != null && itemList.size() > 0) {
			String columnAlias = null;
			if (prevMetaObjectList != null) {
				prevRelationCode = related.getAlias();
				ClassRelationDescriptor crd = metaClassManager.getClassRelationDescriptor(related);

				// 上个对象 1:N
				if (related.getRelationType() == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_ONE2N) {
					columnAlias = getColumnAliasName(nmps, metaClass.findMetaAttribute(crd.getShelfColumn()));
				} else if (related.getRelationType() == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_N2ONE) {
					columnAlias = getColumnAliasName(nmps, metaClass.getPrimitiveAttribute());
				} else {
					columnAlias = "LETHE_OTHERID";
				}
			}
			for (Map<String, Object> item : itemList) {
				DefaultMetaObject metaObject = new DefaultMetaObject(metaClass);
				selfList.add(metaObject);
				for (MetaAttribute attribute : metaClass.getMetaAttributes()) {
					// 属性对应SQL中的别名
					String name = getColumnAliasName(nmps, attribute);
					Object value = item.get(name);
					setValue(metaObject, attribute, value);
				}

				// 批量拼装上个对象的关联对象
				if (prevMetaObjectList != null) {
					Object value = item.get(columnAlias);
					if (value instanceof BigDecimal) {
						value = ((BigDecimal) value).longValue();
					}
					List<MetaObject> relList = prevMetaObjectMappingList.get(value);
					if (relList == null) {
						relList = new ArrayList<MetaObject>();
						prevMetaObjectMappingList.put(value, relList);
					}
					relList.add(metaObject);
				}
			}
		}

		if (selfList.size() > 0) {
			if (queryCriteria.isLoadSubClass()) {
				// 拼装卫星表
				initPlainTable(metaClass, selfList);
			}

			// 拼装组件
			initComponentTable(metaClass, selfList);

			// 拼装关系对象
			initRelationObject(queryCriteria, metaClass, selfList);

			// 拼装上一个对象的关联
			if (prevMetaObjectList != null) {
				String keyName = prevMetaClass.getPrimitiveAttribute().getAttributeName();
				ClassRelationDescriptor crd = metaClassManager.getClassRelationDescriptor(related);
				if (related.getRelationType() == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_N2ONE) {
					keyName = crd.getOppositeColumn();
				}
				for (Iterator<Object> iterator = prevMetaObjectMappingList.keySet().iterator(); iterator.hasNext();) {
					Object priObject = iterator.next();
					for (MetaObject metaObject : prevMetaObjectList) {
						if (metaObject.getValue(keyName) != null && metaObject.getValue(keyName).equals(priObject)) {

							List<MetaObject> tempList = prevMetaObjectMappingList.get(priObject);
							// 重新定位引用
							if (tempList != null) {
								for (int i = 0; i < tempList.size(); i++) {
									MetaObject tempMetaObject = tempList.get(i);
									for (MetaObject metaObject2 : selfList) {
										if (metaObject2.getValue(metaClass.getPrimitiveAttribute().getAttributeName())
												.equals(
														tempMetaObject.getValue(metaClass.getPrimitiveAttribute()
																.getAttributeName()))) {
											tempList.set(i, metaObject2);
											continue;
										}
									}
								}
							} else {
								// 兼容老的代码，没做非空判断的
								tempList = new ArrayList<MetaObject>();
							}
							

							//metaObject.setRelateObject(tempList, prevRelationCode);
							//有超过1000会有bug
							//fiexed by zhengjian
							if (metaObject.getRelateObject(prevRelationCode) == null) {
								metaObject.setRelateObject(tempList, prevRelationCode);
							} else {
								metaObject.getRelateObject(prevRelationCode).addAll(tempList);
							}
						}
					}
				}
			}
		}
	}

	private void initComponentTable(MetaClass metaClass, List<MetaObject> metaObjectList) throws SqlExecutorException {
		String idName = metaClass.getPrimitiveAttribute().getAttributeName();
		// 主对象ID List

		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < metaObjectList.size(); i++) {
			if (i > 0) {
				buffer.append(",");
			}
			buffer.append(metaObjectList.get(i).getValue(idName));
		}

		List<MetaComponentRelation> metaComponentRelationList = metaClass.getMetaComponentRelations();
		for (MetaComponentRelation metaComponentRelation : metaComponentRelationList) {
			// 遍历所有的组件
			String componentTable = metaClassManager.getComponentTableDescriptor(
					metaComponentRelation.getRelationMetaComponent()).getTable();
			String componentSql = "SELECT * FROM " + componentTable + " WHERE ENTITYID IN (" + buffer.toString() + ")";
			List<Map<String, Object>> componentData = executor.query(componentSql, null);
			for (Map<String, Object> item : componentData) {
				MetaComponentObject metaComponentObject = new DefaultMetaComponentObject(metaComponentRelation
						.getRelationMetaComponent());
				Boolean needInsert = false;
				for (MetaAttribute attribute : metaComponentRelation.getRelationMetaComponent().getMetaAttributes()) {
					String name = attribute.getAttributeName();
					Object value = item.get(name);
					if (value != null) {
						setValue(metaComponentObject, attribute, value);
						needInsert = true;
					}
				}
				if (needInsert) {
					for (MetaObject metaObject : metaObjectList) {
						if (item.get("ENTITYID") != null
								&& metaObject.getValue(idName).equals(((BigDecimal) item.get("ENTITYID")).longValue())) {
							metaObject.setComponentObject(metaComponentObject, metaComponentRelation);
							break;
						}
					}
				}
			}
		}
	}

	private void initPlainTable(MetaClass metaClass, List<MetaObject> metaObjectList)
			throws MetaPlatformPersisterException, SqlExecutorException {
		String idColumnName = metaClassManager.getClassTableDescriptor(metaClass).getPrimitiveProperty()
				.getMetaAttribute().getAttributeName();
		Map<String, List<Object>> keyMap = new HashMap<String, List<Object>>();

		// 查找需要拼装的子表
		for (MetaObject metaObject : metaObjectList) {
			if (metaObject.getValue("METACATEGORY") != null && !metaObject.getValue("METACATEGORY").equals("")
					&& !metaObject.getValue("METACATEGORY").equals(metaObject.getMetaClass().getClassName())) {
				List<Object> idList = keyMap.get(metaObject.getValue("METACATEGORY"));
				if (idList == null) {
					idList = new ArrayList<Object>();
					keyMap.put(metaObject.getString("METACATEGORY"), idList);
				}
				idList.add(metaObject.getValue(idColumnName));
			}
		}

		// 批量拼装子表
		for (String subMetaName : keyMap.keySet()) {
			MetaClass subMetaClass = metaClassManager.getMetaClass(subMetaName);
			QueryCriteria subQueryCriteria = new QueryCriteria(subMetaClass);
			subQueryCriteria.addExpression(QueryExpressionUtil.in(idColumnName, keyMap.get(subMetaName)));
			subQueryCriteria.setEnd(0);
			MetaObjectQueryResult subResult = query(subQueryCriteria);
			if (subResult.getList() != null && subResult.getList().size() > 0) {
				String idName = metaClass.getPrimitiveAttribute().getAttributeName();
				for (int i = 0; i < metaObjectList.size(); i++) {
					MetaObject fatherObject = metaObjectList.get(i);
					for (MetaObject subObject : subResult.getList()) {
						if (fatherObject.getValue(idName).equals(subObject.getValue(idName))) {
							metaObjectList.set(i, subObject);
							break;
						}
					}
				}
			}
		}
	}

	private void queryAndfill(QueryCriteria queryCriteria, List<MetaObject> prevMetaObjectList,
			MetaClassRelation related, MetaClass prevMetaClass) throws MetaPlatformPersisterException,
			SqlExecutorException {
		String prevRelationCode = related.getAlias();
		Query query = new Query(oracle9dialect);
		MetaClass metaClass = queryCriteria.getMetaClass();
		ClassTableDescriptor descriptor = metaClassManager.getClassTableDescriptor(metaClass);
		Vector<String> orderByAscs = new Vector<String>();
		Vector<String> orderByDescs = new Vector<String>();
		Map<MetaAttribute, String> dbNameMap = new HashMap<MetaAttribute, String>();
		String coreTableName = query.setCoreTable(descriptor.getTable(), descriptor.getPrimitiveProperty().getColumn(),
				filterColumnNames(descriptor.getMetaProerties(), (NamingMetaObjectParseStrategy) queryCriteria
						.getNamingParseStrategy()));
		loadDbNameMap(descriptor.getMetaProerties(), dbNameMap, coreTableName);
		// 待查询table别名
		String coreTableAliasName = ((NamingMetaObjectParseStrategy) queryCriteria.getNamingParseStrategy())
				.getTableAliasName(descriptor.getTable());

		ClassRelationDescriptor crd = metaClassManager.getClassRelationDescriptor(related);
		if (related.getRelationType() == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_ONE2N) {
			List<Object> idlist = new ArrayList<Object>();
			String primitiveKey = prevMetaClass.getPrimitiveAttribute().getAttributeName();
			for (MetaObject metaObject : prevMetaObjectList) {
				idlist.add(metaObject.getValue(primitiveKey));
			}
			String oppoSite = crd.getShelfColumn();
			queryCriteria.addExpression(QueryExpressionUtil.in(oppoSite, idlist));
		} else if (related.getRelationType() == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_N2ONE) {
			List<Object> idlist = new ArrayList<Object>();
			String oppoSite = crd.getOppositeColumn();
			for (MetaObject metaObject : prevMetaObjectList) {
				if (metaObject.getValue(oppoSite) != null) {
					idlist.add(metaObject.getValue(oppoSite));
				}
			}
			// 比如 网元对应的厂商， 如果厂商ID 都是空 跳出循环
			if (idlist.size() == 0)
				return;
			queryCriteria.addExpression(QueryExpressionUtil.in(metaClass.getPrimitiveAttribute().getAttributeName(),
					idlist));
		} else {

			String primitiveKey = prevMetaClass.getPrimitiveAttribute().getAttributeName();
			StringBuffer ids = new StringBuffer();
			for (int i = 0; i < prevMetaObjectList.size(); i++) {
				MetaObject metaObject = prevMetaObjectList.get(i);
				if (i > 0) {
					ids.append(",");
				}
				ids.append(metaObject.getValue(primitiveKey));
			}
			String middleTable = crd.getJoinTableName();
			String middleTalbeAlias = "LETHE";
			String oppositeKeyColumn = crd.getOppositeColumn();
			String oppositeKey = metaClass.getPrimitiveAttribute().getAttributeName();
			List<String> middleAttrList = new ArrayList<String>();
			middleAttrList.add("OTHERID");
			query.simpleMiddleTableJoinTable(middleTable, middleTalbeAlias, oppositeKeyColumn, coreTableAliasName,
					oppositeKey, crd.getShelfColumn(), ids.toString());
		}

		addPlainTableJoinRelation(queryCriteria, metaClass, query, descriptor, dbNameMap, coreTableAliasName,
				orderByAscs, orderByDescs);
		addTableConditions(queryCriteria, query, metaClass, coreTableAliasName, descriptor.getTable(), orderByAscs,
				orderByDescs, true);

		// 默认的relation 在select 里面没有列的 这里需要列, 对于同一个对象 网元所属父网元 这个方法支持部了
		/**
		 * Vector<QueryCriteria>
		 * relateds = queryCriteria.getRelationQueryCriterias();
		    if (relateds != null) {
		      for(QueryCriteria criteria : relateds) {
		    	  //遍历每一个子查询
		        MetaClassRelation related = criteria.getMetaClassRelation();
		        if(related == null)
		          throw new RuntimeException();
		        MetaClass relatedMetaClass = related.getRelationMetaClass();
		        ClassTableDescriptor relatedDes = metaClassManager.getClassTableDescriptor(relatedMetaClass);
		        ClassRelationDescriptor relation = metaClassManager.getClassRelationDescriptor(related);
		        String tableName = ((NamingMetaObjectParseStrategy)criteria.getNamingParseStrategy()).getTableAliasName(relatedDes.getTable());
		        boolean  isJoinTable = true;
		        
		        //查看 是否有查询条件
		        isJoinTable = isJoinTable(criteria,relatedDes);
		        
		        List<String> columnList = 
		        	filterColumnNames(relatedDes.getMetaProerties(),(NamingMetaObjectParseStrategy)criteria.getNamingParseStrategy());
		        
		        if(isJoinTable){
			        query.addJoinTable(descriptor.getTable(), coreTableAliasName, descriptor.getPrimitiveProperty().getColumn(), 
			            	relation.getOppositeTable().getTable(), relation.getOppositeTable().getPrimitiveProperty().getColumn(), 
			            	relation.getJoinTableName(), relation.getShelfColumn(), relation.getOppositeColumn(), 
			            	related.getRelationType(),columnList,			            	
			            	false,tableName);
			        addTableConditions(criteria, query, relatedMetaClass, tableName, relatedDes.getTable(), orderByAscs, orderByDescs, true);
			        addComponentJoinRelation(criteria, criteria.getMetaClass(), query, relatedDes, dbNameMap, tableName, orderByAscs, orderByDescs);
			        addMetaClassRelationConditions(criteria, query, relatedDes, dbNameMap,tableName, orderByAscs, orderByDescs);
		        }
		      }
		    }
		 */

		// addMetaClassRelationConditions(queryCriteria, query,
		// descriptor,dbNameMap, coreTableAliasName, orderByAscs, orderByDescs);
		Object[] params = query.toQueryParams();
		String[] orders = assembleOrderColumn(orderByAscs, orderByDescs);
		String sql = query.toQueryStatementString(orders);
		SqlQueryResult result;
		List<Map<String, Object>> list = executor.query(sql, params);
		result = new SqlQueryResult(list.size(), list);
		assembleMetaObjectBatch(queryCriteria, metaClass, result, related, prevMetaClass, prevMetaObjectList);
	}

	private void initRelationObject(QueryCriteria queryCriteria, MetaClass metaClass, List<MetaObject> metaObjectList)
			throws MetaPlatformPersisterException, SqlExecutorException {
		QueryAssemble queryAssemble = queryCriteria.getQueryAssemble();
		Set<String> rels = new HashSet<String>();

		if (queryAssemble != null) {
			List<Object> list = new ArrayList<Object>();
			String primitiveKey = metaClass.getPrimitiveAttribute().getAttributeName();
			for (MetaObject metaObject : metaObjectList) {
				list.add(metaObject.getValue(primitiveKey));
			}

			// 只是下面一层
			for (QueryAssemble subQueryAssemble : queryAssemble.getChilds()) {
				// 拼装编码中N:1的只查询一次，1：N还需再查询一次
				String relationCode = subQueryAssemble.getCurrentRelationCode();
				
				rels.add(relationCode);
				for (MetaObject metaObject : metaObjectList) {
					metaObject.addFech(relationCode);
				}
				MetaClassRelation related = metaClass.findMetaClassRelation(relationCode);
				MetaClass relatedClass = related.getRelationMetaClass();
				QueryCriteria relatedCriteria = new QueryCriteria(relatedClass);
				relatedCriteria.setQueryAssemble(subQueryAssemble);
				// Next Relation from relatedClass
				MetaClassRelation relation = null;
				relation = findRelationByFetch(relationCode, relatedClass);
				if (relation == null) {
					throw new RuntimeException("Can Not Find MetaClassRelation For Code : " + relationCode);
				}
				// TODO: 一般情况下 list不会超过1000。 如果真碰到这种情况，可以分多次查询， 然后组合结果
				// relatedCriteria.addRelationQueryCriteria(relation).addExpression(QueryExpressionUtil.in(primitiveKey,
				// list));

				queryAndfill(relatedCriteria, metaObjectList, related, metaClass);

				/**
				 * ClassTableDescriptor mainDes =
				 * metaClassManager.getClassTableDescriptor(metaClass);
				 * ClassRelationDescriptor relationDesc =
				 * this.metaClassManager.getClassRelationDescriptor(relatedClass
				 * .findMetaClassRelation(related.getAlias())); if
				 * (queryCriteria.isSimpleLoadRelation() &&
				 * related.getRelationType() ==
				 * MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_N2ONE &&
				 * relationDesc.getJoinTableName().equals(mainDes.getTable())) { //
				 * 支持多层嵌套 initSimpleRelatedObject(nmps, item, metaObject,
				 * queryAssemble, relationCode, metaClass); } else { String
				 * primitiveKey =
				 * metaClassManager.getClassRelationDescriptor(relation).getOppositeTable()
				 * .getPrimitiveProperty().getMetaAttribute().getAttributeName();
				 * relatedCriteria.addRelationQueryCriteria(relation).addExpression(
				 * QueryExpressionUtil.equals(primitiveKey,
				 * metaObject.getValue(primitiveKey))); MetaObjectQueryResult
				 * relatedResult = query(relatedCriteria);
				 * metaObject.setRelateObject(relatedResult.getList(), related); }
				 */
			}
		}
		if (queryCriteria.getChildrenFetchs() != null && queryCriteria.getChildrenFetchs().size() > 0) {
			// TODO 临时修改
			// 拼装编码中N:1的只查询一次，1：N还需再查询一次
			for (String relationCode : queryCriteria.getChildrenFetchs()) {
				if (rels.contains(relationCode)) {
					continue;
				}
				for (MetaObject metaObject : metaObjectList) {
					metaObject.addFech(relationCode);
				}
				MetaClassRelation related = metaClass.findMetaClassRelation(relationCode);
				MetaClass relatedClass = related.getRelationMetaClass();
				QueryCriteria relatedCriteria = new QueryCriteria(relatedClass);
				// Next Relation from relatedClass
				MetaClassRelation relation = null;
				relation = findRelationByFetch(relationCode, relatedClass);
				if (relation == null) {
					throw new RuntimeException("Can Not Find MetaClassRelation For Code : " + relationCode);
				}
				queryAndfill(relatedCriteria, metaObjectList, related, metaClass);
			}
		}
	}

	private void initSimpleRelatedObject(NamingMetaObjectParseStrategy nmps, Map<String, Object> item,
			MetaObject metaObject, QueryAssemble queryAssemble, String justThisFetch, MetaClass metaClass) {
		for (QueryAssemble subQueryAssemble : queryAssemble.getChilds()) {
			if (justThisFetch == null
					|| (justThisFetch != null && justThisFetch.equals(subQueryAssemble.getCurrentRelationCode()))) {
				MetaClassRelation related = metaClass.findMetaClassRelation(subQueryAssemble.getCurrentRelationCode());
				MetaClass relatedClass = related.getRelationMetaClass();
				DefaultMetaObject simpleRelatedObject = new DefaultMetaObject(relatedClass);
				NamingMetaObjectParseStrategy current = nmps.getRelationNamingParseStrategy(related.getAlias());
				for (MetaAttribute attribute : relatedClass.getMetaAttributes()) {
					String attributeAlias = current.getTableAliasName(attribute.getTableName()) + "_"
							+ current.getColumnAliasName(attribute.getAttributeName());
					Object value = item.get(attributeAlias);
					setValue(simpleRelatedObject, attribute, value);
				}
				if (simpleRelatedObject.getValue(relatedClass.getPrimitiveAttribute().getAttributeName()) != null) {
					// 递归查询子
					if (subQueryAssemble.hasChilds()) {
						initSimpleRelatedObject(current, item, simpleRelatedObject, subQueryAssemble, null,
								relatedClass);
					}
					List<MetaObject> listRel = new ArrayList<MetaObject>();
					listRel.add(simpleRelatedObject);
					metaObject.setRelateObject(listRel, related);
				}
			}
		}
	}

	private void initSimpleRelatedObject(Map<String, Object> item, MetaObject metaObject, QueryCriteria queryCriteria,
			String justThisFetch) {
		MetaClass metaClass = null;
		if (queryCriteria.getMetaClass() != null) {
			metaClass = queryCriteria.getMetaClass();
		} else {
			metaClass = queryCriteria.getMetaClassRelation().getRelationMetaClass();
		}
		Vector<String> fetches = queryCriteria.getChildrenFetchs();
		for (String fetch : fetches) {
			if (justThisFetch == null || (justThisFetch != null && justThisFetch.equals(fetch))) {
				MetaClassRelation related = metaClass.findMetaClassRelation(fetch);
				QueryCriteria subQueryCriteria = null;
				for (QueryCriteria criteria : queryCriteria.getRelationQueryCriterias()) {
					if (criteria.getMetaClassRelation() == related) {
						subQueryCriteria = criteria;
						break;
					}
				}

				MetaClass relatedClass = related.getRelationMetaClass();
				DefaultMetaObject simpleRelatedObject = new DefaultMetaObject(relatedClass);

				NamingMetaObjectParseStrategy nmps = (NamingMetaObjectParseStrategy) subQueryCriteria
						.getNamingParseStrategy();
				for (MetaAttribute attribute : relatedClass.getMetaAttributes()) {
					String attributeAlias = nmps.getTableAliasName(attribute.getTableName()) + "_"
							+ nmps.getColumnAliasName(attribute.getAttributeName());
					Object value = item.get(attributeAlias);
					setValue(simpleRelatedObject, attribute, value);
				}

				if (simpleRelatedObject.getValue(relatedClass.getPrimitiveAttribute().getAttributeName()) != null) {
					// 递归查询子
					if (subQueryCriteria != null) {
						initSimpleRelatedObject(item, simpleRelatedObject, subQueryCriteria, null);
					}
					List<MetaObject> listRel = new ArrayList<MetaObject>();
					listRel.add(simpleRelatedObject);
					metaObject.setRelateObject(listRel, related);
				}
			}
		}
	}

	private MetaClassRelation findRelationByFetch(String fetch, MetaClass relatedClass) {
		MetaClassRelation relation;
		if (relatedClass.findMetaClassRelation(fetch + "_Z") != null) {
			relation = relatedClass.findMetaClassRelation(fetch + "_Z");
		} else {
			relation = ((DefaultMetaClass) relatedClass).findHiddenMetaClassRelation(fetch);

		}
		if (relation == null) {
			if (relatedClass.findMetaClassRelation(fetch) != null) {
				relation = relatedClass.findMetaClassRelation(fetch);
			} else {
				relation = ((DefaultMetaClass) relatedClass).findHiddenMetaClassRelation(fetch);

			}
		}
		return relation;
	}

	// TODO bug fix 不支持关联对象的卫星表属性查询
	private void addMetaClassRelationConditions(QueryCriteria queryCriteria, Query query,
			ClassTableDescriptor descriptor, Map<MetaAttribute, String> dbNameMap, String tableAlias,
			Vector<String> orderByAscs, Vector<String> orderByDescs) throws MetaPlatformPersisterException {
		/**
		 * if(queryCriteria.isSimpleLoadRelation()){
		 * if(queryCriteria.getQueryAssemble()!=null){ for(QueryAssemble
		 * subQueryAssemble : queryCriteria.getQueryAssemble().getChilds()){
		 * MetaClassRelation related = null;
		 * if(queryCriteria.getMetaClass()!=null){ related =
		 * queryCriteria.getMetaClass().findMetaClassRelation(subQueryAssemble.getCurrentRelationCode());
		 * }else{ //受制于QueryCriteria 对于子查询 没有设置metaClass related =
		 * queryCriteria.getMetaClassRelation().getRelationMetaClass().findMetaClassRelation(subQueryAssemble.getCurrentRelationCode()); }
		 * if(related!=null && related.getRelationType() ==
		 * MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_N2ONE){
		 * queryCriteria.addRelationQueryCriteria(related).setSimpleLoadRelation(true);
		 * queryCriteria.addRelationQueryCriteria(related).setQueryAssemble(subQueryAssemble); } }
		 * }else{ // 支持 fetch 查询 Vector<String> fetches =
		 * queryCriteria.getChildrenFetchs(); for(String fetch : fetches) {
		 * MetaClassRelation related = null;
		 * if(queryCriteria.getMetaClass()!=null){ related =
		 * queryCriteria.getMetaClass().findMetaClassRelation(fetch); }else{
		 * //受制于QueryCriteria 对于子查询 没有设置metaClass related =
		 * queryCriteria.getMetaClassRelation().getRelationMetaClass().findMetaClassRelation(fetch); }
		 * if(related!=null && related.getRelationType() ==
		 * MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_N2ONE){
		 * queryCriteria.addRelationQueryCriteria(related).setSimpleLoadRelation(true); } } } }
		 */

		Vector<QueryCriteria> relateds = queryCriteria.getRelationQueryCriterias();
		if (relateds != null) {
			for (QueryCriteria criteria : relateds) {
				// 遍历每一个子查询
				MetaClassRelation related = criteria.getMetaClassRelation();
				if (related == null)
					throw new RuntimeException();
				MetaClass relatedMetaClass = related.getRelationMetaClass();
				ClassTableDescriptor relatedDes = metaClassManager.getClassTableDescriptor(relatedMetaClass);
				ClassRelationDescriptor relation = metaClassManager.getClassRelationDescriptor(related);
				String tableName = ((NamingMetaObjectParseStrategy) criteria.getNamingParseStrategy())
						.getTableAliasName(relatedDes.getTable());

				boolean isJoinTable = true;

				// 查看 是否有查询条件
				isJoinTable = isJoinTable(criteria, relatedDes);

				if (isJoinTable) {

					String baseTableName = descriptor.getTable();
					String baseTableNameAlias = tableAlias;
					String baseTableKeyColumn = descriptor.getPrimitiveProperty().getColumn();
					String oppositeTableName = relation.getOppositeTable().getTable();
					String oppositeKeyColumn = relation.getOppositeTable().getPrimitiveProperty().getColumn();
					String joinTableName = relation.getJoinTableName();
					String coreKey = relation.getShelfColumn();
					String oppositeKey = relation.getOppositeColumn();
					int multiple = related.getRelationType();
					List<String> columnNames = new ArrayList<String>();
					boolean needFecth = false;
					String tableNameAlias = tableName;
					
					
					
					
					
					
					if(multiple == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_N2ONE){
						//N:1 属性记在本端上的
						baseTableName = getTableNameByColumnName(descriptor,relation.getOppositeColumn());
						baseTableNameAlias = ((NamingMetaObjectParseStrategy) queryCriteria.getNamingParseStrategy())
							.getTableAliasName(baseTableName);
						joinTableName = baseTableName;
						
					}else if(multiple == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_ONE2N){
						//属性记在对端
						oppositeTableName = getTableNameByColumnName(relation.getOppositeTable(),relation.getShelfColumn());
						tableNameAlias = ((NamingMetaObjectParseStrategy) criteria.getNamingParseStrategy())
							.getTableAliasName(oppositeTableName);
						joinTableName = oppositeTableName;
					}
					
					query.addJoinTable(baseTableName, baseTableNameAlias, baseTableKeyColumn, oppositeTableName, oppositeKeyColumn,
							joinTableName, coreKey, oppositeKey, multiple, columnNames, needFecth, tableNameAlias);
					addPlainTableJoinRelation(criteria, relatedMetaClass, query, relatedDes, dbNameMap, tableName,
							orderByAscs, orderByDescs);
//					query.addJoinTable(descriptor.getTable(), tableAlias,
//							descriptor.getPrimitiveProperty().getColumn(), relation.getOppositeTable().getTable(),
//							relation.getOppositeTable().getPrimitiveProperty().getColumn(),
//							relation.getJoinTableName(), relation.getShelfColumn(), relation.getOppositeColumn(),
//							related.getRelationType(), new ArrayList<String>(), false, tableName);
					addTableConditions(criteria, query, relatedMetaClass, tableName, relatedDes.getTable(),
							orderByAscs, orderByDescs, true);
					addComponentJoinRelation(criteria, criteria.getMetaClass(), query, relatedDes, dbNameMap,
							tableName, orderByAscs, orderByDescs);
					addMetaClassRelationConditions(criteria, query, relatedDes, dbNameMap, tableName, orderByAscs,
							orderByDescs);
				}
			}
		}
	}
	
	/**
	 * 查询属性在那个表上
	 * @param descriptor
	 * @param columnName
	 * @return
	 */
	private String getTableNameByColumnName(TableDescriptor descriptor,String columnName){
		List<PropertyDescriptor> propertyList = descriptor.getMetaProerties();
		for (PropertyDescriptor propertyDescriptor : propertyList) {
			if(propertyDescriptor.getColumn().equals(columnName)){
				return descriptor.getTable();
			}
		}
		
		List<RelationDescriptor> rdList = descriptor.getPlainTableRelations();
		for (RelationDescriptor relationDescriptor : rdList) {
			propertyList = relationDescriptor.getOppositeTable().getMetaProerties();
			for (PropertyDescriptor propertyDescriptor : propertyList) {
				if(propertyDescriptor.getColumn().equals(columnName)){
					return relationDescriptor.getOppositeTable().getTable();
				}
			}
		}
		
		return null;
	}

	private boolean isJoinTable(QueryCriteria queryCriteria, ClassTableDescriptor descriptor) {
		boolean isJoinTable = false; // true 添加关联
		// 查看组件是否有查询条件
		for (ComponentRelationDescriptor componentRelationDes : descriptor.getRelationComponentTables()) {
			List<QueryCriteria> componentsCriteria = queryCriteria.getComponentQueryCriterias();
			boolean isBreak = false;
			for (QueryCriteria itemTemp : componentsCriteria) {
				if (itemTemp.getMetaComponentRelation() == componentRelationDes.getMetaComponentRelation()) {
					if (itemTemp.getExpressions() != null && itemTemp.getExpressions().size() > 0) {
						// 组件没有查询条件的话 就
						isBreak = true;
					}
					break;
				}
			}
			if (isBreak) {
				isJoinTable = true;
				break;
			}
		}

		if (!isJoinTable) {
			if (queryCriteria.getExpressions() != null && queryCriteria.getExpressions().size() > 0) {
				isJoinTable = true;
			}
		}
		// 关联对象有查询条件 也要拼装本对象
		if (!isJoinTable) {
			Vector<QueryCriteria> relateds = queryCriteria.getRelationQueryCriterias();
			boolean isSubJoin = false;
			for (QueryCriteria subCriteria : relateds) {
				MetaClassRelation related = subCriteria.getMetaClassRelation();
				MetaClass relatedMetaClass = related.getRelationMetaClass();
				ClassTableDescriptor relatedDes = metaClassManager.getClassTableDescriptor(relatedMetaClass);
				if (isJoinTable(subCriteria, relatedDes)) {
					isSubJoin = true;
				}
			}
			if (isSubJoin) {
				isJoinTable = true;
			}
		}
		return isJoinTable;
	}

	private String[] assembleOrderAsc(Vector<String> orderByAscs, Vector<String> orderByDescs) {
		String[] isAsc = new String[orderByAscs.size() + orderByDescs.size()];
		for (int i = 0; i < orderByAscs.size(); ++i) {
			isAsc[i] = "ASC";
		}
		for (int i = orderByAscs.size(); i < isAsc.length; ++i) {
			isAsc[i] = "DESC";
		}
		return isAsc;
	}

	private String[] assembleOrderColumn(Vector<String> orderByAscs, Vector<String> orderByDescs) {
		List<String> orderColumns = new ArrayList();
		orderColumns.addAll(orderByAscs);
		orderColumns.addAll(orderByDescs);
		String[] orders = orderColumns.toArray(new String[orderColumns.size()]);
		return orders;
	}

	private void resolveFeches(QueryCriteria queryCriteria, MetaClass metaClass) {
		Vector<QueryCriteria> relateds = queryCriteria.getRelationQueryCriterias();
		Vector<String> newFeches = new Vector<String>();
		Vector<String> feches = queryCriteria.getChildrenFetchs();
		for (QueryCriteria criteria : relateds) {
			MetaClassRelation related = criteria.getMetaClassRelation();
			if (CollectionUtil.notContainInList(feches, related.getAlias())
					&& CollectionUtil.notContainInList(newFeches, related.getAlias())) {
				newFeches.add(related.getAlias());
			}
		}

		for (String fech : feches) {
			queryCriteria.addRelationQueryCriteria(metaClass.findMetaClassRelation(fech));
		}

		feches.addAll(newFeches);
	}

	private void addPlainTableJoinRelation(QueryCriteria queryCriteria, MetaClass metaClass, Query query,
			ClassTableDescriptor descriptor, Map<MetaAttribute, String> dbNameMap, String tableAlias,
			Vector<String> orderByAscs, Vector<String> orderByDescs) throws MetaPlatformPersisterException {
		for (RelationDescriptor relationDes : descriptor.getPlainTableRelations()) {
			String plainTableKey = null;
			if (relationDes.getOppositeTable().getPrimitiveProperty() == null) {
				plainTableKey = "ENTITYID";
			} else {
				plainTableKey = relationDes.getOppositeTable().getPrimitiveProperty().getColumn();
			}
			String plainTableName = ((NamingMetaObjectParseStrategy) queryCriteria.getNamingParseStrategy())
					.getTableAliasName(relationDes.getOppositeTable().getTable());

			// clob 不支持group by
			boolean isFilterClob = false;
			if (queryCriteria.getRelationQueryCriterias() != null
					&& queryCriteria.getRelationQueryCriterias().size() > 0) {
				isFilterClob = true;
			}
			query.addJoinTable(descriptor.getTable(), tableAlias, descriptor.getPrimitiveProperty().getColumn(),
					relationDes.getOppositeTable().getTable(), plainTableKey, relationDes.getJoinTableName(),
					relationDes.getShelfColumn(), relationDes.getOppositeColumn(),
					MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_ONE2ONE, filterColumnNames(relationDes.getOppositeTable()
							.getMetaProerties(),
							(NamingMetaObjectParseStrategy) queryCriteria.getNamingParseStrategy(), isFilterClob),
					false, plainTableName);
			loadDbNameMap(relationDes.getOppositeTable().getMetaProerties(), dbNameMap, plainTableName);
			addTableConditions(queryCriteria, query, metaClass, plainTableName, relationDes.getOppositeTable()
					.getTable(), orderByAscs, orderByDescs, true);

		}
	}

	private void addComponentJoinRelation(QueryCriteria queryCriteria, MetaClass metaClass, Query query,
			ClassTableDescriptor descriptor, Map<MetaAttribute, String> dbNameMap, String tableAlias,
			Vector<String> orderByAscs, Vector<String> orderByDescs) {
		for (ComponentRelationDescriptor componentRelationDes : descriptor.getRelationComponentTables()) {
			List<QueryCriteria> componentsCriteria = queryCriteria.getComponentQueryCriterias();
			if (componentsCriteria == null || componentsCriteria.size() == 0)
				continue;
			QueryCriteria item = null;
			boolean isBreak = false;
			for (QueryCriteria itemTemp : componentsCriteria) {
				if (itemTemp.getMetaComponentRelation() == componentRelationDes.getMetaComponentRelation()) {
					item = itemTemp;
					if (itemTemp.getExpressions() == null || itemTemp.getExpressions().size() == 0) {
						// 组件没有查询条件的话 就
						isBreak = true;
					}
					break;
				}
			}
			if(item==null) isBreak=true;
			
			if (isBreak)
				continue;

			NamingMetaObjectParseStrategy nmps = ((NamingMetaObjectParseStrategy) queryCriteria
					.getNamingParseStrategy()).getRelationNamingParseStrategy(componentRelationDes
					.getMetaComponentRelation().getAlias());
			String componentTableName = nmps.getTableAliasName(componentRelationDes.getOppositeTable().getTable());
			query.addJoinTable(descriptor.getTable(), tableAlias, descriptor.getPrimitiveProperty().getColumn(),
					componentRelationDes.getOppositeTable().getTable(), componentRelationDes.getOppositeTable()
							.getPrimitiveProperty().getColumn(), componentRelationDes.getJoinTableName(),
					componentRelationDes.getShelfColumn(), componentRelationDes.getOppositeColumn(),
					MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_ONE2ONE, filterColumnNames(componentRelationDes
							.getOppositeTable().getMetaProerties(), nmps), false, componentTableName);
			loadDbNameMap(componentRelationDes.getOppositeTable().getMetaProerties(), dbNameMap, componentTableName);

			// for (QueryCriteria item : componentsCriteria) {
			// if (item.getMetaComponentRelation() ==
			// componentRelationDes.getMetaComponentRelation()) {
			addComponentTableConditions(item, query, componentRelationDes.getMetaComponentRelation()
					.getRelationMetaComponent(), componentTableName,
					componentRelationDes.getOppositeTable().getTable(), orderByAscs, orderByDescs);
			// }
			// }
		}
	}

	private void setValue(MetaData metaData, MetaAttribute attribute, Object value) {
		if (value == null) {
			metaData.setValue(attribute.getAttributeName(), value);
		} else {
		  try {
  			if (attribute.getDataType() == MetaConsts.VALUE_METADATA_DATATYPE_BYTE)
  				metaData.setValue(attribute.getAttributeName(), ((BigDecimal) value).byteValue());
  			// TODO long type handler
  			if (attribute.getDataType() == MetaConsts.VALUE_METADATA_DATATYPE_LONG)
  				metaData.setValue(attribute.getAttributeName(), ((BigDecimal) value).longValue());
  			if (attribute.getDataType() == MetaConsts.VALUE_METADATA_DATATYPE_FLOAT)
  				metaData.setValue(attribute.getAttributeName(), ((BigDecimal) value).floatValue());
  			if (attribute.getDataType() == MetaConsts.VALUE_METADATA_DATATYPE_STRING)
  				metaData.setValue(attribute.getAttributeName(), (String) value);
  			if (attribute.getDataType() == MetaConsts.VALUE_METADATA_DATATYPE_DATE)
  				metaData.setValue(attribute.getAttributeName(), (Date) value);
  			if (attribute.getDataType() == MetaConsts.VALUE_METADATA_DATATYPE_INT)
  				metaData.setValue(attribute.getAttributeName(), ((BigDecimal) value).intValue());
  			if (attribute.getDataType() == MetaConsts.VALUE_METADATA_DATATYPE_DICTIONARY)
  				metaData.setValue(attribute.getAttributeName(), ((BigDecimal) value).intValue());
  			if (attribute.getDataType() == MetaConsts.VALUE_METADATA_DATATYPE_MULTIDICTIONARY)
  				metaData.setValue(attribute.getAttributeName(), (String) value);
  			if (attribute.getDataType() == MetaConsts.VALUE_METADATA_DATATYPE_CLOB) {
  				OracleSerialClob clob = (OracleSerialClob) value;
  				BufferedReader a;
  				try {
  					a = new BufferedReader(clob.getCharacterStream());
  
  					String content = "";
  					String str = "";
  					while ((str = a.readLine()) != null) {
  						content = content.concat(str); // 最后以String的形式得到
  					}
  
  					metaData.setValue(attribute.getAttributeName(), content);
  				} catch (Exception e) {
  					logger.error("error read clob data ");
  				}
  			}
			}
		  catch (ClassCastException error) {
			  logger.error("error set value for attribute:" + attribute.getTableColumnName() + " in table:" + attribute.getTableName());
		    throw new RuntimeException("error set value for attribute:" + attribute.getTableColumnName() + " in table:" + attribute.getTableName());
		  }
		}
	}

	private List<String> filterColumnNames(List<PropertyDescriptor> metaProerties, NamingMetaObjectParseStrategy nmps) {
		return filterColumnNames(metaProerties, nmps, false);
	}

	private List<String> filterColumnNames(List<PropertyDescriptor> metaProerties, NamingMetaObjectParseStrategy nmps,
			boolean filterClob) {
		List<String> names = new ArrayList<String>();
		for (PropertyDescriptor property : metaProerties) {
			if (filterClob && property.getMetaAttribute().getDataType() == MetaConsts.VALUE_METADATA_DATATYPE_CLOB) {
				continue;
			}
			names.add(nmps.getColumnAliasName(property.getMetaAttribute().getAttributeName()));
		}
		return names;
	}

	private String filterColumnName(PropertyDescriptor property) {

		return property.getMetaAttribute().getTableColumnName();
	}

	private void loadDbNameMap(List<PropertyDescriptor> metaProerties, Map<MetaAttribute, String> dbNameMap,
			String dbTableName) {
		for (PropertyDescriptor property : metaProerties) {
			loadDbNameMap(property, dbNameMap, dbTableName);
		}
	}

	private void loadDbNameMap(PropertyDescriptor property, Map<MetaAttribute, String> dbNameMap, String dbTableName) {
		dbNameMap
				.put(property.getMetaAttribute(), dbTableName + "_" + property.getMetaAttribute().getTableColumnName());
	}

	private String reAssembleProperty(Select select, MetaAttribute attribute, String key) {
		String tableName = attribute.getTableName();
		String columnName = attribute.getTableColumnName();
		String replace = makeUpReplace(tableName, columnName);
		return replace;
	}

	private String makeUpReplace(String tableName, String columnName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(tableName).append(".").append(columnName);
		String replace = buffer.toString();
		return replace;
	}

	public void delete(MetaObject metaObject) throws MetaPlatformPersisterException, SqlExecutorException {
		deleteMetaObject(metaObject, null, true);
	}

	public void deleteWithoutCheckRelation(MetaObject metaObject) throws MetaPlatformPersisterException,
			SqlExecutorException {
		deleteMetaObject(metaObject, null, false);
	}

	private void deleteMetaObject(MetaObject metaObject, MetaClassRelation mainClassRelation, boolean updateRelation)
			throws SqlExecutorException {
		if (metaObject.getValue("METACATEGORY") != null && !metaObject.getValue("METACATEGORY").equals("")) {
			MetaClass subMetaClass = metaClassManager.getMetaClass(metaObject.getString("METACATEGORY"));
			((DefaultMetaObject) metaObject).setMetaClass(subMetaClass);
		}
		MetaClass metaClass = metaObject.getMetaClass();
		ClassTableDescriptor tableDesc = metaClassManager.getClassTableDescriptor(metaClass);
		List<MetaComponentRelation> componentRelations = metaClass.getMetaComponentRelations();
		for (MetaComponentRelation relation : componentRelations) {
			MetaComponentObject cObject = metaObject.getRelateComponentObject(relation);
			if (cObject != null) {
				ComponentTableDescriptor componentDesc = metaClassManager.getComponentTableDescriptor(cObject
						.getMetaComponent());
				deleteComponent(cObject, componentDesc);
			}
		}

		// 删除 卫星表 关联关系
		List<RelationDescriptor> plainList = tableDesc.getPlainTableRelations();
		for (RelationDescriptor descriptor : plainList) {
			Delete del = new Delete();
			TableDescriptor plainDesc = descriptor.getOppositeTable();
			del.setTableName(descriptor.getJoinTableName());
			String priCol = getPlainTableIDColumnName(plainDesc);
			del.setWhere(priCol + " =? ");
			String delSql = del.toStatementString();
			Object[] params = new Object[1];
			String attributeName = tableDesc.getPrimitiveProperty().getMetaAttribute().getAttributeName();
			params[0] = metaObject.getValue(attributeName);
			executor.update(delSql, params);
		}

		if (updateRelation) {
			List<MetaClassRelation> objectRelations = metaClass.getMetaClassRelations();
			for (MetaClassRelation relation : objectRelations) {
				// 避免关系重复和自包含的关系
				// if(!relation.isSameRelationSpec(mainClassRelation) &&
				// relation.getRelationMetaClass() != metaClass) {
				if (!relation.isSameRelationSpec(mainClassRelation)) {
					deleteAllRelationOnlyCallByDeleteMetaObject(metaObject, relation);
					List<MetaObject> metaObjects = metaObject.getRelateObject(relation);
					if (metaObjects != null && metaObjects.size() > 0) {
						if (isCascade(relation)) {
							for (MetaObject object : metaObjects) {
								deleteMetaObject(object, relation, true);
							}
						}
					}
				}
			}
		}

		Delete del = new Delete();
		del.setTableName(tableDesc.getTable());
		String priCol = tableDesc.getPrimitiveProperty().getColumn();
		del.setWhere(priCol + " =? ");
		String delSql = del.toStatementString();
		Object[] params = new Object[1];
		String attributeName = tableDesc.getPrimitiveProperty().getMetaAttribute().getAttributeName();
		params[0] = metaObject.getValue(attributeName);
		executor.update(delSql, params);
	}

	private boolean isCascade(MetaClassRelation relation) {

		return relation.getCascade() == MetaConsts.VALUE_METADATA_STATUS_AVAILABLE;
	}

	/**
	 * 供update调用
	 * 
	 * @param metaObject
	 * @param relation
	 * @throws SqlExecutorException
	 */
	private void deleteEntityRelation(MetaObject metaObject, MetaClassRelation relation) throws SqlExecutorException {
		ClassTableDescriptor relationEntityDesc = metaClassManager.getClassTableDescriptor(relation
				.getRelationMetaClass());
		ClassTableDescriptor mainEntityDesc = metaClassManager.getClassTableDescriptor(metaObject.getMetaClass());
		ClassRelationDescriptor relationDesc = metaClassManager.getClassRelationDescriptor(relation);

		int multiple = relation.getRelationType();
		if (!relationDesc.isIndependentTable()) {
			// 不是中间表
			String joinTableName = relationDesc.getJoinTableName();
			Update update = new Update(oracle9dialect).setTableName(joinTableName);
			if (isRelationTableRely(joinTableName, mainEntityDesc)
					&& isRelationTableRely(joinTableName, relationEntityDesc)) {
				if (isRelationTableRely(joinTableName, mainEntityDesc)
						&& multiple == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_N2ONE) {
					// 关系在本表
					update.addColumn(relationDesc.getOppositeColumn());
					if (isCoretableRely(joinTableName, mainEntityDesc)) {
						update.addWhereColumn(mainEntityDesc.getPrimitiveProperty().getColumn());
					} else if (isExtendTableRelay(joinTableName, mainEntityDesc)) {
						update.addWhereColumn(getPlainTableIDColumnName(mainEntityDesc, joinTableName));
					}
					String updateSql = update.toStatementString();
					Object[] ids = new Object[2];
					List<MetaObject> metaObjects = metaObject.getRelateObject(relation);
					if (metaObjects != null && metaObjects.size() > 0) {
						Object shelfID = metaObject.getValue(mainEntityDesc.getPrimitiveProperty().getMetaAttribute()
								.getAttributeName());
						ids[0] = null;
						ids[1] = shelfID;
						executor.update(updateSql, ids);
					}
				} else if (isRelationTableRely(joinTableName, relationEntityDesc)
						&& multiple == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_ONE2N) {
					// 关系在对端表
					update.addColumn(relationDesc.getShelfColumn());
					if (isCoretableRely(joinTableName, relationEntityDesc)) {
						update.addWhereColumn(relationEntityDesc.getPrimitiveProperty().getColumn());
					} else if (isExtendTableRelay(joinTableName, relationEntityDesc)) {
						update.addWhereColumn(getPlainTableIDColumnName(relationEntityDesc, joinTableName));
					}
					String updateSql = update.toStatementString();
					Object[] ids = new Object[2];
					List<MetaObject> metaObjects = metaObject.getRelateObject(relation);
					if (metaObjects != null && metaObjects.size() > 0) {
						for (MetaObject object : metaObjects) {
							String componentAttrName = relationEntityDesc.getPrimitiveProperty().getMetaAttribute()
									.getAttributeName();
							Object oppositeId = object.getValue(componentAttrName);
							ids[0] = null;
							ids[1] = oppositeId;
							executor.update(updateSql, ids);
						}
					}
				}
			} else if (isRelationTableRely(joinTableName, mainEntityDesc)) {
				update.addColumn(relationDesc.getOppositeColumn());
				if (isCoretableRely(joinTableName, mainEntityDesc)) {
					update.addWhereColumn(mainEntityDesc.getPrimitiveProperty().getColumn());
				} else if (isExtendTableRelay(joinTableName, mainEntityDesc)) {
					update.addWhereColumn(getPlainTableIDColumnName(mainEntityDesc, joinTableName));
				}
				String updateSql = update.toStatementString();
				Object[] ids = new Object[2];
				List<MetaObject> metaObjects = metaObject.getRelateObject(relation);
				if (metaObjects != null && metaObjects.size() > 0) {
					Object shelfID = metaObject.getValue(mainEntityDesc.getPrimitiveProperty().getMetaAttribute()
							.getAttributeName());
					ids[0] = null;
					ids[1] = shelfID;
					executor.update(updateSql, ids);
				}
			} else {
				// update.addColumn(relationDesc.getOppositeColumn());
				update.addColumn(relationDesc.getShelfColumn());

				if (isCoretableRely(joinTableName, relationEntityDesc)) {
					update.addWhereColumn(mainEntityDesc.getPrimitiveProperty().getColumn());
				} else if (isExtendTableRelay(joinTableName, relationEntityDesc)) {
					update.addWhereColumn(getPlainTableIDColumnName(relationEntityDesc, joinTableName));
				}
				String updateSql = update.toStatementString();
				Object[] ids = new Object[2];
				List<MetaObject> metaObjects = metaObject.getRelateObject(relation);
				if (metaObjects != null && metaObjects.size() > 0) {
					for (MetaObject object : metaObjects) {
						String componentAttrName = relationEntityDesc.getPrimitiveProperty().getMetaAttribute()
								.getAttributeName();
						Object oppositeId = object.getValue(componentAttrName);
						ids[0] = null;
						ids[1] = oppositeId;
						executor.update(updateSql, ids);
					}
				}
			}
		} else {
			String joinTableName = relationDesc.getJoinTableName();
			Delete del = new Delete();
			del.setTableName(joinTableName);
			// TODO versioninstance
			del.setWhere("" + relationDesc.getOppositeColumn() + " =?  and " + relationDesc.getShelfColumn() + " =?");
			String delSql = del.toStatementString();
			Object[] params = new Object[2];
			List<MetaObject> metaObjects = metaObject.getRelateObject(relation);
			if (metaObjects != null && metaObjects.size() > 0) {
				for (MetaObject object : metaObjects) {
					String componentAttrName = relationEntityDesc.getPrimitiveProperty().getMetaAttribute()
							.getAttributeName();
					Object oppositeId = object.getValue(componentAttrName);
					Object shelfID = metaObject.getValue(mainEntityDesc.getPrimitiveProperty().getMetaAttribute()
							.getAttributeName());
					// params[2] = relation.getId();
					params[1] = shelfID;
					params[0] = oppositeId;
					executor.update(delSql, params);
				}
			}
		}
	}

	/**
	 * delete调用 只有2中情况更新 1：中间表 删除 2：关系建在对端上， 更新对端
	 * 
	 * @param metaObject
	 * @param relation
	 * @throws SqlExecutorException
	 */
	private void deleteAllRelationOnlyCallByDeleteMetaObject(MetaObject metaObject, MetaClassRelation relation)
			throws SqlExecutorException {
		ClassTableDescriptor relationEntityDesc = metaClassManager.getClassTableDescriptor(relation
				.getRelationMetaClass());
		ClassTableDescriptor mainEntityDesc = metaClassManager.getClassTableDescriptor(metaObject.getMetaClass());
		ClassRelationDescriptor relationDesc = metaClassManager.getClassRelationDescriptor(relation);
		if (!relationDesc.isIndependentTable()) {
			// 不是中间表,关系建在自己身上的时候就不用删了
			String joinTableName = relationDesc.getJoinTableName();
			Update update = new Update(oracle9dialect).setTableName(joinTableName);
			if (isRelationTableRely(joinTableName, relationEntityDesc)) {
				// 类似区域的自关联的情况
				if (!mainEntityDesc.getPrimitiveProperty().getMetaAttribute().getAttributeName().equals(
						relationDesc.getShelfColumn())) {
					Object shelfID = metaObject.getValue(mainEntityDesc.getPrimitiveProperty().getMetaAttribute()
							.getAttributeName());
					StringBuffer buffer = new StringBuffer();
					buffer.append("SELECT COUNT(*) FROM " + joinTableName + " WHERE ");
					buffer.append(relationDesc.getShelfColumn() + " = ? ");
					Object[] params = new Object[1];
					params[0] = shelfID;
		       int count;
		       //印尼IMS中删除Region时查询SELECT COUNT(*) FROM XB_DIAGRAM WHERE MANAGEDAREAID=? 但是MANAGEDAREAID在数据库里类型是VARCHAR2，会导致类型转换错误，add by wangxianxing 2013.7.17 19:46
		       if(joinTableName.equals("XB_DIAGRAM")&&relationDesc.getShelfColumn().equals("MANAGEDAREAID")){
		         params[0] = shelfID.toString();
		         count = executor.queryForInt(buffer.toString(),params );
		       }else{
		         count = executor.queryForInt(buffer.toString(), params);
		       }
					if (count > 0) {
						update.addColumn(relationDesc.getShelfColumn());
						update.addWhereColumn(relationDesc.getShelfColumn());
						String updateSql = update.toStatementString();
						Object[] ids = new Object[2];

						ids[0] = null;
						ids[1] = shelfID;
						try{
							executor.update(updateSql, ids);
						}catch(Exception e){
							//捕获更新时的异常，不作处理
						}
					}
				}
			}
		} else {
			String joinTableName = relationDesc.getJoinTableName();
			Object shelfID = metaObject.getValue(mainEntityDesc.getPrimitiveProperty().getMetaAttribute()
					.getAttributeName());
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT COUNT(*) FROM " + joinTableName + " WHERE ");
			buffer.append(relationDesc.getShelfColumn() + " = ? ");
			Object[] params1 = new Object[1];
			params1[0] = shelfID;
			 int count;
			 //印尼IMS中删除Region时查询SELECT COUNT(*) FROM XB_DIAGRAM WHERE MANAGEDAREAID=? 但是MANAGEDAREAID在数据库里类型是VARCHAR2，会导致类型转换错误，add by wangxianxing 2013.7.17 19:46
       if(joinTableName.equals("XB_DIAGRAM")&&relationDesc.getShelfColumn().equals("MANAGEDAREAID")){
         params1[0] = shelfID.toString();
         count = executor.queryForInt(buffer.toString(),params1 );
       }else{
         count = executor.queryForInt(buffer.toString(), params1);
       }
			if (count > 0) {
				Delete del = new Delete();
				del.setTableName(joinTableName);
				Object[] params = new Object[2];
				// 删除所有的
				del.setWhere(" " + relationDesc.getShelfColumn() + " =?");
				String delSql = del.toStatementString();
				params = new Object[1];
				params[0] = shelfID;
				System.out.println(delSql);
				executor.update(delSql, params);
			}
		}
	}

	/**
	 * 根据配置取得卫星表主键列明
	 * 
	 * @param tableDescriptor
	 * @return
	 */
	private String getPlainTableIDColumnName(TableDescriptor tableDescriptor) {
		String plainTableKey = null;
		if (tableDescriptor.getPrimitiveProperty() == null) {
			plainTableKey = "ENTITYID";
		} else {
			plainTableKey = tableDescriptor.getPrimitiveProperty().getColumn();
		}
		return plainTableKey;
	}

	/**
	 * 根据配置取得卫星表主键列明
	 * 
	 * @param tableDescriptor
	 * @return
	 */
	private String getPlainTableIDColumnName(ClassTableDescriptor mainEntityDesc, String joinTableName) {
		for (RelationDescriptor plain : mainEntityDesc.getPlainTableRelations()) {
			if (plain.getOppositeTable() != null && joinTableName.equals(plain.getOppositeTable().getTable())) {
				return getPlainTableIDColumnName(plain.getOppositeTable());
			}
		}
		return "ENTITYID";
	}

	/**
	 * 根据配置取得卫星表主键
	 * 
	 * @param tableDescriptor
	 * @return
	 */
	private PropertyDescriptor getPlainTableID(TableDescriptor tableDescriptor, MetaClass metaClass) {
		PropertyDescriptor plainTableKey = null;
		if (tableDescriptor.getPrimitiveProperty() == null) {
			plainTableKey = new PropertyDescriptor();
			plainTableKey.setColumn("ENTITYID");
			plainTableKey.setMetaAttribute(metaClass.findMetaAttribute("ENTITYID"));
		} else {
			plainTableKey = tableDescriptor.getPrimitiveProperty();
		}
		return plainTableKey;
	}

	private void deleteComponent(MetaComponentObject cObject, TableDescriptor componentDesc)
			throws SqlExecutorException {
		Delete del = new Delete();
		del.setTableName(componentDesc.getTable());
		String priCol = componentDesc.getPrimitiveProperty().getColumn();
		del.setWhere(priCol + " =? ");
		String delSql = del.toStatementString();
		Object[] params = new Object[1];
		String attributeName = componentDesc.getPrimitiveProperty().getMetaAttribute().getAttributeName();
		params[0] = cObject.getValue(attributeName);
		executor.update(delSql, params);
	}

	public MetaClassManager getMetaClassManager() {
		return metaClassManager;
	}

	public void setMetaClassManager(MetaClassManager descFactory) {
		this.metaClassManager = descFactory;
	}

	public SqlExecutor getSqlExecutor() {
		return executor;
	}

	public void setSqlExecutor(SqlExecutor executor) {
		this.executor = executor;
	}
}
