package com.gxlu.meta;

import java.util.Vector;

import com.gxlu.meta.criterion.Criterion;
import com.gxlu.meta.engine.persister.entity.DefaultNamingMetaObjectParseStrategy;
import com.gxlu.meta.engine.persister.entity.NamingMetaObjectParseStrategy;
import com.gxlu.meta.help.MetaObjectHelper;

/**
 * 
 * @author K
 */
public class QueryCriteria {

	private QueryAssemble queryAssemble;

	public QueryCriteria(MetaClass metaClass) {
		namingParseStrategy = new DefaultNamingMetaObjectParseStrategy(metaClass);
		this.metaClass = metaClass;
	}

	/**
	 * 
	 * @param metaClass
	 *            like com.gxlu.ngrm.equipment.ManagedElement
	 */
	public QueryCriteria(String metaClassName) {
		MetaClass metaClass = MetaObjectHelper.getMetaClass(metaClassName);
		namingParseStrategy = new DefaultNamingMetaObjectParseStrategy(metaClass);
		this.metaClass = metaClass;
	}

	public QueryCriteria(MetaClassRelation metaClassRelation) {
		this.metaClassRelation = metaClassRelation;
		this.metaClass = metaClassRelation.getRelationMetaClass();
	}

	public QueryCriteria(MetaComponentRelation metaComponentRelation) {
		this.metaComponentRelation = metaComponentRelation;
	}

	public MetaClassRelation getMetaClassRelation() {
		return metaClassRelation;
	}

	public MetaClass getMetaClass() {
		return metaClass;
	}

	public MetaComponentRelation getMetaComponentRelation() {
		return metaComponentRelation;
	}

	public Vector<QueryCriteria> getComponentQueryCriterias() {
		return componentQueryCriterias;
	}

	public Vector<QueryCriteria> getRelationQueryCriterias() {
		return relationQueryCriterias;
	}

	public QueryCriteria addComponentQueryCriteria(MetaComponentRelation metaComponentRelation) {
		QueryCriteria criteria = null;
		boolean ishas = false;
		if(componentQueryCriterias!=null){
		  for(QueryCriteria qc : componentQueryCriterias) {
        if(qc.getMetaComponentRelation()==metaComponentRelation){
          ishas = true;
        }
      }
		}
		if (!ishas) {
			criteria = new QueryCriteria(metaComponentRelation);
			DefaultNamingMetaObjectParseStrategy relDnmp = new DefaultNamingMetaObjectParseStrategy(
					metaComponentRelation.getRelationMetaComponent(),
					(DefaultNamingMetaObjectParseStrategy) namingParseStrategy, metaComponentRelation
							.getRelationMetaComponent().getCode());
			criteria.namingParseStrategy = relDnmp;
			componentQueryCriterias.add(criteria);
		} else {
			for (QueryCriteria oldCriteria : componentQueryCriterias) {
				if (oldCriteria.getMetaComponentRelation() == metaComponentRelation) {
					criteria = oldCriteria;
					break;
				}
			}
		}

		return criteria;
	}

	public QueryCriteria addComponentQueryCriteria(String metaComponentCode) {
		QueryCriteria criteria = null;
		if (metaClass != null) {
			MetaComponentRelation relation = metaClass.findMetaComponentRelation(metaComponentCode);
			if (relation == null)
				throw new RuntimeException("Can Not Find MetaComponentRelation For Code : " + metaComponentCode);
			criteria = addComponentQueryCriteria(relation);
		} else {
			throw new RuntimeException("Empty MetaClass When Find A MetaComponentRelation In QueryCriteria");
		}

		return criteria;
	}

	public QueryCriteria addRelationQueryCriteria(MetaClassRelation metaClassRelation) {
		QueryCriteria criteria = null;
		boolean isContains = false;
		for (QueryCriteria oldCriteria : relationQueryCriterias) {
			if (oldCriteria.getMetaClassRelation() == metaClassRelation) {
				criteria = oldCriteria;
				isContains = true;
				break;
			}
		}

		if (!isContains) {
			criteria = new QueryCriteria(metaClassRelation);
			DefaultNamingMetaObjectParseStrategy relDnmp = new DefaultNamingMetaObjectParseStrategy(metaClassRelation
					.getRelationMetaClass(), (DefaultNamingMetaObjectParseStrategy) namingParseStrategy,
					metaClassRelation.getAlias());
			criteria.namingParseStrategy = relDnmp;
			relationQueryCriterias.add(criteria);
		}

		return criteria;
	}

	public QueryCriteria addRelationQueryCriteria(String relationCode) {
		QueryCriteria criteria = null;
		if (metaClass != null) {
			MetaClassRelation relation = metaClass.findMetaClassRelation(relationCode);
			if (relation == null)
				throw new RuntimeException("Can Not Find MetaClassRelation For Code : " + relationCode);
			criteria = addRelationQueryCriteria(relation);
		} else if (metaClassRelation != null) {
			MetaClassRelation relation = metaClassRelation.getRelationMetaClass().findMetaClassRelation(relationCode);
			if (relation == null)
				throw new RuntimeException("Can Not Find MetaClassRelation For Code : " + relationCode);
			criteria = addRelationQueryCriteria(relation);
		}

		return criteria;
	}

	public Vector<String> getChildrenFetchs() {
		return this.childrenFetchs;
	}

	public void setChildrenFetchs(Vector<String> _childrenFetchs) {
		this.childrenFetchs = _childrenFetchs;
	}

	/**
	 * ChildrenFetch 目前只能支持3层，4层查询就支持不了 4层以上可以使用 assembleString 此方法仍旧支持
	 * 
	 * @param _childrenFetch
	 */
	@Deprecated
	public void addChildrenFetch(String _childrenFetch) {
		if (!childrenFetchs.contains(_childrenFetch)) {
			this.childrenFetchs.add(_childrenFetch);
		}
	}

	public Vector<String> getOrderByDescs() {
		return this.orderByDescs;
	}

	public void setOrderByDescs(Vector<String> _orderByDescs) {
		this.orderByDescs = _orderByDescs;
	}

	public void addOrderByDesc(String orderField) {
		this.orderByDescs.add(orderField);
	}

	public Vector<String> getOrderByAscs() {
		return this.orderByAscs;
	}

	public void setOrderByAscs(Vector<String> _orderByAscs) {
		this.orderByAscs = _orderByAscs;
	}

	public void addOrderByAsc(String orderField) {
		this.orderByAscs.add(orderField);
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int _begin) {
		this.begin = _begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int _end) {
		this.end = _end;
	}

	public Vector<Criterion> getExpressions() {
		return this.expressions;
	}

	public void setExpressions(Vector<Criterion> _expressions) {
		this.expressions = _expressions;
	}

	public int getDefaultPageCount() {
		return defaultPageCount;
	}

	public boolean isOnlyQueryCount() {
		return isOnlyQueryCount;
	}

	public void setOnlyQueryCount(boolean isOnlyQueryCount) {
		this.isOnlyQueryCount = isOnlyQueryCount;
	}

	public void setDefaultPageCount(int defaultPageCount) {
		this.defaultPageCount = defaultPageCount;
	}

	public void addExpression(Criterion _queryExpression) {
		this.expressions.add(_queryExpression);
	}

	public boolean isLoadSubClass() {
		return isLoadSubClass;
	}

	public void setLoadSubClass(boolean isLoadSubClass) {
		this.isLoadSubClass = isLoadSubClass;
	}

	public boolean isSimpleLoadRelation() {
		return isSimpleLoadRelation;
	}

	public void setSimpleLoadRelation(boolean isSimpleLoadRelation) {
		this.isSimpleLoadRelation = isSimpleLoadRelation;
	}

	public String getAssembleString() {
		return queryAssemble == null ? null : queryAssemble.getAssembleString();
	}

	/**
	 * example：
	 * [ME2ROOM][ME2SITE][MANAGEDELEMENT2VENDOR][NE2NETYPE][PHYSICALCONTAINER2ME[PHYSICALCONTAINER2CHILD[CONTAINERCARDASSIGN]]]
	 * 
	 * @param assembleString
	 *            以'['开始
	 */
	public void setAssembleString(String assembleString) {
		queryAssemble = new QueryAssemble(assembleString);
	}

	/**
	 * 追加 assemble 支持重复 old:
	 * [PHYSICALCONTAINER2ME[PHYSICALCONTAINER2CHILD]][ME2SITE]
	 * assembleFragment:[ME2ROOM][PHYSICALCONTAINER2ME[PHYSICALCONTAINER2CHILD[CONTAINERCARDASSIGN]]]
	 * new:
	 * [ME2ROOM][PHYSICALCONTAINER2ME[PHYSICALCONTAINER2CHILD[CONTAINERCARDASSIGN]]][ME2SITE]
	 * 
	 * @param assembleFragment
	 */
	public void addAssembleFragment(String assembleFragment) {
		if (assembleFragment == null || assembleFragment.length() == 0) {
			return;
		}
		if (queryAssemble == null) {
			queryAssemble = new QueryAssemble(assembleFragment);
		} else {
			queryAssemble.addAssembleFragment(assembleFragment);
		}
	}

	public boolean isNotExecute() {
		return isNotExecute;
	}

	public void setNotExecute(boolean isNotExecute) {
		this.isNotExecute = isNotExecute;
	}

	public QueryAssemble getQueryAssemble() {
		return queryAssemble;
	}

	public void setQueryAssemble(QueryAssemble queryAssemble) {
		this.queryAssemble = queryAssemble;
	}

	public NamingParseStrategy getNamingParseStrategy() {
		return namingParseStrategy;
	}

	private MetaClass metaClass;
	private int begin;
	private int end;
	private int defaultPageCount = 1000;
	private Vector<Criterion> expressions = new Vector<Criterion>();
	private Vector<String> orderByDescs = new Vector<String>();
	private Vector<String> orderByAscs = new Vector<String>();
	private Vector<String> childrenFetchs = new Vector<String>();
	private Vector<QueryCriteria> componentQueryCriterias = new Vector<QueryCriteria>();
	private Vector<QueryCriteria> relationQueryCriterias = new Vector<QueryCriteria>();
	private MetaClassRelation metaClassRelation;
	private MetaComponentRelation metaComponentRelation;
	private boolean isOnlyQueryCount = false;
	private boolean ignoreQueryCount = false;
	private boolean isSimpleLoadRelation = false; // 只带出 关联对象及卫星表
													// 不包含其子及关联，提高性能的查询
	private boolean isLoadSubClass = true; // 是否带出子类卫星表
	protected NamingMetaObjectParseStrategy namingParseStrategy; // 对应拼装sql的别名，用于嵌入SQL
	private boolean isNotExecute = false;

	public boolean isIgnoreQueryCount() {
		return ignoreQueryCount;
	}

	public void setIgnoreQueryCount(boolean ignoreQueryCount) {
		this.ignoreQueryCount = ignoreQueryCount;
	}
}
