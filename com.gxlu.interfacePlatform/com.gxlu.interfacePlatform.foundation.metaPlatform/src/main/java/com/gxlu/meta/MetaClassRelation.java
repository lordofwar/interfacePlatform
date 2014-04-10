/**************************************************************************
 * $RCSfile: MetaClassRelation.java,v $  $Revision: 1.9 $  $Date: 2009/07/28 03:05:38 $
 *
 * $Log: MetaClassRelation.java,v $
 * Revision 1.9  2009/07/28 03:05:38  richie
 * *** empty log message ***
 *
 * Revision 1.8  2009/07/20 06:37:07  liuding
 * *** empty log message ***
 *
 * Revision 1.7  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.6  2009/02/27 02:02:00  richie
 * *** empty log message ***
 *
 * Revision 1.5  2009/02/04 03:29:11  liuding
 * *** empty log message ***
 *
 * Revision 1.4  2009/01/04 01:21:02  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta;

/**
 * @author K & lethe
 */
public interface MetaClassRelation {

	/**
	 * Relation type may be 1:N, N:1, 1:1 and M:N.
	 * @see com.gxlu.meta.tools.MetaConsts VALUE_RELATIONSPEC_MULTIPLE
	 * @return
	 */
	int getRelationType();

	/**
	 * if need cascade delete for its associated MetaObject.
	 * @return
	 */
	int getCascade();

  /**
   * Corresponding of XM_RELATIONSPEC.CODE in database.
   * @return
   */
	String getAlias();
	
	String getDisplayName();

	/**
	 * MetaClass of supplier of this relationship.
	 * @return
	 */
	MetaClass getRelationMetaClass();

	 /**
   * MetaClass of owner of this relationship.
   * @return
   */
	MetaClass getOwnerMetaClass();

	/**
	 * @return
	 */
	boolean isSameRelationSpec(MetaClassRelation meteClassRelation);
	
  /**
   * Corresponding of XM_RELATIONSPEC.ID in database.
   * @return
   */
  long getId();

  /**
   * Corresponding of XM_RELATIONSPEC.VERSIONINSTANCE in database.
   * @return
   */
  long getVersionInstanceId();
}
