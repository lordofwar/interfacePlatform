/**************************************************************************
 * $RCSfile: MetaObjectQueryResult.java,v $  $Revision: 1.6 $  $Date: 2009/11/20 06:51:11 $
 *
 * $Log: MetaObjectQueryResult.java,v $
 * Revision 1.6  2009/11/20 06:51:11  richie
 * *** empty log message ***
 *
 * Revision 1.5  2009/05/21 12:19:51  shenwq
 * *** empty log message ***
 *
 * Revision 1.4  2009/05/21 07:33:15  liuding
 * *** empty log message ***
 *
 * Revision 1.3  2009/05/08 05:09:28  liuding
 * *** empty log message ***
 *
 * Revision 1.2  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/26 05:55:23  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gxlu.meta.help.MetaObjectHelper;


/**
 * @author K
 */
public class MetaObjectQueryResult implements Serializable ,Cloneable{

	private static final long serialVersionUID = -3572254517618275123L;

/**
   * Default constructor.
   * 
   * @param total total records count that match the where conditions.
   * @param beans a list of records.
   */
  public MetaObjectQueryResult(long total, List<MetaObject> beans){
    this.total = total;
    this.list = beans;
  }

  /**
   * Collection of query result.
   * @return
   */
  public List<MetaObject> getList() {
    return list;
  }

  public void setList(List<MetaObject> list) {
    this.list = list;
  }

  /**
   * Total record count that this query may get.
   * @return
   */
  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }
  
  public String getSql() {
    return sql;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public Object clone() throws CloneNotSupportedException {
	  MetaObjectQueryResult clone = (MetaObjectQueryResult) super.clone();
	  
	  List<MetaObject> listClone = null;
		if (list != null) {
			listClone = new ArrayList<MetaObject>();
			for (MetaObject object : list) {
				MetaObject cloneMetaObject = MetaObjectHelper.cloneMetaObject(object);
				listClone.add(cloneMetaObject);
			}
		}
		clone.setList(listClone);
		return clone;
	}
  

  private List<MetaObject> list;
  private String sql;
  private long total;
}
