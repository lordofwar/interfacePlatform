/**************************************************************************
 * $RCSfile: MetaComponentObject.java,v $  $Revision: 1.3 $  $Date: 2009/03/03 06:25:12 $
 *
 * $Log: MetaComponentObject.java,v $
 * Revision 1.3  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.2  2009/01/04 01:21:01  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta;

/**
 * @author lethe
 */
public interface MetaComponentObject extends MetaData {
	
  /**
   * MetaComponent of this MetaComponentObject relay on.
   * @return
   */
	MetaComponent getMetaComponent();

}

