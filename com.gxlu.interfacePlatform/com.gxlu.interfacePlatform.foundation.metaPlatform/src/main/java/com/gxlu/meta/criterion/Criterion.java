/**************************************************************************
 * $RCSfile: Criterion.java,v $  $Revision: 1.2 $  $Date: 2009/04/13 05:11:46 $
 *
 * $Log: Criterion.java,v $
 * Revision 1.2  2009/04/13 05:11:46  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/26 05:55:24  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.criterion;

/**
 * @author K
 */
public interface Criterion {

  public String getAliasSql();
  
  public Object[] getParams();
  
  public String getKey();
  
  public Object[] getKeys();
}

