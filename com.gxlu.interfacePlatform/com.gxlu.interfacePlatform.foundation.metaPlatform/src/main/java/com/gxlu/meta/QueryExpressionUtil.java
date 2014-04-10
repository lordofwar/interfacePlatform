/***********************************************************************************************************************************************************************************************************************************************
 * $RCSfile: QueryExpressionUtil.java,v $ $Revision: 1.11 $ $Date: 2009/11/27 02:04:35 $
 * 
 * $Log: QueryExpressionUtil.java,v $
 * Revision 1.11  2009/11/27 02:04:35  liuding
 * *** empty log message ***
 *
 * Revision 1.10  2009/07/24 05:53:17  richie
 * *** empty log message ***
 *
 * Revision 1.9  2009/07/15 01:51:49  liuding
 * *** empty log message ***
 *
 * Revision 1.8  2009/05/18 11:42:39  richie
 * *** empty log message ***
 *
 * Revision 1.7  2009/05/06 10:44:45  liuding
 * *** empty log message ***
 *
 * Revision 1.6  2009/04/24 06:26:35  richie
 * *** empty log message ***
 *
 * Revision 1.5  2009/04/13 05:11:58  richie
 * *** empty log message ***
 *
 * Revision 1.4  2009/04/09 12:52:53  wayne
 * *** empty log message ***
 *
 * Revision 1.3  2009/03/31 09:09:52  richie
 * *** empty log message ***
 *
 * Revision 1.2  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/26 05:55:23  richie
 * *** empty log message ***
 *
 * Revision 1.8  2008/09/03 12:43:14  fengbin
 * Incomplete - task 34: 组织机构管理：根据名称查询和定位组织机构
 *
 * Revision 1.7  2008/09/02 10:43:07  fengbin
 * Incomplete - task 34: 组织机构管理：根据名称查询和定位组织机构
 *
 * Revision 1.6  2008/07/31 14:30:12  fengbin
 * MR#:NGRM-0001
 * formatter
 *
 * Revision 1.5  2008/07/28 08:07:52  fengbin
 * MR#:NGRM-0001
 * 处理一对多的one_to_many
 *
 * Revision 1.4  2008/07/28 07:51:12  fengbin
 * MR#:NGRM-0001
 * 处理一对多的one_to_many
 * Revision 1.3 2008/07/26 09:47:19 fengbin MR#:NGRM-0001 unittest
 * 
 * Revision 1.2 2008/07/26 02:10:39 fengbin MR#:NGRM-0001 ilike
 * 
 * Revision 1.1 2008/07/16 11:56:07 fengbin MR#:NGRM-0001 common query
 * 
 * Revision 1.5 2008/01/21 02:09:48 fengbin Incomplete - task 104: SG内存泄露问题
 * 
 **********************************************************************************************************************************************************************************************************************************************/
package com.gxlu.meta;

import java.util.Collection;
import java.util.List;

import com.gxlu.meta.criterion.Criterion;
import com.gxlu.meta.criterion.Expression;

public class QueryExpressionUtil {

  public static Criterion equals(String key, Object value) {
    return Expression.eq(key, value);
  }

  public static Criterion between(String key, Object low, Object high) {
    return Expression.between(key, low, high);
  }

  public static Criterion in(String key, Collection<?> values) {
    return Expression.in(key, values);
  }
  
  public static Criterion in(String key, Object [] values) {
  return Expression.in(key, values);
  }
  
  public static Criterion in(String key, String values) {
	  return Expression.in(key, values);
  }
  
  public static Criterion notIn(String key, Collection<?> values) {
    return Expression.notIn(key, values);
  } 
  
  public static Criterion notIn(String key, Object [] values) {
    return Expression.notIn(key, values);
  }  

  public static Criterion isNotNull(String key) {
    return Expression.isNotNull(key);
  }

  public static Criterion isNull(String key) {
    return Expression.isNull(key);
  }

  public static Criterion like(String key, Object value) {
    return Expression.like(key, value);
  }

  public static Criterion tslike(String key, Object value) {
    return Expression.like(key, "%" + value + "%");
  }

  public static Criterion rlike(String key, Object value) {
    return Expression.like(key, value + "%");
  }

  public static Criterion llike(String key, Object value) {
    return Expression.like(key, "%" + value);
  }

  public static Criterion ilike(String key, Object value) {
    return Expression.ilike(key, value);
  }

  public static Criterion tsilike(String key, Object value) {
    return Expression.ilike(key, "%" + value + "%");
  }

  public static Criterion rilike(String key, Object value) {
    return Expression.ilike(key, value + "%");
  }

  public static Criterion lilike(String key, Object value) {
    return Expression.ilike(key, "%" + value);
  }

  public static Criterion notLike(String key, Object value) {
    return Expression.ilike(key, value);
  }

  public static Criterion notEquals(String key, Object value) {
    return Expression.not(Expression.eq(key, value));
  }

  public static Criterion notEq(String key, Object value) {
    return Expression.notEq(key, value);
  }

  public static Criterion lessThan(String key, Object value) {
    return Expression.lt(key, value);
  }

  public static Criterion notMoreThan(String key, Object value) {
    return Expression.le(key, value);
  }

  public static Criterion moreThan(String key, Object value) {
    return Expression.gt(key, value);
  }

  public static Criterion notLessThan(String key, Object value) {
    return Expression.ge(key, value);
  }

  public static Criterion eqProperty(String key1, String key2) {
    return Expression.eqProperty(key1, key2);
  }

  public static Criterion sql(String sql, String[] keys) {
    return Expression.sql(sql, keys);
  }

  public static Criterion andExpression(Criterion expression1, Criterion expression2) {
    return Expression.and(expression1, expression2);
  }

  public static Criterion orExpression(Criterion expression1, Criterion expression2) {
    return Expression.or(expression1, expression2);
  }
  public static Criterion orExpression(List<Criterion> expressionList) {
	    return Expression.or(expressionList);
  }
}
