/**************************************************************************
 * $RCSfile: MetaComponent.java,v $  $Revision: 1.6 $  $Date: 2009/03/03 06:25:12 $
 *
 * $Log: MetaComponent.java,v $
 * Revision 1.6  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.5  2009/02/27 02:02:00  richie
 * *** empty log message ***
 *
 * Revision 1.4  2009/01/04 01:21:01  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta;

import java.util.List;

/**
 * @author lethe
 */
public interface MetaComponent {
	
	/**
	 * Corresponding of XM_COMPONETSPEC.CODE in database.
	 * @return
	 */
	String getCode();
	
	
  /**
   * Corresponding of XM_COMPONETSPEC.DISPLAYNAME in database.
   * @return
   */
	String getDisplayName();
	
	/**
   * All attribute of MetaComponent.
   * @return
   */
	List<MetaAttribute> getMetaAttributes();
	
	 /**
   * Find MetaAttribute by attribute's name.
   * @return
   */
	MetaAttribute findMetaAttribute(String key);
	
  /**
   * Corresponding of XM_COMPONETSPEC.ID in database.
   * @return
   */
  long getId();

  /**
   * Corresponding of XM_COMPONETSPEC.VERSIONINSTANCE in database.
   * @return
   */
  long getVersionInstanceId();
}

