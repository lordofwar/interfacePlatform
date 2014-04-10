/**************************************************************************
 * $RCSfile: DefaultMetaComponentRelation.java,v $  $Revision: 1.3 $  $Date: 2009/03/06 07:04:38 $
 *
 * $Log: DefaultMetaComponentRelation.java,v $
 * Revision 1.3  2009/03/06 07:04:38  richie
 * *** empty log message ***
 *
 * Revision 1.2  2009/02/01 07:29:47  liuding
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/19 09:40:06  richie
 * *** empty log message ***
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : DefaultMetaComponentRelation.java
 * Created on : Dec 19, 2008 3:28:31 PM
 * Creator : RichieJin
 */
package com.gxlu.meta.cfg;

import com.gxlu.meta.MetaComponent;
import com.gxlu.meta.MetaComponentRelation;

/**
 * <pre>
 * Description : TODO
 * @author RichieJin
 * </pre>
 */
public class DefaultMetaComponentRelation implements MetaComponentRelation {

	public MetaComponent getMetaComponent() {
		return metaComponent;
	}

	public void setMetaComponent(MetaComponent metaComponent) {
		this.metaComponent = metaComponent;
	}

	public MetaComponent getRelationMetaComponent() {

		return metaComponent;
	}

	private MetaComponent metaComponent;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
	  this.alias = alias;
	}

	private String alias;
}
