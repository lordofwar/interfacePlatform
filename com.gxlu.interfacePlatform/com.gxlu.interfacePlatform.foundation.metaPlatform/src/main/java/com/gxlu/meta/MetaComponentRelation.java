/**************************************************************************
 * $RCSfile: MetaComponentRelation.java,v $  $Revision: 1.4 $  $Date: 2009/03/03 06:25:12 $
 *
 * $Log: MetaComponentRelation.java,v $
 * Revision 1.4  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.3  2009/02/01 07:29:54  liuding
 * *** empty log message ***
 *
 * Revision 1.2  2009/01/04 01:21:02  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta;

/**
 * @author lethe
 */
public interface MetaComponentRelation {
	
  /**
   * MetaComponent of supplier of this relationship.
   * @return
   */
	MetaComponent getRelationMetaComponent();
	
	/**
	 * MetaComponent's code.
	 * @return
	 */
	String getAlias();
}

