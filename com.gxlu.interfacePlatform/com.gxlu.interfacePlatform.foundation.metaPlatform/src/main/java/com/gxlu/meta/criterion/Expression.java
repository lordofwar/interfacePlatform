/**************************************************************************
 * $RCSfile: Expression.java,v $  $Revision: 1.11.2.1 $  $Date: 2011/01/20 08:29:36 $
 *
 * $Log: Expression.java,v $
 * Revision 1.11.2.1  2011/01/20 08:29:36  liuding
 * *** empty log message ***
 *
 * Revision 1.11  2010/05/17 11:47:31  liuding
 * *** empty log message ***
 *
 * Revision 1.10  2009/11/27 02:04:54  liuding
 * *** empty log message ***
 *
 * Revision 1.9  2009/07/15 01:51:49  liuding
 * *** empty log message ***
 *
 * Revision 1.8  2009/05/18 11:42:39  richie
 * *** empty log message ***
 *
 * Revision 1.7  2009/04/24 06:26:35  richie
 * *** empty log message ***
 *
 * Revision 1.6  2009/04/13 05:11:46  richie
 * *** empty log message ***
 *
 * Revision 1.5  2009/04/09 12:57:41  wayne
 * *** empty log message ***
 *
 * Revision 1.4  2009/03/31 09:22:03  richie
 * *** empty log message ***
 *
 * Revision 1.3  2009/03/31 09:09:52  richie
 * *** empty log message ***
 *
 * Revision 1.2  2009/02/11 06:00:39  shenwq
 * 关键字前加空格
 *
 * Revision 1.1  2008/12/26 05:55:24  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.criterion;

import java.util.Collection;
import java.util.List;

import com.gxlu.meta.tools.CollectionUtil;

/**
 * @author K
 */
public class Expression implements Criterion {

  public Expression(String aliasSql, String key, Object[] params) {
    this.aliasSql = aliasSql;
    this.keys = new String[1];
    this.keys[0] = key;
    this.params = params;
  }
  
  public Expression(String aliasSql, Object[] keys, Object[] params) {
    this.aliasSql = aliasSql;
    this.keys = keys;
    this.params = params;
  }

  public String getAliasSql() {

    return this.aliasSql;
  }

  public Object[] getParams() {

    return this.params;
  }

  public String getKey() {
    if (keys != null && keys.length > 0) {
      return (String) this.keys[0];
    }
    return null;
  }
  
  public Object[] getKeys() {
    return this.keys;
  }

  private String aliasSql;
  private Object[] params;
  private Object[] keys;

  public static Criterion eq(String key, Object value) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" = ").append("?");

    return new Expression(buffer.toString(), key, new Object[]{value});
  }
  
  public static Criterion notEq(String key, Object value) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" <> ").append("?");

    return new Expression(buffer.toString(), key, new Object[]{value});
  }

  public static Criterion between(String key, Object lo, Object hi) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" BETWEEN ").append("?").append(" AND ").append(" ? ");

    return new Expression(buffer.toString(), key, new Object[]{lo, hi});
  }

  public static Criterion notIn(String key, Collection<?> values) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" NOT IN (");
    for (int i = 0; i < values.size(); ++i) {
      if (i > 0) buffer.append(",");
      buffer.append("?");
    }
    buffer.append(")");

    return new Expression(buffer.toString(), key, values.toArray());
  }


  public static Criterion notIn(String key, Object[] values) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" NOT IN (");
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) buffer.append(",");
      buffer.append("?");
    }
    //-------------------------S1吴家林特殊需求: values为NULL或者size为NULL 拼成 in(null)
    if(values == null ||values.length==0){
    	buffer.append("null");
    }
    //-------------------------S1
    buffer.append(")");

    if(values == null ||values.length==0){
    	return new Expression(buffer.toString(), key, null);
    }else{
    	return new Expression(buffer.toString(), key, values);
    }
  }

  public static Criterion in(String key, Collection<?> values) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" IN (");
    for (int i = 0; i < values.size(); ++i) {
      if (i > 0) buffer.append(",");
      buffer.append("?");
    }
    //-------------------------S1吴家林需求: values为NULL或者size为NULL 拼成 in(null)
    if(values == null ||values.size()==0){
    	buffer.append("null");
    }
    //-------------------------S1
    buffer.append(")");
    
    if(values == null ||values.size()==0){
    	return new Expression(buffer.toString(), key, null);
    }else{
    	return new Expression(buffer.toString(), key, values.toArray());
    }
  }
  
  public static Criterion in(String key, String valuesOrSql) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" IN ( ? )");
    return new Expression(buffer.toString(), key, new String[]{valuesOrSql});
  }

  public static Criterion in(String key, Object[] values) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" IN (");
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) buffer.append(",");
      buffer.append("?");
    }
    buffer.append(")");

    return new Expression(buffer.toString(), key, values);
  }

  public static Criterion not(Criterion in) {
    return new Expression(" NOT " + in.getAliasSql(), in.getKey(), in.getParams());
  }

  public static Criterion isNotNull(String key) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" IS NOT NULL");

    return new Expression(buffer.toString(), key, null);
  }

  public static Criterion isNull(String key) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" IS NULL ");

    return new Expression(buffer.toString(), key, null);
  }

  public static Criterion like(String key, Object value) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" LIKE ").append("?");

//    return new Expression(buffer.toString(), key, new Object[]{value.toString().toUpperCase()});
    
    //buffer.append("UPPER(").append(key).append(") LIKE UPPER(").append("?)");

    return new Expression(buffer.toString(), key, new Object[]{value});
  }

  public static Criterion ilike(String key, Object value) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("UPPER(").append(key).append(") LIKE UPPER(").append("?)");

    return new Expression(buffer.toString(), key, new Object[]{value});
  }

  public static Criterion lt(String key, Object value) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" < ").append("?");

    return new Expression(buffer.toString(), key, new Object[]{value});
  }
  
  public static Criterion le(String key, Object value) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" <= ").append("?");

    return new Expression(buffer.toString(), key, new Object[]{value});
  }
  
  public static Criterion gt(String key, Object value) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" > ").append("?");

    return new Expression(buffer.toString(), key, new Object[]{value});
  }
  

  public static Criterion ge(String key, Object value) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key).append(" >= ").append("?");

    return new Expression(buffer.toString(), key, new Object[]{value});
  }

  public static Criterion sql(String sql, String[] keys) {

    return new Expression(sql, keys, null);
  }

  public static Criterion eqProperty(String key1, String key2) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(key1).append(" = ").append(key2);
    String[] keys = new String[2];
    keys[0] = key1;
    keys[1] = key2;
    return new Expression(buffer.toString(), keys, null);
  }

  public static Criterion and(Criterion expression1, Criterion expression2) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("(").append(expression1.getAliasSql()).append(" AND ").append(expression2.getAliasSql()).append(")");
    
    
//    List keysArray = Arrays.asList(expression1.getKeys());
//    keysArray.addAll((Arrays.asList(expression2.getKeys()));
//    
//    List paramsArray = Arrays.asList(expression1.getParams());
//    paramsArray.addAll(Arrays.asList(expression2.getParams()));

    return new Expression(buffer.toString(), CollectionUtil.mergeArray(expression1.getKeys(), expression2.getKeys()), CollectionUtil.mergeArray(expression1.getParams(), expression2.getParams()));
  }

  public static Criterion or(Criterion expression1, Criterion expression2) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("(").append(expression1.getAliasSql()).append(" OR ").append(expression2.getAliasSql()).append(")");

//    List keysArray = Arrays.asList(expression1.getKeys());
//    keysArray.addAll(Arrays.asList(expression2.getKeys()));
//    
//    List paramsArray = Arrays.asList(expression1.getParams());
//    paramsArray.addAll(Arrays.asList(expression2.getParams()));
    return new Expression(buffer.toString(), CollectionUtil.mergeArray(expression1.getKeys(), expression2.getKeys()), CollectionUtil.mergeArray(expression1.getParams(), expression2.getParams()));
    //return new Expression(buffer.toString(), keysArray.toArray(), paramsArray.toArray());
  }
  public static Criterion or(List<Criterion> expressionList) {
	  	if(expressionList==null || expressionList.size()==0) return null;
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("(");
	    Object[] key = null;
	    Object[] params = null;
	    for (int i = 0; i < expressionList.size(); i++) {
	    	Criterion criterion = expressionList.get(i);
	    	buffer.append(criterion.getAliasSql());
	    	if(i!=(expressionList.size()-1)){
	    		buffer.append(" OR ");
	    	}
	    	
	    	if(key==null){
	    		key = criterion.getKeys();
	    	}else{
	    		key = CollectionUtil.mergeArray(key,criterion.getKeys());
	    	}
	    	
	    	if(params==null){
	    		params = criterion.getParams();
	    	}else{
	    		params = CollectionUtil.mergeArray(params,criterion.getParams());
	    	}
	    	
		}
	    buffer.append(")");
	    return new Expression(buffer.toString(), key, params);

  }
}
